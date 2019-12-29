package com.optile.cs.client;

import com.optile.cs.error.JobErrorCode;
import com.optile.cs.error.JobException;
import com.optile.cs.model.EventMessage;
import com.optile.cs.model.JobStatus;
import com.optile.cs.model.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

public abstract class Job {

    @Value("${job.statusQueue}")
    private String statusQueue;

    @Value("${job.eventQueue}")
    private String eventQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    protected abstract void process(String jobId);

    public void execute(String jobId) throws JobException {
        if(jobId == null && jobId.isEmpty())
            throw new JobException(JobErrorCode.JOB_ERROR_001);

        this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.RUNNING));
        try {
            this.process(jobId);
            this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.SUCCESS));
        } catch (Exception exception) {
            this.jmsTemplate.convertAndSend(eventQueue, new EventMessage(jobId, exception.getMessage()));
            this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.FAILED));
        }
    }

    public void log(String jobId, String message) {
        this.jmsTemplate.convertAndSend(eventQueue, new EventMessage(jobId, message));
    }
}

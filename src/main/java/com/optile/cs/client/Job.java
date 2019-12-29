package com.optile.cs.client;

import com.optile.cs.model.EventMessage;
import com.optile.cs.model.JobStatus;
import com.optile.cs.model.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

public abstract class Job {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("status")
    private Queue statusQueue;

    @Autowired
    @Qualifier("event")
    private Queue eventQueue;

    private String jobId;

    protected abstract void execute();

    public void execute(String jobId) {
        this.jobId = jobId;

        this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.RUNNING));
        try {
            this.execute();
            this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.SUCCESS));
        } catch (Exception exception) {
            this.jmsTemplate.convertAndSend(eventQueue, new EventMessage(jobId, exception.getMessage()));
            this.jmsTemplate.convertAndSend(statusQueue, new StatusMessage(jobId, JobStatus.FAILED));
        }
    }

    public void log(String message) {
        this.jmsTemplate.convertAndSend(eventQueue, new EventMessage(jobId, message));
    }
}

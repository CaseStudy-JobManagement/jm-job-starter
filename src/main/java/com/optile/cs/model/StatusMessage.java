package com.optile.cs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class StatusMessage implements Serializable {
    private String jobId;
    private JobStatus jobStatus;
}


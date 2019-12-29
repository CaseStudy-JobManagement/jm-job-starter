package com.optile.cs.error;

import lombok.Getter;

@Getter
public class JobException extends Exception {
    private JobErrorCode jobErrorCode;

    public JobException(JobErrorCode jobErrorCode) {
        super(jobErrorCode.getMessage());
    }
}

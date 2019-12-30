package com.optile.cs.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    JOB_ERROR_001("Failed to start job : invalid job id"),
    ;

    private String message;
}

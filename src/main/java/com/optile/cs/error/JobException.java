package com.optile.cs.error;

import lombok.Getter;

@Getter
public class JobException extends Exception {
    public JobException(String message) {
        super(message);
    }
}

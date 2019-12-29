package com.optile.cs.client;

import org.springframework.stereotype.Component;

@Component
public class TestJob extends Job {
    @Override
    public void execute() {
        this.log("Hello");
    }
}

package com.iamakulov.myskusdk;

public class MyskuError {
    private Throwable exception;

    public MyskuError(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}

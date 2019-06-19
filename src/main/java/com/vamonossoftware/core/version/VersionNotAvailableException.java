package com.vamonossoftware.core.version;

public class VersionNotAvailableException extends Exception {

    public VersionNotAvailableException() {
    }

    public VersionNotAvailableException(String message) {
        super(message);
    }

    public VersionNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public VersionNotAvailableException(Throwable cause) {
        super(cause);
    }

    public VersionNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

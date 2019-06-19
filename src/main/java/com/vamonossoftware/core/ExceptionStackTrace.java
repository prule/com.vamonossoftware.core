package com.vamonossoftware.core;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Generates a string representation of an exception stack trace.
 * <p>
 * Sample usage: <code>new ExceptionStackTrace(ex).toString();</code>
 */
public class ExceptionStackTrace {

    private final String stackTrace;

    public ExceptionStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        stackTrace = sw.toString();
    }

    @Override
    public String toString() {
        return stackTrace;
    }
}

package com.vamonossoftware.core;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class TemplatedString {

    private final String template;
    private final Map<String, String> variables;

    public TemplatedString(String template, Map<String, String> variables) {
        this.template = template;
        this.variables = variables;
    }

    public String toString() {
        return StringSubstitutor.replace(template, variables);
    }
}

package com.vamonossoftware.core.sources;

public interface Matcher<T> {

    boolean matches(T in);
}

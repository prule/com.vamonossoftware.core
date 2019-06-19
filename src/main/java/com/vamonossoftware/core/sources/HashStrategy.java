package com.vamonossoftware.core.sources;

public interface HashStrategy<T> {

    String compute(T input);

}

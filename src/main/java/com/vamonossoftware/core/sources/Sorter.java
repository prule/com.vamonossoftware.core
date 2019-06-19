package com.vamonossoftware.core.sources;

import java.util.List;

public interface Sorter<T> {

    List<T> sort(List<T> toSort);
}

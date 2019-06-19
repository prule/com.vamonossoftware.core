package com.vamonossoftware.core.sources;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilenameSorter implements Sorter<Path> {

    @Override
    public List<Path> sort(List<Path> toSort) {
        List<Path> list = new ArrayList<>(toSort);
        list.sort(Comparator.comparing(Path::getFileName));
        return list;
    }
}

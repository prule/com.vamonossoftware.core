package com.vamonossoftware.core.sources;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.function.ThrowingFunction.unchecked;

public class PathSelection {

    private final List<Path> paths;

    public PathSelection(List<Path> paths) {
        this.paths = paths;
    }

    public InputStream combinedInputStream() {
        List<InputStream> streams = paths.stream()
                .map(unchecked(path -> new FileInputSource(path).inputStream()))
                .collect(Collectors.toList());

        return new SequenceInputStream(Collections.enumeration(streams));
    }

    public <T> Stream map(Function<? super Path, ?> mapper) {
        return paths.stream()
                .map(mapper);
    }
}

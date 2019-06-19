package com.vamonossoftware.core.sources;


import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryInputSource implements InputSource {

    private final Path directory;
    private final Sorter<Path> sorter;
    private final Matcher<Path> matcher;
    private final HashStrategy<PathSelection> hashStrategy;

    public DirectoryInputSource(Path directory, Matcher<Path> matcher, Sorter<Path> sorter, HashStrategy<PathSelection> hashStrategy) {
        this.directory = directory;
        this.sorter = sorter;
        this.matcher = matcher;
        this.hashStrategy = hashStrategy;
    }

    @Override
    public InputStream inputStream() {
        return listPaths().combinedInputStream();
    }

    private PathSelection listPaths() {
        try {
            List<Path> sortedPaths = sorter.sort(Files.list(directory).collect(Collectors.toList()));
            List<Path> filteredPaths = sortedPaths.stream()
                    .filter(matcher::matches)
                    .collect(Collectors.toList());

            return new PathSelection(filteredPaths);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String text() throws IOException {
        return CharStreams.toString(new InputStreamReader(inputStream()));
    }

    public String hash() {
        return this.hashStrategy.compute(listPaths());
    }
}

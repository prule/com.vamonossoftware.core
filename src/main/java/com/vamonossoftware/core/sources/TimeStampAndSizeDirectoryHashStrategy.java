package com.vamonossoftware.core.sources;


import java.util.stream.Collectors;

public class TimeStampAndSizeDirectoryHashStrategy implements HashStrategy<PathSelection> {

    private final TimeStampAndSizeFileHashStrategy fileHashStrategy;

    public TimeStampAndSizeDirectoryHashStrategy(MessageDigester digester) {
        this.fileHashStrategy = new TimeStampAndSizeFileHashStrategy(digester);
    }

    @Override
    public String compute(PathSelection paths) {
        return paths.map(path -> fileHashStrategy.compute(path.toFile()))
                .collect(Collectors.<String>toList())
                .toString();
    }
}

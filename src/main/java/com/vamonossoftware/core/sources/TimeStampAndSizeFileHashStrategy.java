package com.vamonossoftware.core.sources;


import java.io.File;

public class TimeStampAndSizeFileHashStrategy implements HashStrategy<File> {

    private final MessageDigester digester;

    public TimeStampAndSizeFileHashStrategy(MessageDigester digester) {
        this.digester = digester;
    }

    @Override
    public String compute(File file) {
        String value = String.join("|",
                file.getName(),
                String.valueOf(file.length()),
                String.valueOf(file.lastModified())
        );

        return digester.digest(value);
    }
}

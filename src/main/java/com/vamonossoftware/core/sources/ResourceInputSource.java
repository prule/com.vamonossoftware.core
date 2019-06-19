package com.vamonossoftware.core.sources;


import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceInputSource implements InputSource {

    private final String path;

    public ResourceInputSource(String path) {
        this.path = path;
    }

    public String text() throws IOException {
        return CharStreams.toString(new InputStreamReader(inputStream(), StandardCharsets.UTF_8));
    }

    public InputStream inputStream() {
        return this.getClass().getResourceAsStream(path);
    }

}

package com.vamonossoftware.core.sources;

import java.io.IOException;
import java.io.InputStream;

public interface InputSource {
    InputStream inputStream() throws IOException;

    String text() throws IOException;
}

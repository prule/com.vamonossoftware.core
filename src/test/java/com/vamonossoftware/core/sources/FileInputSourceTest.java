package com.vamonossoftware.core.sources;

import com.google.common.io.CharStreams;
import com.vamonossoftware.core.DirectoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class FileInputSourceTest {

    private DirectoryManager directoryManager;

    @BeforeEach
    public void setup() throws Exception {
        this.directoryManager = new DirectoryManager();
    }

    @AfterEach
    public void teardown() throws Exception {
        this.directoryManager.clean();
    }

    @Test
    public void should_read_text_file() throws Exception {

        final String content1 = "hello\nworld";
        File file = directoryManager.writeFile("1.txt", content1);

        FileInputSource source = new FileInputSource(file);

        try (InputStream input = source.inputStream()) {

            String text = CharStreams.toString(new InputStreamReader(input));
            assertThat(text).isEqualTo(content1);

        }

    }

    @Test
    public void should_read_gzip_file() throws Exception {

        final String content1 = "this is a compressed file\nwith multiple lines";

        File file = directoryManager.writeGzipFile("1.txt", content1);

        FileInputSource source = new FileInputSource(file);

        try (InputStream input = source.inputStream()) {

            String text = CharStreams.toString(new InputStreamReader(input));
            assertThat(text).isEqualTo(content1);

        }

    }

}

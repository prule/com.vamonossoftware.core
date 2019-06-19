package com.vamonossoftware.core.sources;

import com.google.common.io.CharStreams;
import com.vamonossoftware.core.DirectoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryInputSourceTest {

    private static final String content1 = "this is a compressed file\nwith multiple lines";

    private DirectoryManager directoryManager;
    private MessageDigester messageDigester;

    @BeforeEach
    public void setup() throws Exception {
        this.directoryManager = new DirectoryManager();
        this.messageDigester = new MessageDigester(MessageDigest.getInstance("SHA-1"));
    }

    @AfterEach
    public void teardown() throws Exception {
        this.directoryManager.clean();
    }

    @Test
    public void should_concatenate_multiple_files() throws Exception {

        directoryManager.writeFile("1.txt", "hello");
        directoryManager.writeFile("2.txt", "world");

        DirectoryInputSource source = new DirectoryInputSource(
                directoryManager.directoryPath(),
                new FileExtensionMatcher(true, "txt"),
                new FilenameSorter(),
                new TimeStampAndSizeDirectoryHashStrategy(messageDigester)
        );

        try (InputStream input = source.inputStream()) {

            String text = CharStreams.toString(new InputStreamReader(input));
            assertThat(text).isEqualTo("helloworld");

        }

    }

    @Test
    public void should_concatenate_text_from_multiple_files() throws Exception {

        directoryManager.writeFile("1.txt", "hello");
        directoryManager.writeFile("2.txt", "world");

        DirectoryInputSource source = new DirectoryInputSource(
                directoryManager.directoryPath(),
                new FileExtensionMatcher(true, "txt"),
                new FilenameSorter(),
                new TimeStampAndSizeDirectoryHashStrategy(messageDigester)
        );

        assertThat(source.text()).isEqualTo("helloworld");

    }

}

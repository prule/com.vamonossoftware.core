package com.vamonossoftware.core.sources;

import com.google.common.io.CharStreams;
import com.vamonossoftware.core.DirectoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathSelectionTest {


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

        Path path1 = directoryManager.writeFile("1.txt", "hello").toPath();
        Path path2 = directoryManager.writeFile("2.txt", "world").toPath();

        PathSelection pathSelection = new PathSelection(List.of(path1, path2));
        try (InputStream input = pathSelection.combinedInputStream()) {

            String text = CharStreams.toString(new InputStreamReader(input));
            assertThat(text).isEqualTo("helloworld");

        }

    }
}
package com.vamonossoftware.core.sources;

import com.vamonossoftware.core.DirectoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeStampAndSizeDirectoryHashStrategyTest {

    private MessageDigester digest;

    @BeforeEach
    public void setup() throws Exception {
        this.digest = new MessageDigester(MessageDigest.getInstance("SHA-1"));
    }

    @Test
    public void lastmod_should_affect_hash() throws Exception {
        TimeStampAndSizeDirectoryHashStrategy strategy = new TimeStampAndSizeDirectoryHashStrategy(digest);

        String hash1;
        {
            DirectoryManager directoryManager = new DirectoryManager();
            File file1 = directoryManager.writeFile("test1", "helloworld");
            File file2 = directoryManager.writeFile("test2", "helloworld");
            hash1 = strategy.compute(new PathSelection(Arrays.asList(file1.toPath(), file2.toPath())));
        }

        // sleep to ensure last modified timestamps are different
        Thread.sleep(1000);

        String hash2;
        {
            DirectoryManager directoryManager = new DirectoryManager();
            File file1 = directoryManager.writeFile("test1", "helloworld");
            File file2 = directoryManager.writeFile("test2", "helloworld");
            hash2 = strategy.compute(new PathSelection(Arrays.asList(file1.toPath(), file2.toPath())));
        }

        assertThat(hash1).isNotEqualTo(hash2);
    }
}
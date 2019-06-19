package com.vamonossoftware.core.sources;

import com.vamonossoftware.core.DirectoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.MessageDigest;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeStampAndSizeFileHashStrategyTest {

    private MessageDigester digest;
    private DirectoryManager directoryManager;

    @BeforeEach
    public void setup() throws Exception {
        this.digest = new MessageDigester(MessageDigest.getInstance("SHA-1"));
        this.directoryManager = new DirectoryManager();
    }

    @Test
    public void filename_should_affect_hash() throws Exception {
        TimeStampAndSizeFileHashStrategy strategy = new TimeStampAndSizeFileHashStrategy(digest);

        File file1 = directoryManager.writeFile("test1", "helloworld");
        String hash1 = strategy.compute(file1);

        File file2 = directoryManager.writeFile("test2", "helloworld");
        file2.setLastModified(file1.lastModified());

        String hash2 = strategy.compute(file2);

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    public void size_should_affect_hash() throws Exception {
        TimeStampAndSizeFileHashStrategy strategy = new TimeStampAndSizeFileHashStrategy(digest);

        File file1 = directoryManager.writeFile("test1", "helloworld");
        String hash1 = strategy.compute(file1);

        File file2 = directoryManager.writeFile("test2", "helloworld ");
        file2.setLastModified(file1.lastModified());
        String hash2 = strategy.compute(file2);

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    public void lastmod_should_affect_hash() throws Exception {
        TimeStampAndSizeFileHashStrategy strategy = new TimeStampAndSizeFileHashStrategy(digest);

        File file1 = new DirectoryManager().writeFile("test1", "helloworld");
        String hash1 = strategy.compute(file1);

        File file2 = new DirectoryManager().writeFile("test1", "helloworld");
        file2.setLastModified(file1.lastModified() + 1000);
        String hash2 = strategy.compute(file2);

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    public void content_should_not_affect_hash() throws Exception {
        TimeStampAndSizeFileHashStrategy strategy = new TimeStampAndSizeFileHashStrategy(digest);

        File file1 = new DirectoryManager().writeFile("test1", "aaaaaaaa");
        String hash1 = strategy.compute(file1);

        File file2 = new DirectoryManager().writeFile("test1", "bbbbbbbb");
        file2.setLastModified(file1.lastModified());
        String hash2 = strategy.compute(file2);

        assertThat(hash1).isEqualTo(hash2);
    }

}
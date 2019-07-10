package com.vamonossoftware.core;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

public class DirectoryManager {

    private final File directory;

    public DirectoryManager() {
        directory = Files.createTempDir();
    }

    public DirectoryManager(File directory) {
        this.directory = directory;
    }

    public void clean() {
        try {
            FileUtils.deleteDirectory(directory);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path directoryPath() {
        return directory.toPath();
    }

    public File writeGzipFile(String fileName, String content) throws IOException {
        File file = new File(directory, fileName);
        try (OutputStream os = new GZIPOutputStream(new FileOutputStream(file))) {
            os.write(content.getBytes());
        }
        return file;
    }

    public File writeFile(String fileName, String content) throws IOException {
        File file = new File(directory, fileName);
        file.getParentFile().mkdirs();
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(content.getBytes());
        }
        return file;
    }

    public File getFile(String fileName) {
        return new File(directory, fileName);
    }
}

package com.vamonossoftware.core.sources;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class FileInputSource implements InputSource {

    private static final int BUFFER_SIZE = 66560;

    private final Path path;

    public FileInputSource(String path) {
        this(Paths.get(path));
    }

    public FileInputSource(File file) {
        this(file.toPath());
    }

    public FileInputSource(Path path) {
        Objects.requireNonNull(path, "Path cannot be null");
        this.path = path;
    }

    @Override
    public InputStream inputStream() throws IOException {
        return toInputStream(path);
    }

    public String text() throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    /**
     * Detects for GZIP files and will automatically wrap with GZIPInputStream when necessary.
     */
    private InputStream toInputStream(Path path) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {

            // read first couple of bytes from file
            byte[] header = new byte[2];
            if (fileInputStream.read(header, 0, 2) == 2) {
                int magic = header[0] & 0xff | ((header[1] << 8) & 0xff00);

                // test for gzip
                if (magic == GZIPInputStream.GZIP_MAGIC) {
                    // set buffer size for best performance
                    return new GZIPInputStream(new FileInputStream(path.toFile()), BUFFER_SIZE);
                }
            }

            // default to normal file input stream
            return new BufferedInputStream(new FileInputStream(path.toFile()), BUFFER_SIZE);

        }
    }

}

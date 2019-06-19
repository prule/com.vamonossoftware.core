package com.vamonossoftware.core.sources;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileExtensionMatcher implements Matcher<Path> {

    private final Set<String> extensions;
    private final boolean caseInsensitive;

    public FileExtensionMatcher(boolean caseInsensitive, String... extensions) {
        this(caseInsensitive, new HashSet<>(Arrays.asList(extensions)));
    }

    public FileExtensionMatcher(boolean caseInsensitive, Set<String> extensions) {
        this.caseInsensitive = caseInsensitive;
        this.extensions = extensions;
    }

    @Override
    public boolean matches(Path in) {
        for (String extension : extensions) {
            String suffix = "." + (caseInsensitive ? extension.toUpperCase() : extension);
            String name = caseInsensitive ? in.toString().toUpperCase() : in.toString();
            if (name.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
}

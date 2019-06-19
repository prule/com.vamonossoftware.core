package com.vamonossoftware.core.version;

public interface VersionProvider {

    String version() throws VersionNotAvailableException;

}

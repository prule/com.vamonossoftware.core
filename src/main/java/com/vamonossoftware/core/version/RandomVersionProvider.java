package com.vamonossoftware.core.version;

import java.util.UUID;

public class RandomVersionProvider implements VersionProvider {

    @Override
    public String version() {
        return UUID.randomUUID().toString();
    }

}

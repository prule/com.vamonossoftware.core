package com.vamonossoftware.core.version;

public class ManifestVersionProvider implements VersionProvider {

    @Override
    public String version() {
        return new ManifestSource().get("Build-Revision");
    }

}

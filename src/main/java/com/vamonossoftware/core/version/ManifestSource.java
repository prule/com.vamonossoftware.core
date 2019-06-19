package com.vamonossoftware.core.version;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ManifestSource {

    private Manifest manifest;

    public Manifest manifest() {
        if (manifest == null) {
            manifest = getManifest();
        }
        return manifest;
    }

    public String get(String name) {
        Manifest manifest = manifest();
        Attributes attributes = manifest.getAttributes(name);
        return attributes != null ? attributes.getValue(name) : null;
    }

    // https://stackoverflow.com/a/32486996/20242
    private Manifest getManifest() {
        // get the full name of the application manifest file
        String appManifestFileName = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() + JarFile.MANIFEST_NAME;

        Enumeration resEnum;
        try {
            // get a list of all manifest files found in the jars loaded by the app
            resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                try {
                    URL url = (URL) resEnum.nextElement();
                    // is the app manifest file?
                    if (url.toString().equals(appManifestFileName)) {
                        // open the manifest
                        InputStream is = url.openStream();
                        if (is != null) {
                            // read the manifest and return it to the application
                            return new Manifest(is);
                        }
                    }
                }
                catch (Exception e) {
                    // Silently ignore wrong manifests on classpath?
                }
            }
        }
        catch (IOException e1) {
            // Silently ignore wrong manifests on classpath?
        }
        return new Manifest();
    }
}

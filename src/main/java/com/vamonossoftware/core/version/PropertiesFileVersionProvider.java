package com.vamonossoftware.core.version;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFileVersionProvider implements VersionProvider {

    private final String propertiesPath;
    private final String propertyName;

    public PropertiesFileVersionProvider(String propertiesPath, String propertyName) {
        this.propertiesPath = propertiesPath;
        this.propertyName = propertyName;
    }

    @Override
    public String version() throws VersionNotAvailableException {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream(propertiesPath));
            return properties.getProperty(propertyName);
        }
        catch (IOException e) {
            throw new VersionNotAvailableException(e);
        }
    }

}

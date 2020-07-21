package com.github.ograndebe.lilihelper;

import java.io.File;
import java.io.InputStream;

public class AppConstants {
    public static File getConfFile() {
        final File confFile = new File(String.format("%s/liliHelper.txt",System.getProperty("user.home")));
        return confFile;
    }

    public static InputStream getDefaultConfig() {
        return ConfigurationLoader.class.getResourceAsStream("/default-config.txt");
    }
}

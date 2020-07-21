package com.github.ograndebe.lilihelper;

import java.io.*;
import java.util.List;

public class ConfigurationLoader {


    public List<ButtonConfiguration> load() throws IOException {
        final File confFile = AppConstants.getConfFile();
        if (!confFile.exists()) {
            try (final InputStream defaultConfig = AppConstants.getDefaultConfig()) {
                byte[] buffer = new byte[defaultConfig.available()];
                defaultConfig.read(buffer);

                try (OutputStream outStream = new FileOutputStream(confFile)) {
                    outStream.write(buffer);
                }
            }
        }

        return new ConfigurationReader(confFile).getButtonList();
    }



}

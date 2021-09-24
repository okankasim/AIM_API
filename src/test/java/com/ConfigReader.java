package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to load properties file");
            e.printStackTrace();
        }
    }

    public static String getEnvProperty(String key) {
        key = key.toLowerCase();
        return properties.getProperty(key);
    }
}

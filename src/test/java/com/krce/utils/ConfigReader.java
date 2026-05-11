package com.krce.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    static Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (Exception e) {
            System.out.println("Config file error: " + e.getMessage());
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
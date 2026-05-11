package com.krce.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            prop = new Properties();
            prop.load(fis);
            fis.close();
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }

    public static String getBrowser() {
        return prop.getProperty("browser");
    }

    public static String getBaseUrl() {
        return prop.getProperty("baseUrl");
    }

    public static String getUsername() {
        return prop.getProperty("username");
    }

    public static String getPassword() {
        return prop.getProperty("password");
    }

    public static int getTimeout() {
        return Integer.parseInt(prop.getProperty("timeout"));
    }
}

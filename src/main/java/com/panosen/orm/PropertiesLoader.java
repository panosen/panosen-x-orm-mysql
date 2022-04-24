package com.panosen.orm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadBuiltInProperties() throws IOException {
        String fileName = "datasources/datasource-builtin.properties";
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            System.out.println("url is null.");
            return new Properties();
        }

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public Properties loadDataSourceProperties() throws IOException {
        String fileName = "/datasources/datasource.properties";
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        if (inputStream == null) {
            return new Properties();
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public Properties loadDataSourceProperties(String logicDbName) throws IOException {
        String fileName = "/datasources/datasource-" + logicDbName + ".properties";
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}

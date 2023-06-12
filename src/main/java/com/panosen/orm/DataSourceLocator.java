package com.panosen.orm;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

import java.io.IOException;
import java.util.Properties;

public class DataSourceLocator {
    public static DataSource getDataSource(String logicDbName, Properties extraProperties) throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties builtInProperties = PropertiesLoader.loadBuiltInProperties();
        Properties projectProperties = propertiesLoader.loadDataSourceProperties();
        Properties databaseProperties = propertiesLoader.loadDataSourceProperties(logicDbName);

        PoolConfiguration poolConfiguration = DataSourceFactory.parsePoolProperties(combine(builtInProperties, projectProperties, databaseProperties, extraProperties));

        return new DataSource(poolConfiguration);
    }

    private static Properties combine(Properties... args) {
        Properties properties = new Properties();
        for (Properties arg : args) {
            properties.putAll(arg);
        }
        return properties;
    }
}

package com.panosen.orm;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

import java.io.IOException;
import java.util.Properties;

public class DataSourceLocator {
    public static DataSource getDataSource(String logicDbName) throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties builtInProperties = PropertiesLoader.loadBuiltInProperties();
        Properties properties = propertiesLoader.loadDataSourceProperties();
        Properties dataSourceProperties = propertiesLoader.loadDataSourceProperties(logicDbName);

        PoolConfiguration poolConfiguration = DataSourceFactory.parsePoolProperties(combine(builtInProperties, properties, dataSourceProperties));

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

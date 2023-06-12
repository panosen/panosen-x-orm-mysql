package com.panosen.orm;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DalClientFactory {

    private final static String DAL_SHUTDOWN_THREAD = "DAL-SHUTDOWN-THREAD";

    private final static Map<String, DalClient> DalClientMap = new ConcurrentHashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Thread.currentThread().setName(DAL_SHUTDOWN_THREAD)));
    }

    public static DalClient getClient(String logicDbName) throws IOException {
        if (DalClientMap.containsKey(logicDbName)) {
            return DalClientMap.get(logicDbName);
        }

        synchronized (DalClientFactory.class) {
            if (DalClientMap.containsKey(logicDbName)) {
                return DalClientMap.get(logicDbName);
            }

            DalClient dalClient = getClientNow(logicDbName);
            DalClientMap.put(logicDbName, dalClient);
            return dalClient;
        }
    }

    private static DalClient getClientNow(String logicDbName) throws IOException {
        return getClientNow(logicDbName, new Properties());
    }

    private static DalClient getClientNow(String logicDbName, Properties properties) throws IOException {
        DataSource dataSource = DataSourceLocator.getDataSource(logicDbName, properties);
        return new DalClient(dataSource);
    }

    public static void registerClient(String logicDbName, Properties properties) throws IOException {
        DalClient dalClient = getClientNow(logicDbName, properties);
        DalClientMap.putIfAbsent(logicDbName, dalClient);
    }
}

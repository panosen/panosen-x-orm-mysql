package com.panosen.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DalClientFactoryListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(DalClientFactoryListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("DAL is starting up.");

        DalClientFactory.startup();

        DalClientFactory.ignite();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("DAL is shutting down");

        DalClientFactory.shutdown();
    }
}

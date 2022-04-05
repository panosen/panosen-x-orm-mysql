package com.panosen.orm.tasks;

import com.panosen.orm.DalClient;
import com.panosen.orm.DalClientFactory;
import com.panosen.orm.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class SingleTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final EntityManager entityManager;

    protected DalClient dalClient;

    public SingleTask(EntityManager entityManager) throws IOException {
        this.entityManager = entityManager;

        dalClient = DalClientFactory.getClient(entityManager.getDataSourceName());
    }

//    protected DataSource getDataSource() throws IOException {
//        return DataSourceLocator.getDataSource(entityManager.getDataSourceName());
//    }
}

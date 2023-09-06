package com.panosen.orm;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.mapper.EntityMapper;
import com.panosen.orm.mapper.Mapper;

import java.io.IOException;
import java.util.List;

public class DalQueryDao {

    private final DalClient dalClient;

    public DalQueryDao(String dataSourceName) throws IOException {
        this.dalClient = DalClientFactory.getClient(dataSourceName);
    }

    public <TEntity> List<TEntity> queryList(SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectList(this.dalClient, selectSqlBuilder, mapper);
    }

    public <TEntity> TEntity querySingle(SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectSingle(this.dalClient, selectSqlBuilder, mapper);
    }

    public <TEntity> List<TEntity> queryList(SelectSqlBuilder selectSqlBuilder, Class<TEntity> clazz) throws Exception {
        EntityManager entityManager = EntityManagerFactory.getOrCreateManager(clazz);

        Mapper<TEntity> mapper = new EntityMapper<>(entityManager);

        return DalClientExtension.selectList(this.dalClient, selectSqlBuilder, mapper);
    }

    public <TEntity> TEntity querySingle(SelectSqlBuilder selectSqlBuilder, Class<TEntity> clazz) throws Exception {
        EntityManager entityManager = EntityManagerFactory.getOrCreateManager(clazz);

        Mapper<TEntity> mapper = new EntityMapper<>(entityManager);

        return DalClientExtension.selectSingle(this.dalClient, selectSqlBuilder, mapper);
    }
}

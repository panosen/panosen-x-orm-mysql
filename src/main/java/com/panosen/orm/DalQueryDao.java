package com.panosen.orm;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.mapper.Mapper;

import java.util.List;

public class DalQueryDao {

    private final DalClient dalClient;

    public DalQueryDao(DalClient dalClient) {
        this.dalClient = dalClient;
    }

    public static <TEntity> List<TEntity> queryList(DalClient dalClient, SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectList(dalClient, selectSqlBuilder, mapper);
    }

    public static <TEntity> TEntity querySingle(DalClient dalClient, SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectSingle(dalClient, selectSqlBuilder, mapper);
    }

    public <TEntity> List<TEntity> queryList(SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectList(this.dalClient, selectSqlBuilder, mapper);
    }

    public <TEntity> TEntity querySingle(SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        return DalClientExtension.selectSingle(this.dalClient, selectSqlBuilder, mapper);
    }
}

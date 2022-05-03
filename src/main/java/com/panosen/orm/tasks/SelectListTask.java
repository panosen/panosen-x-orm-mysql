package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.DalClientExtension;
import com.panosen.orm.EntityManager;
import com.panosen.orm.mapper.EntityMapper;
import com.panosen.orm.mapper.Mapper;

import java.io.IOException;
import java.util.List;

public class SelectListTask extends SelectTask {

    public SelectListTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> List<TEntity> selectList(TEntity entity) throws Exception {
        SelectSqlBuilder selectSqlBuilder = buildSelectSqlBuilder(entity);
        selectSqlBuilder.from(entityManager.getTableName());
        return selectList(selectSqlBuilder);
    }

    public <TEntity> List<TEntity> selectListByPage(TEntity entity, Integer pageIndex, Integer pageSize) throws Exception {
        SelectSqlBuilder selectSqlBuilder = buildSelectSqlBuilder(entity);
        selectSqlBuilder.from(entityManager.getTableName());
        selectSqlBuilder.limit(pageIndex * pageSize, pageSize);
        return selectList(selectSqlBuilder);
    }

    public <TEntity> List<TEntity> selectList(SelectSqlBuilder selectSqlBuilder) throws Exception {
        Mapper<TEntity> mapper = new EntityMapper<>(entityManager);
        return DalClientExtension.selectList(dalClient, selectSqlBuilder, mapper);
    }
}

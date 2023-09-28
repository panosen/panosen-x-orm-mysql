package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.DalClientExtension;
import com.panosen.orm.EntityManager;
import com.panosen.orm.mapper.EntityMapper;

import java.io.IOException;

public class SelectSingleTask extends SelectTask {

    public SelectSingleTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> TEntity selectSingle(TEntity entity) throws Exception {
        SelectSqlBuilder selectSqlBuilder = buildSelectSqlBuilder(entity);

        selectSqlBuilder.from(entityManager.getTableName());
        selectSqlBuilder.limit(1);

        return selectSingle(selectSqlBuilder);
    }

    public <TEntity> TEntity selectSingle(SelectSqlBuilder selectSqlBuilder) throws Exception {
        EntityMapper<TEntity> mapper = new EntityMapper<>(entityManager);
        return DalClientExtension.selectSingle(dalClient, selectSqlBuilder, mapper);
    }
}

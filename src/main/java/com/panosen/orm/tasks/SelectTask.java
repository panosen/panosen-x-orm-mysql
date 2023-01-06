package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.codedom.mysql.builder.WhereBuilder;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.Map;

public abstract class SelectTask extends SingleTask {

    public SelectTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    protected <TEntity> SelectSqlBuilder buildSelectSqlBuilder(TEntity entity) throws IllegalAccessException {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder()
                .from(entityManager.getTableName());

        if (entity == null) {
            return selectSqlBuilder;
        }

        WhereBuilder whereBuilder = selectSqlBuilder.where();
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            Object value = entry.getValue().getField().get(entity);
            if (value == null) {
                continue;
            }
            whereBuilder.equal(entry.getKey(), entry.getValue().getType(), value);
        }
        return selectSqlBuilder;
    }
}

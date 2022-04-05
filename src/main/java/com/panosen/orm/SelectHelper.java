package com.panosen.orm;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.codedom.mysql.builder.WhereBuilder;

import java.util.Map;

public class SelectHelper {

    private final EntityManager entityManager;

    public SelectHelper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <TEntity> SelectSqlBuilder buildSelectSqlBuilder(TEntity entity) throws IllegalAccessException {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder()
                .from(entityManager.getTableName());

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

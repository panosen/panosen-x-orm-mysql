package com.panosen.orm.tasks;

import com.google.common.collect.Lists;
import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.List;

public class SelectListByPrimaryKeysTask extends SelectListTask {
    public SelectListByPrimaryKeysTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <T, TEntity> List<TEntity> selectListByPrimaryKeys(List<T> primaryKeys) throws Exception {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder();
        selectSqlBuilder.from(entityManager.getTableName());

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return null;
        }

        selectSqlBuilder.where()
                .in(primaryKeyColumn.getColumnName(), primaryKeyColumn.getType(), Lists.newArrayList(primaryKeys));

        return selectList(selectSqlBuilder);
    }
}

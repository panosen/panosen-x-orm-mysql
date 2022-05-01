package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;

public class SelectSingleByIdTask extends SelectSingleTask {

    public SelectSingleByIdTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> TEntity selectSingleById(Object id) throws Exception {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder()
                .from(entityManager.getTableName())
                .limit(1);

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return null;
        }

        selectSqlBuilder.where()
                .equal(primaryKeyColumn.getColumnName(), primaryKeyColumn.getType(), id);

        return selectSingle(selectSqlBuilder);
    }
}

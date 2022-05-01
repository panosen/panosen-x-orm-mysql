package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.DeleteSqlBuilder;
import com.panosen.codedom.mysql.engine.DeleteSqlEngine;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;

public class DeleteTask extends SingleTask {

    public DeleteTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int execute(TEntity entity) throws Exception {

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return 0;
        }

        Object value = primaryKeyColumn.getField().get(entity);
        if (value == null) {
            return 0;
        }

        DeleteSqlBuilder updateSqlBuilder = new DeleteSqlBuilder()
                .from(entityManager.getTableName());

        updateSqlBuilder.where()
                .equal(primaryKeyColumn.getColumnName(), primaryKeyColumn.getType(), value);

        GenerationResponse generationResponse = new DeleteSqlEngine().generate(updateSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        return dalClient.update(sql, parameters, null);
    }
}

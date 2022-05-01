package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.StatementsBuilder;
import com.panosen.codedom.mysql.builder.UpdateSqlBuilder;
import com.panosen.codedom.mysql.builder.WhereBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.UpdateSqlEngine;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.Map;

public class UpdateTask extends SingleTask {

    public UpdateTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int execute(TEntity entity) throws Exception {

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return 0;
        }

        Object primaryKeyValue = primaryKeyColumn.getField().get(entity);
        if (primaryKeyValue == null) {
            return 0;
        }

        UpdateSqlBuilder updateSqlBuilder = new UpdateSqlBuilder()
                .table(entityManager.getTableName());

        StatementsBuilder setBuilder = updateSqlBuilder.set();
        WhereBuilder whereBuilder = updateSqlBuilder.where();
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            Object value = entry.getValue().getField().get(entity);
            if (value == null) {
                continue;
            }
            if (primaryKeyColumn.getColumnName().contains(entry.getKey())) {
                whereBuilder.equal(entry.getKey(), entry.getValue().getType(), value);
            } else {
                setBuilder.set(entry.getKey(), entry.getValue().getType(), value);
            }
        }

        GenerationResponse generationResponse = new UpdateSqlEngine().generate(updateSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        return dalClient.update(sql, parameters, null);
    }
}

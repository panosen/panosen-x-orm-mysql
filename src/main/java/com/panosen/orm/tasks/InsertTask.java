package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.InsertSqlBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.InsertSqlEngine;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;
import com.panosen.orm.KeyHolder;

import java.io.IOException;
import java.util.Map;

public class InsertTask extends SingleTask {

    public InsertTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int execute(TEntity entity) throws Exception {
        return execute(entity, null);
    }

    public <TEntity> int execute(TEntity entity, KeyHolder keyHolder) throws Exception {
        InsertSqlBuilder insertSqlBuilder = new InsertSqlBuilder()
                .intoTable(entityManager.getTableName());

        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            Object value = entry.getValue().getField().get(entity);
            if (value == null) {
                continue;
            }
            insertSqlBuilder.value(entry.getKey(), entry.getValue().getType(), value);
        }

        GenerationResponse generationResponse = new InsertSqlEngine().generate(insertSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());
        for (Parameter parameter : parameters) {
            logger.info(parameter.getValue().toString());
        }

        return dalClient.update(sql, parameters, keyHolder);
    }
}

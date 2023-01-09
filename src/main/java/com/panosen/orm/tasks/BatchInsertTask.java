package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.BatchBuilder;
import com.panosen.codedom.mysql.builder.BatchInsertSqlBuilder;
import com.panosen.codedom.mysql.engine.BatchInsertSqlEngine;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;
import com.panosen.orm.KeyHolder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BatchInsertTask extends SingleTask {

    public BatchInsertTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int batchInsert(List<TEntity> entityList) throws Exception {
        return batchInsert(entityList, null);
    }

    public <TEntity> int batchInsert(List<TEntity> entityList, KeyHolder keyHolder) throws Exception {
        BatchInsertSqlBuilder batchInsertSqlBuilder = new BatchInsertSqlBuilder()
                .intoTable(entityManager.getTableName());

        for (TEntity entity : entityList) {
            BatchBuilder batch = batchInsertSqlBuilder.addBatch();

            for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
                Object value = entry.getValue().getField().get(entity);
                if (value == null) {
                    continue;
                }
                batchInsertSqlBuilder.addColumn(entry.getKey(), entry.getValue().getType());
                batch.value(entry.getKey(), value);
            }
        }

        GenerationResponse generationResponse = new BatchInsertSqlEngine().generate(batchInsertSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        return dalClient.update(sql, parameters, keyHolder);
    }


}

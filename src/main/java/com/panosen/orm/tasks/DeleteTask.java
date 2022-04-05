package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.DeleteSqlBuilder;
import com.panosen.codedom.mysql.builder.WhereBuilder;
import com.panosen.codedom.mysql.engine.DeleteSqlEngine;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DeleteTask extends SingleTask {

    public DeleteTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int execute(TEntity entity) throws Exception {
        DeleteSqlBuilder updateSqlBuilder = new DeleteSqlBuilder()
                .from(entityManager.getTableName());

        List<String> primaryKeyList = entityManager.getPrimaryKeyList();

        WhereBuilder whereBuilder = updateSqlBuilder.where();
        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            Object value = entry.getValue().getField().get(entity);
            if (value == null) {
                continue;
            }
            if (primaryKeyList.contains(entry.getKey())) {
                whereBuilder.equal(entry.getKey(), entry.getValue().getType(), value);
            }
        }

        GenerationResponse generationResponse = new DeleteSqlEngine().generate(updateSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        return dalClient.update(sql, parameters, null);
    }
}

package com.panosen.orm.tasks;

import com.google.common.collect.Lists;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.DeleteSqlBuilder;
import com.panosen.codedom.mysql.engine.DeleteSqlEngine;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.List;

public class DeleteByPrimaryKeysTask extends SingleTask {
    public DeleteByPrimaryKeysTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TId> int deleteByPrimaryKeys(List<TId> primaryKeys) throws Exception {
        DeleteSqlBuilder deleteSqlBuilder = new DeleteSqlBuilder()
                .from(entityManager.getTableName());

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return 0;
        }

        deleteSqlBuilder.where()
                .in(primaryKeyColumn.getColumnName(), primaryKeyColumn.getType(), Lists.newArrayList(primaryKeys));

        GenerationResponse generationResponse = new DeleteSqlEngine().generate(deleteSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        return dalClient.update(sql, parameters, null);
    }
}

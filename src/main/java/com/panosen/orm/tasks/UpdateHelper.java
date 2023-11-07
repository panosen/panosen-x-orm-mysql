package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.ConditionsBuilder;
import com.panosen.codedom.mysql.builder.StatementsBuilder;
import com.panosen.codedom.mysql.builder.UpdateSqlBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.UpdateSqlEngine;
import com.panosen.orm.DalClient;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UpdateHelper {

    private static Logger logger = LoggerFactory.getLogger(UpdateHelper.class);

    public static <TEntity> int execute(DalClient dalClient, EntityManager entityManager, TEntity entity) throws Exception {

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
        ConditionsBuilder whereBuilder = updateSqlBuilder.where();
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
        for (Parameter parameter : parameters) {
            logger.info(parameter.getValue().toString());
        }

        return dalClient.update(sql, parameters, null);
    }
}

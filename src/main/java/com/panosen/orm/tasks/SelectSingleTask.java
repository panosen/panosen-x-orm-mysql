package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.SelectSqlEngine;
import com.panosen.orm.EntityManager;
import com.panosen.orm.SelectHelper;
import com.panosen.orm.extractor.EntityExtractor;
import com.panosen.orm.mapper.EntityMapper;

import java.io.IOException;

public class SelectSingleTask extends SingleTask {

    private final SelectHelper selectHelper;

    public SelectSingleTask(EntityManager entityManager) throws IOException {
        super(entityManager);

        this.selectHelper = new SelectHelper(entityManager);
    }

    public <TEntity> TEntity selectSingle(TEntity entity) throws Exception {
        SelectSqlBuilder selectSqlBuilder = selectHelper.buildSelectSqlBuilder(entity);

        selectSqlBuilder
                .from(entityManager.getTableName())
                .limit(1);

        return selectSingle(selectSqlBuilder);
    }

    public <TEntity> TEntity selectSingle(SelectSqlBuilder selectSqlBuilder) throws Exception {
        GenerationResponse generationResponse = new SelectSqlEngine().generate(selectSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        final EntityExtractor<TEntity> extractor = new EntityExtractor<>(new EntityMapper<>(entityManager));

        return dalClient.query(sql, parameters, extractor);
    }
}

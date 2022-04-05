package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.SelectSqlEngine;
import com.panosen.orm.EntityManager;
import com.panosen.orm.SelectHelper;
import com.panosen.orm.extractor.EntityListExtractor;
import com.panosen.orm.mapper.EntityMapper;

import java.io.IOException;
import java.util.List;

public class SelectListTask extends SingleTask {

    private final SelectHelper selectHelper;

    public SelectListTask(EntityManager entityManager) throws IOException {
        super(entityManager);

        this.selectHelper = new SelectHelper(entityManager);
    }

    public <TEntity> List<TEntity> selectList(TEntity entity) throws Exception {
        SelectSqlBuilder selectSqlBuilder = selectHelper.buildSelectSqlBuilder(entity);

        selectSqlBuilder.from(entityManager.getTableName());

        return selectList(selectSqlBuilder);
    }

    public <TEntity> List<TEntity> selectList(SelectSqlBuilder selectSqlBuilder) throws Exception {
        GenerationResponse generationResponse = new SelectSqlEngine().generate(selectSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());

        final EntityListExtractor<TEntity> extractor = new EntityListExtractor<>(new EntityMapper<>(entityManager));

        return dalClient.query(sql, parameters, extractor);
    }
}

package com.panosen.orm;

import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.codedom.mysql.engine.GenerationResponse;
import com.panosen.codedom.mysql.engine.SelectSqlEngine;
import com.panosen.orm.extractor.EntityExtractor;
import com.panosen.orm.extractor.EntityListExtractor;
import com.panosen.orm.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DalClientExtension {

    private final static Logger logger = LoggerFactory.getLogger(DalClientExtension.class);

    public static <TEntity> List<TEntity> selectList(DalClient dalClient, SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        GenerationResponse generationResponse = new SelectSqlEngine().generate(selectSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());
        for (Parameter parameter : parameters) {
            logger.info(parameter.getValue().toString());
        }

        final EntityListExtractor<TEntity> extractor = new EntityListExtractor<>(mapper);

        return dalClient.query(sql, parameters, extractor);
    }

    public static  <TEntity> TEntity selectSingle(DalClient dalClient, SelectSqlBuilder selectSqlBuilder, Mapper<TEntity> mapper) throws Exception {
        GenerationResponse generationResponse = new SelectSqlEngine().generate(selectSqlBuilder);
        String sql = generationResponse.getSql();
        logger.info("sql = " + sql);

        Parameters parameters = generationResponse.getParameters();
        logger.info("parameters.size() = " + parameters.size());
        for (Parameter parameter : parameters) {
            logger.info(parameter.getValue().toString());
        }

        final EntityExtractor<TEntity> extractor = new EntityExtractor<>(mapper);

        return dalClient.query(sql, parameters, extractor);
    }
}

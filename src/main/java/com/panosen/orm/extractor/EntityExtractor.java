package com.panosen.orm.extractor;

import com.panosen.orm.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityExtractor<TEntity> implements Extractor<TEntity> {

    private final Mapper<TEntity> mapper;

    public EntityExtractor(Mapper<TEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    public TEntity extract(ResultSet resultSet) throws SQLException, ReflectiveOperationException {
        if (!resultSet.next()) {
            return null;
        }
        return mapper.map(resultSet);
    }
}

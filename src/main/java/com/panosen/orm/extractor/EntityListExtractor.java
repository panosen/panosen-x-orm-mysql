package com.panosen.orm.extractor;

import com.panosen.orm.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityListExtractor<TEntity> implements Extractor<List<TEntity>> {

    private final Mapper<TEntity> mapper;

    public EntityListExtractor(Mapper<TEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TEntity> extract(ResultSet resultSet) throws SQLException, ReflectiveOperationException {
        List<TEntity> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(mapper.map(resultSet));
        }

        return result;
    }
}

package com.panosen.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<TEntity> {
    TEntity map(ResultSet resultSet) throws SQLException, ReflectiveOperationException;
}


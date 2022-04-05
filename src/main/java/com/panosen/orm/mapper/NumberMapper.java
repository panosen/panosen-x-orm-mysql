package com.panosen.orm.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class NumberMapper implements Mapper<Number> {

    public Number map(ResultSet resultSet) throws SQLException {

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        switch (resultSetMetaData.getColumnType(1)) {
            case Types.INTEGER:
                return resultSet.getInt(1);

            case Types.BIGINT:
                return resultSet.getLong(1);

            default:
                return null;
        }
    }
}

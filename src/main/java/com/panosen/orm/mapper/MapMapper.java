package com.panosen.orm.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapMapper implements Mapper<Map<String, Object>> {

    public Map<String, Object> map(ResultSet resultSet) throws SQLException {

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        String[] columns = new String[resultSetMetaData.getColumnCount()];
        for (int index = 0; index < columns.length; index++) {
            columns[index] = resultSetMetaData.getColumnLabel(index + 1);
        }

        Map<String, Object> map = new LinkedHashMap<>(columns.length);
        for (int index = 0; index < columns.length; index++) {
            map.put(columns[index], resultSet.getObject(index + 1));
        }
        return map;
    }
}

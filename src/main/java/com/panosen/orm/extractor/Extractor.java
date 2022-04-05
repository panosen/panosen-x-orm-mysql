package com.panosen.orm.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Extractor<T> {
    T extract(ResultSet resultSet) throws SQLException, ReflectiveOperationException;
}

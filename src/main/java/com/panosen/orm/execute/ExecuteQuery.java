package com.panosen.orm.execute;

import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.orm.extractor.Extractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExecuteQuery<T> extends Execute<T> {

    private final Extractor<T> extractor;
    private final String sql;
    private final Parameters parameters;

    public ExecuteQuery(String sql, Parameters parameters, Extractor<T> extractor) {
        this.sql = sql;
        this.parameters = parameters;
        this.extractor = extractor;
    }

    @Override
    public T execute(Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        for (Parameter parameter : parameters) {
            preparedStatement.setObject(parameter.getIndex(), parameter.getValue(), parameter.getDbType());
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        T result = extractor.extract(resultSet);

        resultSet.close();

        return result;
    }
}

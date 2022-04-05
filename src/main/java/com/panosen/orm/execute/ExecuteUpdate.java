package com.panosen.orm.execute;

import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;
import com.panosen.orm.KeyHolder;
import com.panosen.orm.extractor.EntityListExtractor;
import com.panosen.orm.mapper.NumberMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExecuteUpdate extends Execute<Integer> {

    public final String sql;
    public final Parameters parameters;
    private final KeyHolder keyHolder;

    private final EntityListExtractor<Number> extractor = new EntityListExtractor<>(new NumberMapper());

    public ExecuteUpdate(String sql, Parameters parameters, KeyHolder keyHolder) {
        this.sql = sql;
        this.parameters = parameters;
        this.keyHolder = keyHolder;
    }

    @Override
    public Integer execute(Connection connection) throws Exception {

        PreparedStatement preparedStatement;

        if (keyHolder == null) {
            preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        } else {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }

        for (Parameter parameter : parameters) {
            preparedStatement.setObject(parameter.getIndex(), parameter.getValue(), parameter.getDbType());
        }

        int rows = preparedStatement.executeUpdate();

        if (keyHolder == null) {
            return rows;
        }

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet != null) {
            keyHolder.setPrimaryKeys(extractor.extract(resultSet));
            resultSet.close();
        }

        return rows;
    }
}

package com.panosen.orm.execute;


import com.panosen.codedom.mysql.Parameter;
import com.panosen.codedom.mysql.Parameters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class ExecuteBatchParams extends Execute<int[]> {

    public final String sql;
    public final Parameters[] parametersList;

    public ExecuteBatchParams(String sql, Parameters[] parametersList) {
        this.sql = sql;
        this.parametersList = parametersList;
    }

    @Override
    public int[] execute(Connection connection) throws Exception {

        PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        for (Parameters parameters : parametersList) {
            for (Parameter parameter : parameters) {
                preparedStatement.setObject(parameter.getIndex(), parameter.getValue(), parameter.getDbType());
            }
            preparedStatement.addBatch();
        }

        return preparedStatement.executeBatch();
    }
}

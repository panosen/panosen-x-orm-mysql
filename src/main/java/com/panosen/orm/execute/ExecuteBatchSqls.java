package com.panosen.orm.execute;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExecuteBatchSqls extends Execute<int[]> {

    private final String[] sqls;

    public ExecuteBatchSqls(String[] sqls) {
        this.sqls = sqls;
    }

    @Override
    public int[] execute(Connection connection) throws Exception {
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        for (String sql : sqls) {
            statement.addBatch(sql);
        }

        return statement.executeBatch();
    }
}

package com.panosen.orm;

import com.panosen.codedom.mysql.Parameters;
import com.panosen.orm.execute.*;
import com.panosen.orm.extractor.Extractor;

import javax.sql.DataSource;
import java.sql.Connection;

public class DalClient {

    protected final ConnectionManager connManager;

    public DalClient(DataSource dataSource) {
        connManager = new ConnectionManager(dataSource);
    }

    public <T> T query(String sql, Parameters parameters, Extractor<T> extractor)
            throws Exception {
        return doInConnection(new ExecuteQuery<>(sql, parameters, extractor));
    }

    public Integer update(String sql, Parameters parameters, KeyHolder keyHolder) throws Exception {
        return doInConnection(new ExecuteUpdate(sql, parameters, keyHolder));
    }

    public int[] batchUpdate(String[] sqls) throws Throwable {
        return doInTransaction(new ExecuteBatchSqls(sqls));
    }

    public int[] batchUpdate(String sql, Parameters[] parametersList) throws Throwable {
        return doInTransaction(new ExecuteBatchParams(sql, parametersList));
    }

    public void execute(ExecuteCommand.Command command) throws Throwable {
        doInTransaction(new ExecuteCommand(command));
    }


    private <T> T doInTransaction(Execute<T> action) throws Exception {

        Connection connection = connManager.getConnection();

        try {
            connection.setAutoCommit(false);

            T result = action.execute(connection);

            connection.commit();
            connection.setAutoCommit(true);
            connection.close();

            return result;
        } catch (Throwable e) {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
            return null;
        }
    }

    private <T> T doInConnection(Execute<T> action) throws Exception {

        Connection connection = connManager.getConnection();

        T t = action.execute(connection);
        if (connection.getAutoCommit()) {
            connection.close();
        }
        return t;
    }
}

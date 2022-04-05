package com.panosen.orm.execute;

import java.sql.Connection;

public abstract class Execute<T> {

    public abstract T execute(Connection connection) throws Exception;
}


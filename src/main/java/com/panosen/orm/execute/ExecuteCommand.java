package com.panosen.orm.execute;

import java.sql.Connection;

public class ExecuteCommand extends Execute<Object> {

    protected final Command command;

    public ExecuteCommand(Command command) {
        this.command = command;
    }

    @Override
    public Object execute(Connection connection) throws Exception {
        this.command.execute();
        return null;
    }

    public interface Command {
        void execute() throws Exception;
    }
}

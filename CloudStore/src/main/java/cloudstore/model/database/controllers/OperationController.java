package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class OperationController {

    private final Connector connector = new Connector();
    private final PreparedStatement statement;

    public OperationController(String prepared, Object...args) throws SQLException {
        this.statement = this.connector.connect().prepareStatement(prepared);
        int index = 1;
        for (Object arg : args) {
            this.statement.setObject(index++, arg);
        }
    }
    public boolean execute() throws SQLException {
        final boolean result = this.statement.execute();
        this.statement.close();
        this.statement.getConnection().close();
        return result;
    }
}

package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperationController {

  private final Connector connector = new Connector();
  private final PreparedStatement statement;

  public OperationController(String prepared, Object... args) throws SQLException {
    this.statement =
        this.connector.connect().prepareStatement(prepared, Statement.RETURN_GENERATED_KEYS);
    int index = 1;
    for (Object arg : args) {
      this.statement.setObject(index++, arg);
    }
  }

  public <T> T executeAndGetGeneratedKey(final Class<T> type)
      throws SQLException {
    System.out.println("Query = " + this.statement.toString());
    this.statement.executeUpdate();
    final ResultSet set = this.statement.getGeneratedKeys();
    T key = null;
    if (set.next()) {
      System.out.println("SET = " + set);
      key = (T) set.getObject(1);
    }
    this.statement.close();
    return key;
  }

  public boolean execute() throws SQLException {
    final boolean result = this.statement.execute();
    this.statement.close();
    return result;
  }
}

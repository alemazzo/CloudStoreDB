package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;

import java.sql.*;

public class DatabaseOperation {

  private final Connection connection = new Connector().connect();
  private final PreparedStatement statement;

  public DatabaseOperation(final String prepared, final Object... args) throws SQLException {
    this.statement =
        this.connection.prepareStatement(prepared, Statement.RETURN_GENERATED_KEYS);
    this.buildStatement(args);
  }

  private void buildStatement(final Object ... args) throws SQLException {
    int index = 1;
    for (Object arg : args) {
      this.statement.setObject(index++, arg);
    }
  }

  public void executeUpdate() throws SQLException {
    System.out.println("AAA = " + this.statement.toString());
    this.statement.executeUpdate();
    this.statement.close();
  }

  public <T> T executeAndGetGeneratedKey(final Class<T> type)
      throws SQLException {
    System.out.println("Query = " + this.statement.toString());
    this.statement.executeUpdate();
    final ResultSet set = this.statement.getGeneratedKeys();
    T key = null;
    if (set.next()) {
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

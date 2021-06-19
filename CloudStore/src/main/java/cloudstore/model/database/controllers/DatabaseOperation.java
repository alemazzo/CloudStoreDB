package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;

import java.sql.*;

/** An operation instance that offer some tools. */
public class DatabaseOperation {

  private final Connection connection = new Connector().connect();
  private final PreparedStatement statement;

  public DatabaseOperation(final String prepared, final Object... args) throws SQLException {
    this.statement = this.connection.prepareStatement(prepared, Statement.RETURN_GENERATED_KEYS);
    this.buildStatement(args);
  }

  /**
   * Build the statement from the Object passed throug variable arguments.
   *
   * @param args the elements
   * @throws SQLException exception
   */
  private void buildStatement(final Object... args) throws SQLException {
    int index = 1;
    for (Object arg : args) {
      this.statement.setObject(index++, arg);
    }
  }

  /**
   * Execute an update operation.
   *
   * @throws SQLException exception
   */
  public void executeUpdate() throws SQLException {
    System.out.println(this.statement.toString());
    this.statement.executeUpdate();
    this.statement.close();
  }

  /**
   * Execute the update and retrieve the key for the entry.
   *
   * @param type the type of the key
   * @param <T> the type of the key
   * @return the generated key
   * @throws SQLException exeption
   */
  public <T> T executeAndGetGeneratedKey(final Class<T> type) throws SQLException {
    System.out.println(this.statement.toString());
    this.statement.executeUpdate();
    final ResultSet set = this.statement.getGeneratedKeys();
    T key = null;
    if (set.next()) {
      key = (T) set.getObject(1);
    }
    this.statement.close();
    return key;
  }

  /**
   * Execute the query.
   *
   * @return true if the operation has been made succesfully, false otherwise
   * @throws SQLException exception
   */
  public boolean execute() throws SQLException {
    System.out.println(this.statement.toString());
    final boolean result = this.statement.execute();
    this.statement.close();
    return result;
  }
}

package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.mapper.EntityMapper;
import cloudstore.model.database.query.QueryResultObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * A simple DataBase query executor.
 *
 * @param <T> the type of the result
 */
public class DatabaseQuery<T extends QueryResultObject> {

  private final EntityMapper<T> mapper;
  private final PreparedStatement statement;

  public DatabaseQuery(final Class<T> type, final String query, final Object... args)
      throws SQLException {
    this.mapper = new EntityMapper<>(type);
    this.statement = new Connector().connect().prepareStatement(query);
    int index = 1;
    for (Object arg : args) {
      statement.setObject(index++, arg);
    }
  }

  /**
   * Get multiple results.
   *
   * @return the results
   * @throws SQLException exception
   */
  public Set<T> getResultsSet() throws SQLException {
    final ResultSet results = this.statement.executeQuery();
    final Set<T> list = this.mapper.fromResultSet(results);
    statement.close();
    return list;
  }

  /**
   * Get a single result.
   *
   * @return the single result object
   * @throws SQLException exception
   */
  public T getSingleValue() throws SQLException {
    return this.getResultsSet().stream().findFirst().get();
  }
}

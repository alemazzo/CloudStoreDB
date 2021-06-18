package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.mapper.EntityMapper;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class DatabaseQuery<T extends QueryObjectResult> {

  private final EntityMapper<T> mapper;
  private final PreparedStatement statement;

  public DatabaseQuery(final Class<T> type, final String query, final Object ... args) throws SQLException {
    this.mapper = new EntityMapper<>(type);
    this.statement = new Connector().connect().prepareStatement(query);
    int index = 1;
    for (Object arg : args) {
      statement.setObject(index++, arg);
    }
  }

  public Set<T> getResults() throws SQLException {
    final ResultSet results = this.statement.executeQuery();
    final Set<T> list = this.mapper.fromResultSet(results);
    statement.close();
    statement.getConnection().close();
    return list;
  }

  public T getSingleValue() throws SQLException {
    final T elem = this.getResults().stream().findFirst().get();
    this.statement.close();
    this.statement.getConnection().close();
    return elem;
  }
}

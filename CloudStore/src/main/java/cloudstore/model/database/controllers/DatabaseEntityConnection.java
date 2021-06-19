package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.entities.Entity;
import cloudstore.model.database.mapper.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * The connection of an entity. Use an internal mapper and make possibile some operation on the
 * single entities.
 *
 * @param <T> the Entity
 */
public class DatabaseEntityConnection<T extends Entity> {

  private final ObjectMapper<T> mapper;
  private T object;

  public DatabaseEntityConnection(final Class<T> type) {
    try {
      this.object = type.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.mapper = new ObjectMapper<T>(type);
  }

  /**
   * Get all instance of the entity from the database.
   *
   * @return a set with all the instances.
   * @throws SQLException exception
   */
  public Set<T> getAll() throws SQLException {
    final Connection connection = new Connector().connect();
    final String query = "select * from " + this.object.getTableName();
    final PreparedStatement statement = connection.prepareStatement(query);
    System.out.println(statement);
    final ResultSet results = statement.executeQuery();
    final Set<T> list = this.mapper.fromResultSet(results);
    statement.close();
    connection.close();
    return list;
  }

  public DatabaseOperation insert(Object... args) throws SQLException {
    return new DatabaseOperation(this.object.getInsertQuery(), args);
  }
}

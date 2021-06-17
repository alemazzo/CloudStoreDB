package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.entities.Entity;
import cloudstore.model.database.mapper.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class EntityController<T extends Entity> {

  private T object;
  private final Connector connector = new Connector();
  private final EntityMapper<T> mapper;
  private final String tableName;

  public EntityController(final Class<T> type, final String tableName) {
    try {
      this.object = type.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.mapper = new EntityMapper<T>(type);
    this.tableName = tableName;
  }

  public Connector getConnector() {
    return this.connector;
  }

  public EntityMapper<T> getEntityMapper() {
    return this.mapper;
  }

  public Set<T> getAll() throws SQLException {
    final Connection connection = this.getConnector().connect();
    final String query = "select * from " + this.tableName;
    final PreparedStatement statement = connection.prepareStatement(query);
    final ResultSet results = statement.executeQuery();
    final Set<T> list = this.mapper.fromResultSet(results);
    statement.close();
    connection.close();
    return list;
  }

  public OperationController insert(Object... args) throws SQLException {
    System.out.println("QUERY = " + this.object.getInsertQuery() + " parameters = " + args);
    return new OperationController(this.object.getInsertQuery(), args);
  }
}

package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryResultObject;

/** An entity of the DataBase. */
public abstract class Entity extends QueryResultObject {

  /**
   * Get the name of the table.
   *
   * @return the table's name
   */
  public abstract String getTableName();

  /**
   * Get the template for an insert query.
   *
   * @return the prepared insert query
   */
  public abstract String getInsertQuery();
}

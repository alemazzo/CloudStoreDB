package cloudstore.model.database.mapper;

import cloudstore.model.database.query.QueryResultObject;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The Entity Mapper that handle the conversione from a query result to a Java object using
 * reflection and building the object through his public field.
 *
 * @param <T> The type of object
 */
public class ObjectMapper<T extends QueryResultObject> {

  private final Class<T> type;

  public ObjectMapper(final Class<T> type) {
    this.type = type;
  }

  /**
   * Retrieve an object instance from a single Result.
   *
   * @param result the result
   * @return the created object
   * @throws SQLException exception
   */
  public T fromSingleResult(final ResultSet result) throws SQLException {
    T elem = null;
    try {
      elem = this.type.getConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    final T finalElem = elem;
    Arrays.stream(elem.getClass().getFields()).forEach(x -> buildField(result, finalElem, x));
    return elem;
  }

  private void buildField(final ResultSet result, final T elem, final Field x) {
    try {
      x.set(elem, result.getObject(x.getName()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrive a set of object instances from a ResultsSet.
   *
   * @param results the results set
   * @return the Set of Object
   * @throws SQLException exception
   */
  public Set<T> fromResultSet(final ResultSet results) throws SQLException {
    final Set<T> res = new HashSet<>();
    while (results.next()) res.add(this.fromSingleResult(results));
    return res;
  }
}

package cloudstore.model.database.mapper;

import cloudstore.model.database.query.QueryObjectResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EntityMapper<T extends QueryObjectResult> {

  private final Class<T> type;

  public EntityMapper(Class<T> type) {
    this.type = type;
  }

  public T fromResult(ResultSet result) throws SQLException {
    T elem = null;
    try {
      elem = (T) Class.forName(type.getName()).getConstructor().newInstance();
      //elem = this.type.getConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    final T finalElem = elem;
    Arrays.stream(elem.getClass().getFields())
        .forEach(
            x -> {
              try {
                x.set(finalElem, result.getObject(x.getName()));
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    return elem;
  }

  public Set<T> fromResultSet(ResultSet results) throws SQLException {
    final Set<T> res = new HashSet<>();
    while (results.next()) res.add(this.fromResult(results));
    return res;
  }
}

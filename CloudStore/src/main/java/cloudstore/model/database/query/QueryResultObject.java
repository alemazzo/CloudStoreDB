package cloudstore.model.database.query;

import java.util.Arrays;
import java.util.stream.Collectors;

/** An object that is a result of a query. */
public abstract class QueryResultObject {

  /**
   * A personalized toString that print each public field of the object.
   *
   * @return the toString
   */
  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + "{"
        + Arrays.stream(this.getClass().getFields())
            .map(
                x -> {
                  try {
                    return ""
                        + x.getName()
                        + " = '"
                        + (x.get(this) == null ? "null" : x.get(this).toString())
                        + "'";
                  } catch (IllegalAccessException e) {
                    e.printStackTrace();
                  }
                  return "";
                })
            .collect(Collectors.joining(" , "))
        + "}";
  }
}

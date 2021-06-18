package cloudstore.model.database.query;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class QueryObjectResult {

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + "{"
        + Arrays.stream(this.getClass().getFields())
            .filter(
                x -> {
                  try {
                    return x.get(this) != null;
                  } catch (IllegalAccessException e) {
                    e.printStackTrace();
                  }
                  return false;
                })
            .map(
                x -> {
                  try {
                    return "" + x.getName() + " = '" + x.get(this).toString() + "'";
                  } catch (IllegalAccessException e) {
                    e.printStackTrace();
                  }
                  return "";
                })
            .collect(Collectors.joining(" , "))
        + "}";
  }
}

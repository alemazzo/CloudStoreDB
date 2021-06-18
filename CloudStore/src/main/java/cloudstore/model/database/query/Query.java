package cloudstore.model.database.query;

public class Query {

  private final String select;
  private final String from;
  private final String where;
  private final String orderBy;
  private final String groupBy;
  private final String having;
  private final String limit;

  public Query(
      String select,
      String from,
      String where,
      String orderBy,
      String groupBy,
      String having,
      String limit) {
    this.select = select;
    this.from = from;
    this.where = where;
    this.orderBy = orderBy;
    this.groupBy = groupBy;
    this.having = having;
    this.limit = limit;
  }

  public static QueryBuilder builder() {
    return new QueryBuilder();
  }

  @Override
  public String toString() {
    return "SELECT "
        + select
        + " FROM "
        + from
        + ((where != "") ? " WHERE " + where : "")
        + ((groupBy != "") ? " GROUP BY " + groupBy : "")
        + ((having != "") ? " HAVING " + having : "")
        + ((orderBy != "") ? " ORDER BY " + orderBy : "")
        + ((limit != "") ? " LIMIT " + limit : "");
  }
}

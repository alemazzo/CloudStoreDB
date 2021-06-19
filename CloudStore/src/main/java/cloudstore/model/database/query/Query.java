package cloudstore.model.database.query;

/** A query object that translate to SqlQuery. */
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

  public String toSql() {
    return "SELECT "
        + select
        + " FROM "
        + from
        + ((!where.equals("")) ? " WHERE " + where : "")
        + ((!groupBy.equals("")) ? " GROUP BY " + groupBy : "")
        + ((!having.equals("")) ? " HAVING " + having : "")
        + ((!orderBy.equals("")) ? " ORDER BY " + orderBy : "")
        + ((!limit.equals("")) ? " LIMIT " + limit : "");
  }
}

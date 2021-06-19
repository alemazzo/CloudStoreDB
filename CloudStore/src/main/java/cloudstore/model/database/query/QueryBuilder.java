package cloudstore.model.database.query;

/** The builder for a Query object. */
public class QueryBuilder {

  private String selectString = "";
  private String fromString = "";
  private String whereString = "";
  private String orderByString = "";
  private String groupByString = "";
  private String havingString = "";
  private String limitString = "";

  public QueryBuilder select(final String selectString) {
    this.selectString = selectString;
    return this;
  }

  public QueryBuilder from(final String fromString) {
    this.fromString = fromString;
    return this;
  }

  public QueryBuilder where(final String whereString) {
    this.whereString = whereString;
    return this;
  }

  public QueryBuilder orderBy(final String orderByString) {
    this.orderByString = orderByString;
    return this;
  }

  public QueryBuilder groupBy(final String groupByString) {
    this.groupByString = groupByString;
    return this;
  }

  public QueryBuilder having(final String havingString) {
    this.havingString = havingString;
    return this;
  }

  public QueryBuilder limit(final String limitString) {
    this.limitString = limitString;
    return this;
  }

  /**
   * Retrieve the Query object.
   *
   * @return the Query object
   */
  public Query build() {
    return new Query(
        selectString,
        fromString,
        whereString,
        orderByString,
        groupByString,
        havingString,
        limitString);
  }
}

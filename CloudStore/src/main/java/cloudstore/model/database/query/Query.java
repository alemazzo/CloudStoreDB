package cloudstore.model.database.query;

public class Query {

    private String select;
    private String from;
    private String where;
    private String orderBy;
    private String groupBy;
    private String having;
    private String limit;

    public Query(String select, String from, String where, String orderBy, String groupBy, String having, String limit) {
        this.select = select;
        this.from = from;
        this.where = where;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.having = having;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "SELECT " + select +
                " FROM " + from +
                ((where != "") ? " WHERE " + where : "") +
                ((orderBy != "") ? " ORDER BY " + orderBy : "") +
                ((groupBy != "") ? " GROUP BY " + groupBy : "") +
                ((having != "") ? " HAVING " + having : "") +
                ((limit != "") ? " LIMIT " + limit : "");

    }

    public static QueryBuilder builder() {
        return new QueryBuilder();
    }
}

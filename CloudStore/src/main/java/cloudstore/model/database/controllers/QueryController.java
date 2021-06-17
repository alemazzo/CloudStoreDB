package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.mapper.EntityMapper;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class QueryController<T extends QueryObjectResult> {

    private final EntityMapper<T> mapper;
    private final PreparedStatement query;

    public QueryController(final Class<T> type, final PreparedStatement query) {
        this.mapper = new EntityMapper<>(type);
        this.query = query;
    }

    public Set<T> getResults() throws SQLException {
        final ResultSet results = this.query.executeQuery();
        final Set<T> list = this.mapper.fromResultSet(results);
        query.close();
        query.getConnection().close();
        return list;
    }

    public T getSingleValue() throws SQLException {
        return this.getResults().stream().findFirst().get();
    }
}

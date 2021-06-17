package cloudstore.model.database.controllers;

import cloudstore.model.database.Connector;
import cloudstore.model.database.mapper.EntityMapper;
import cloudstore.model.database.query.QueryObjectResult;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class EntityController<T extends QueryObjectResult> {

    private final Connector connector = new Connector();
    private final EntityMapper<T> mapper;

    public EntityController(final Class<T> type){
        this.mapper = new EntityMapper<T>(type);
    }

    public abstract String getTableName();

    public Connector getConnector(){
        return this.connector;
    }
    public EntityMapper<T> getEntityMapper(){ return this.mapper; }

    public Set<T> getAll() throws SQLException {
        final Connection connection = this.getConnector().connect();
        final String query = "select * from " + this.getTableName();
        final PreparedStatement statement = connection.prepareStatement(query);
        final ResultSet results = statement.executeQuery();
        final Set<T> list = this.mapper.fromResultSet(results);
        statement.close();
        connection.close();
        return list;
    }
}

package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

public abstract class Entity extends QueryObjectResult {
    public abstract String getInsertQuery();
}

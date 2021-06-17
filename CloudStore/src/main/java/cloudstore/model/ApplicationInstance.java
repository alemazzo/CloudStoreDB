package cloudstore.model;

import cloudstore.model.database.Connector;

import java.sql.Connection;
import java.util.Optional;

/**
 * Implementation of the Model interface.
 */
public final class ApplicationInstance implements Model {

    private Connection sqlConnection;

    @Override
    public Connection getConnection() {
        if (this.sqlConnection != null) {
            return this.sqlConnection;
        }
        final Connector connector = new Connector();
        this.sqlConnection = connector.connect();
        return this.sqlConnection;
    }
}

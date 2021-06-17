package cloudstore.model.database;

import java.sql.*;

public final class Connector {

    private final String dbName = "CloudStore";

    public final Connection connect(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUri = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "";

        Connection connection = null;
        try {
            System.out.println("DataSource.getConnection() driver = " + driver);
            Class.forName(driver);
            System.out.println("DataSource.getConnection() dbUri = " + dbUri);
            connection = DriverManager.getConnection(dbUri, userName, password);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Errore"+ e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Errore"+ e.getMessage());
        }
        return connection;
    }
}

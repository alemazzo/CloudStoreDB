package cloudstore.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** The DataBase Connector */
public final class Connector {

  private final String dbName = "CloudStore";

  public final Connection connect() {
    final String driver = "com.mysql.cj.jdbc.Driver";
    final String dbUri =
        "jdbc:mysql://localhost:3306/"
            + dbName
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String userName = "root";
    final String password = "";

    Connection connection = null;
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(dbUri, userName, password);
    } catch (ClassNotFoundException e) {
      System.out.println("Class Not Found Errore" + e.getMessage());
    } catch (SQLException e) {
      System.out.println("Errore" + e.getMessage());
    }
    return connection;
  }
}

package cloudstore.model.database.entities;

import java.util.Date;

/** The Condivisione Entity. */
public class Condivisione extends Entity {

  public int file;
  public String utente;
  public boolean lettura;
  public boolean scrittura;
  public Date dataCondivisione;

  @Override
  public String getTableName() {
    return "Condivisioni";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Condivisioni (File, Utente, Lettura, Scrittura) VALUES (?, ?, ?, ?)";
  }
}

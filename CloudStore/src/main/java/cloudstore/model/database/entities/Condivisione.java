package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Condivisione extends Entity {

  public int file;
  public String utente;
  public boolean lettura;
  public boolean scrittura;
  public Date dataCondivisione;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Condivisioni (File, Utente, Lettura, Scrittura) VALUES (?, ?, ?, ?)";
  }
}

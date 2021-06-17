package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Versione extends Entity{

  public Integer id;
  public Integer file;
  public Integer numero;
  public Date dataCreazione;
  public Integer dimensione;
  public String link;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Versioni (File, Numero, Dimensione, Link) VALUES (?, ?, ?, ?)";
  }
}

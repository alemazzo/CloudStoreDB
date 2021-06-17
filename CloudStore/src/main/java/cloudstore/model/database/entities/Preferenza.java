package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Preferenza extends Entity{

  public Integer file;
  public String utente;
  public Date dataPreferenza;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Preferenze (File, Utente) VALUES (?, ?)";
  }
}

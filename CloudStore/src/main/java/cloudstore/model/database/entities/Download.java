package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Download extends Entity {

  public Integer id;
  public Integer versione;
  public String utente;
  public Date dataDownload;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Downloads (Versione, Utente) VALUES (?, ?)";
  }
}

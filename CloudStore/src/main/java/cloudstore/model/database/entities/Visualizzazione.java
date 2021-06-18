package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Visualizzazione extends Entity{

  public Integer id;
  public Integer versione;
  public String utente;
  public Date dataVisualizzazione;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Visualizzazioni (Versione, Utente) VALUES (?, ?)";
  }
}

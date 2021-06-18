package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Segnalazione extends Entity{

  public Integer id;
  public String utente;
  public String descrizione;
  public Integer operatore;
  public Date dataAccettazione;
  public Date dataChiusura;

  @Override
  public String toString() {
    return "Segnalazione{"
        + "id='"
        + id
        + '\''
        + ", utente='"
        + utente
        + '\''
        + ", descrizione='"
        + descrizione
        + '\''
        + '}';
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Segnalazioni (Utente, Descrizione) VALUES (?, ?)";
  }
}

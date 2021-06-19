package cloudstore.model.database.entities;

import java.util.Date;

/** The Segnalazione Entity. */
public class Segnalazione extends Entity {

  public Integer id;
  public String utente;
  public String descrizione;
  public Integer operatore;
  public Date dataAccettazione;
  public Date dataChiusura;

  @Override
  public String getTableName() {
    return "Segnalazioni";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Segnalazioni (Utente, Descrizione) VALUES (?, ?)";
  }
}

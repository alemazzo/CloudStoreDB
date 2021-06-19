package cloudstore.model.database.entities;

import java.util.Date;

/** The Visualizzazione Entity. */
public class Visualizzazione extends Entity {

  public Integer id;
  public Integer versione;
  public String utente;
  public Date dataVisualizzazione;

  @Override
  public String getTableName() {
    return "Visualizzazioni";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Visualizzazioni (Versione, Utente) VALUES (?, ?)";
  }
}

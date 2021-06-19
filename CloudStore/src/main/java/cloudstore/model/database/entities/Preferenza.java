package cloudstore.model.database.entities;

import java.util.Date;

/** The Preferenza Entity. */
public class Preferenza extends Entity {

  public Integer file;
  public String utente;
  public Date dataPreferenza;

  @Override
  public String getTableName() {
    return "Preferenze";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Preferenze (File, Utente) VALUES (?, ?)";
  }
}

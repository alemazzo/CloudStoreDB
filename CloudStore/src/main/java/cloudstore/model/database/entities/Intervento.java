package cloudstore.model.database.entities;

import java.util.Date;

/** The Intervento Entity. */
public class Intervento extends Entity {

  public Integer segnalazione;
  public Integer numero;
  public String utente;
  public Integer operatore;
  public String messaggio;
  public Date dataIntervento;

  @Override
  public String getTableName() {
    return "Interventi";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Interventi (Segnalazione, Utente, Operatore, Messaggio) VALUES (?, ?, ?, ?)";
  }
}

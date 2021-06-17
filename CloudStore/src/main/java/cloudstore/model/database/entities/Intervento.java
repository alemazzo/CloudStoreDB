package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Intervento extends Entity {

  public Integer segnalazione;
  public Integer numero;
  public String utente;
  public Integer operatore;
  public String messaggio;
  public Date dataIntervento;

  @Override
  public String toString() {
    return "Intervento{" +
            "segnalazione='" + segnalazione + '\'' +
            ", numero=" + numero +
            ", messaggio='" + messaggio + '\'' +
            ", dataIntervento='" + dataIntervento + '\'' +
            '}';
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Interventi (Segnalazione, Numero, Utente, Operatore, Messaggio) VALUES (?, ?, ?, ?, ?)";
  }
}

package cloudstore.model.database.entities;

import java.util.Date;

/** The Operatore Entity. */
public class Operatore extends Entity {

  public Integer codice;
  public String nome;
  public String password;
  public Date dataNascita;

  @Override
  public String getTableName() {
    return "Operatori";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Operatori (Codice, Nome, Password, DataNascita) VALUES (?, ?, ?, ?)";
  }
}

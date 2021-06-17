package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Operatore extends Entity {

  public Integer codice;
  public String nome;
  public String password;
  public Date dataNascita;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Operatori (Codice, Nome, Password, DataNascita) VALUES (?, ?, ?, ?)";
  }
}

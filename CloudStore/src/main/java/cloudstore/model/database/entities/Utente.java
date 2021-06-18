package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Utente extends Entity {

  public String email;
  public String nome;
  public String cognome;
  public Date dataRegistrazione;
  public String password;
  public Date dataNascita;
  public int numeroDirectory;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Utenti (Email, Nome, Cognome, Password, DataNascita) VALUES (?, ?, ?, ?, ?)";
  }
}

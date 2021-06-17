package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Directory extends Entity {

  public int id;
  public String nome;
  public Date dataCreazione;
  public Integer padre;
  public String proprietario;

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Directories (Nome, Padre, Proprietario) VALUES (?, ?, ?)";
  }

  @Override
  public String toString() {
    return "Directory{"
        + "id="
        + id
        + ", nome='"
        + nome
        + '\''
        + ", dataCreazione="
        + dataCreazione
        + ", proprietario='"
        + proprietario
        + '\''
        + '}';
  }
}

package cloudstore.model.database.entities;

import java.util.Date;

/** The Directory Entity. */
public class Directory extends Entity {

  public int id;
  public String nome;
  public Date dataCreazione;
  public Integer padre;
  public String proprietario;

  @Override
  public String getTableName() {
    return "Directories";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Directories (Nome, Padre) VALUES (?, ?)";
  }
}

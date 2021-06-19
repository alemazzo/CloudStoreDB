package cloudstore.model.database.entities;

import java.util.Date;

/** The Versione Entity. */
public class Versione extends Entity {

  public Integer id;
  public Integer file;
  public Integer numero;
  public Date dataCreazione;
  public Integer dimensione;
  public String link;

  @Override
  public String getTableName() {
    return "Versioni";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Versioni (File, Dimensione, Link) VALUES (?, ?, ?)";
  }
}

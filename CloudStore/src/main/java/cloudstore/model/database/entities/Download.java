package cloudstore.model.database.entities;

import java.util.Date;

/** The Download Entity. */
public class Download extends Entity {

  public Integer id;
  public Integer versione;
  public String utente;
  public Date dataDownload;

  @Override
  public String getTableName() {
    return "Downloads";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Downloads (Versione, Utente) VALUES (?, ?)";
  }
}

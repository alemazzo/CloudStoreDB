package cloudstore.model.database.entities;

/** The File Entity. */
public class File extends Entity {

  public Integer id;
  public Integer directory;
  public String nome;
  public String estensione;
  public String proprietario;
  public Integer ultimaVersione;

  @Override
  public String getTableName() {
    return "Files";
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Files (Directory, Nome, Estensione, Proprietario, UltimaVersione) VALUES (?, ?, ?, ?, ?)";
  }
}

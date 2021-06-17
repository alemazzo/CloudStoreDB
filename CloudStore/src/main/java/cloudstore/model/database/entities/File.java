package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

public class File extends Entity{

  public Integer id;
  public Integer directory;
  public String nome;
  public String estensione;
  public String proprietario;
  public Integer ultimaVersione;

  @Override
  public String toString() {
    return "File{"
        + "id="
        + id
        + ", directory="
        + directory
        + ", nome='"
        + nome
        + '\''
        + ", estensione='"
        + estensione
        + '\''
        + ", proprietario='"
        + proprietario
        + '\''
        + '}';
  }

  @Override
  public String getInsertQuery() {
    return "INSERT INTO Files (Directory, Nome, Estensione, Proprietario, UltimaVersione) VALUES (?, ?, ?, ?, ?)";
  }
}

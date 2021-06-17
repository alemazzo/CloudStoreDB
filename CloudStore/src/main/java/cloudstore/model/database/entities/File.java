package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

public class File extends QueryObjectResult {

    public Integer id;
    public Integer directory;
    public String nome;
    public String estensione;
    public String proprietario;
    public Integer ultimaVersione;

}

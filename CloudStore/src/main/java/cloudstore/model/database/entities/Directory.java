package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Directory extends QueryObjectResult {

    public int id;
    public String nome;
    public Date dataCreazione;
    public Integer padre;
    public String proprietario;

}

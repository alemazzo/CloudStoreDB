package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Download extends QueryObjectResult {

    public int id;
    public int versione;
    public String utente;
    public Date dataDownload;

}

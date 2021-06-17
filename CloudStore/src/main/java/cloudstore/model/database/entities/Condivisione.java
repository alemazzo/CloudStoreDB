package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.util.Date;

public class Condivisione extends QueryObjectResult {

    public int file;
    public String utente;
    public boolean lettura;
    public boolean scrittura;
    public Date dataCondivisione;

}

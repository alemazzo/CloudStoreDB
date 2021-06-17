package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

public class Segnalazione extends QueryObjectResult {

    public String id;
    public String utente;
    public String descrizione;
    public String operatore;
    public String dataAccettazione;
    public String dataChiusura;

}

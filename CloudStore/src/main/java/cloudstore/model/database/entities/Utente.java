package cloudstore.model.database.entities;

import cloudstore.model.database.query.QueryObjectResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utente extends QueryObjectResult {

    public String email;
    public String nome;
    public String cognome;
    public Date dataRegistrazione;
    public String password;
    public Date dataNascita;
    public int numeroDirectory;

}

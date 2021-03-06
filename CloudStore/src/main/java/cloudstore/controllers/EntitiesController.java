package cloudstore.controllers;

import cloudstore.model.database.entities.*;

import java.sql.SQLException;
import java.util.Set;

/** The controller of the entities of the database. */
public class EntitiesController extends BasicController {

  public Set<Utente> getUtenti() throws SQLException {
    return this.getModel().getUtentiConnection().getAll();
  }

  public Set<Directory> getDirectories() throws SQLException {
    return this.getModel().getDirectoriesConnection().getAll();
  }

  public Set<File> getFiles() throws SQLException {
    return this.getModel().getFilesConnection().getAll();
  }

  public Set<Versione> getVersioni() throws SQLException {
    return this.getModel().getVersioniConnection().getAll();
  }

  public Set<Visualizzazione> getVisualizzazioni() throws SQLException {
    return this.getModel().getVisualizzazioniConnection().getAll();
  }

  public Set<Download> getDownloads() throws SQLException {
    return this.getModel().getDownloadsConnection().getAll();
  }

  public Set<Preferenza> getPreferenze() throws SQLException {
    return this.getModel().getPreferenzeConnection().getAll();
  }

  public Set<Condivisione> getCondivisioni() throws SQLException {
    return this.getModel().getCondivisioniConnection().getAll();
  }

  public Set<Segnalazione> getSegnalazioni() throws SQLException {
    return this.getModel().getSegnalazioniConnection().getAll();
  }

  public Set<Operatore> getOperatori() throws SQLException {
    return this.getModel().getOperatoriConnection().getAll();
  }

  public Set<Intervento> getInterventi() throws SQLException {
    return this.getModel().getInterventiConnection().getAll();
  }
}

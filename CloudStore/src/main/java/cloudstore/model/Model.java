package cloudstore.model;

import cloudstore.model.database.controllers.DatabaseEntityConnection;
import cloudstore.model.database.entities.*;

public interface Model {

  DatabaseEntityConnection<Utente> getUtentiConnection();

  DatabaseEntityConnection<Directory> getDirectoriesConnection();

  DatabaseEntityConnection<File> getFilesConnection();

  DatabaseEntityConnection<Versione> getVersioniConnection();

  DatabaseEntityConnection<Visualizzazione> getVisualizzazioniConnection();

  DatabaseEntityConnection<Download> getDownloadsConnection();

  DatabaseEntityConnection<Preferenza> getPreferenzeConnection();

  DatabaseEntityConnection<Condivisione> getCondivisioniConnection();

  DatabaseEntityConnection<Segnalazione> getSegnalazioniConnection();

  DatabaseEntityConnection<Operatore> getOperatoriConnection();

  DatabaseEntityConnection<Intervento> getInterventiConnection();
}

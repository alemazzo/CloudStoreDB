package cloudstore.model;

import cloudstore.model.database.controllers.DatabaseEntity;
import cloudstore.model.database.entities.*;

public interface Model {

  DatabaseEntity<Utente> getUtentiConnection();

  DatabaseEntity<Directory> getDirectoriesConnection();

  DatabaseEntity<File> getFilesConnection();

  DatabaseEntity<Versione> getVersioniConnection();

  DatabaseEntity<Visualizzazione> getVisualizzazioniConnection();

  DatabaseEntity<Download> getDownloadsConnection();

  DatabaseEntity<Preferenza> getPreferenzeConnection();

  DatabaseEntity<Condivisione> getCondivisioniConnection();

  DatabaseEntity<Segnalazione> getSegnalazioniConnection();

  DatabaseEntity<Operatore> getOperatoriConnection();

  DatabaseEntity<Intervento> getInterventiConnection();
}

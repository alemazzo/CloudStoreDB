package cloudstore.model;

import cloudstore.model.database.controllers.DatabaseEntity;
import cloudstore.model.database.entities.*;

/** Implementation of the Model interface. */
public final class ApplicationInstance implements Model {
  private final DatabaseEntity<Utente> utenti = new DatabaseEntity<>(Utente.class, "Utenti");
  private final DatabaseEntity<Directory> directories =
      new DatabaseEntity<>(Directory.class, "Directories");
  private final DatabaseEntity<File> files = new DatabaseEntity<>(File.class, "Files");
  private final DatabaseEntity<Versione> versioni =
      new DatabaseEntity<>(Versione.class, "Versioni");
  private final DatabaseEntity<Visualizzazione> visualizzazioni =
      new DatabaseEntity<>(Visualizzazione.class, "Visualizzazioni");
  private final DatabaseEntity<Download> downloads =
      new DatabaseEntity<>(Download.class, "Downloads");
  private final DatabaseEntity<Preferenza> preferenze =
      new DatabaseEntity<>(Preferenza.class, "Preferenze");
  private final DatabaseEntity<Condivisione> condivisioni =
      new DatabaseEntity<>(Condivisione.class, "Condivisioni");
  private final DatabaseEntity<Segnalazione> segnalazioni =
      new DatabaseEntity<>(Segnalazione.class, "Segnalazioni");

  @Override
  public DatabaseEntity<Utente> getUtentiConnection() {
    return this.utenti;
  }

  @Override
  public DatabaseEntity<Directory> getDirectoriesConnection() {
    return directories;
  }

  @Override
  public DatabaseEntity<File> getFilesConnection() {
    return files;
  }

  @Override
  public DatabaseEntity<Versione> getVersioniConnection() {
    return versioni;
  }

  @Override
  public DatabaseEntity<Visualizzazione> getVisualizzazioniConnection() {
    return visualizzazioni;
  }

  @Override
  public DatabaseEntity<Download> getDownloadsConnection() {
    return downloads;
  }

  @Override
  public DatabaseEntity<Preferenza> getPreferenzeConnection() {
    return preferenze;
  }

  @Override
  public DatabaseEntity<Condivisione> getCondivisioniConnection() {
    return condivisioni;
  }

  @Override
  public DatabaseEntity<Segnalazione> getSegnalazioniConnection() {
    return segnalazioni;
  }
}

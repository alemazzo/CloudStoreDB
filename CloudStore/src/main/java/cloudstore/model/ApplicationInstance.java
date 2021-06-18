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
  private final DatabaseEntity<Operatore> operatori =
      new DatabaseEntity<>(Operatore.class, "Operatori");

  @Override
  public DatabaseEntity<Utente> getUtentiConnection() {
    return this.utenti;
  }

  @Override
  public DatabaseEntity<Directory> getDirectoriesConnection() {
    return this.directories;
  }

  @Override
  public DatabaseEntity<File> getFilesConnection() {
    return this.files;
  }

  @Override
  public DatabaseEntity<Versione> getVersioniConnection() {
    return this.versioni;
  }

  @Override
  public DatabaseEntity<Visualizzazione> getVisualizzazioniConnection() {
    return this.visualizzazioni;
  }

  @Override
  public DatabaseEntity<Download> getDownloadsConnection() {
    return this.downloads;
  }

  @Override
  public DatabaseEntity<Preferenza> getPreferenzeConnection() {
    return this.preferenze;
  }

  @Override
  public DatabaseEntity<Condivisione> getCondivisioniConnection() {
    return this.condivisioni;
  }

  @Override
  public DatabaseEntity<Segnalazione> getSegnalazioniConnection() {
    return this.segnalazioni;
  }

  @Override
  public DatabaseEntity<Operatore> getOperatoriConnection() {
    return this.operatori;
  }
}

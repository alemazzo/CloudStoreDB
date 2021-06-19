package cloudstore.model;

import cloudstore.model.database.controllers.DatabaseEntityConnection;
import cloudstore.model.database.entities.*;

/** Implementation of the Model interface. */
public final class ApplicationInstance implements Model {

  private final DatabaseEntityConnection<Utente> utenti = new DatabaseEntityConnection<>(Utente.class);

  private final DatabaseEntityConnection<Directory> directories = new DatabaseEntityConnection<>(Directory.class);

  private final DatabaseEntityConnection<File> files = new DatabaseEntityConnection<>(File.class);

  private final DatabaseEntityConnection<Versione> versioni = new DatabaseEntityConnection<>(Versione.class);

  private final DatabaseEntityConnection<Visualizzazione> visualizzazioni =
      new DatabaseEntityConnection<>(Visualizzazione.class);

  private final DatabaseEntityConnection<Download> downloads = new DatabaseEntityConnection<>(Download.class);

  private final DatabaseEntityConnection<Preferenza> preferenze = new DatabaseEntityConnection<>(Preferenza.class);

  private final DatabaseEntityConnection<Condivisione> condivisioni =
      new DatabaseEntityConnection<>(Condivisione.class);

  private final DatabaseEntityConnection<Segnalazione> segnalazioni =
      new DatabaseEntityConnection<>(Segnalazione.class);

  private final DatabaseEntityConnection<Operatore> operatori = new DatabaseEntityConnection<>(Operatore.class);

  private final DatabaseEntityConnection<Intervento> interventi = new DatabaseEntityConnection<>(Intervento.class);

  @Override
  public DatabaseEntityConnection<Utente> getUtentiConnection() {
    return this.utenti;
  }

  @Override
  public DatabaseEntityConnection<Directory> getDirectoriesConnection() {
    return this.directories;
  }

  @Override
  public DatabaseEntityConnection<File> getFilesConnection() {
    return this.files;
  }

  @Override
  public DatabaseEntityConnection<Versione> getVersioniConnection() {
    return this.versioni;
  }

  @Override
  public DatabaseEntityConnection<Visualizzazione> getVisualizzazioniConnection() {
    return this.visualizzazioni;
  }

  @Override
  public DatabaseEntityConnection<Download> getDownloadsConnection() {
    return this.downloads;
  }

  @Override
  public DatabaseEntityConnection<Preferenza> getPreferenzeConnection() {
    return this.preferenze;
  }

  @Override
  public DatabaseEntityConnection<Condivisione> getCondivisioniConnection() {
    return this.condivisioni;
  }

  @Override
  public DatabaseEntityConnection<Segnalazione> getSegnalazioniConnection() {
    return this.segnalazioni;
  }

  @Override
  public DatabaseEntityConnection<Operatore> getOperatoriConnection() {
    return this.operatori;
  }

  @Override
  public DatabaseEntityConnection<Intervento> getInterventiConnection() {
    return this.interventi;
  }
}

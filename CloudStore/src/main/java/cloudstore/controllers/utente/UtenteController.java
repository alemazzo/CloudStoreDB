package cloudstore.controllers.utente;

import cloudstore.controllers.EntitiesController;
import cloudstore.model.database.controllers.DatabaseOperation;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;

public class UtenteController extends EntitiesController {

  public void createFile(
      final Integer directoryId,
      final String nome,
      final String estensione,
      final String proprietario,
      final String link,
      final Integer dimensione)
      throws SQLException {

    final BigInteger fileId =
        this.getModel()
            .getFilesConnection()
            .insert(directoryId, nome, estensione, proprietario, null)
            .executeAndGetGeneratedKey(BigInteger.class);

    this.getModel().getVersioniConnection().insert(fileId, dimensione, link).execute();
  }

  public void createVersione(final Integer fileId, String link, Integer dimensione)
      throws SQLException {
    this.getModel().getVersioniConnection().insert(fileId, dimensione, link).execute();
  }

  public void createVisualizzazione(final Integer versioneId, final String email)
      throws SQLException {
    this.getModel().getVisualizzazioniConnection().insert(versioneId, email).execute();
  }

  public void createDownload(final Integer versioneId, final String email) throws SQLException {
    this.getModel().getDownloadsConnection().insert(versioneId, email).execute();
  }

  public void createPreferenza(Integer fileId, String email) throws SQLException {
    this.getModel().getPreferenzeConnection().insert(fileId, email).execute();
  }

  public void createCondivisione(
      final Integer fileId, final String email, final boolean lettura, final boolean scrittura)
      throws SQLException {
    this.getModel().getCondivisioniConnection().insert(fileId, email, lettura, scrittura).execute();
  }

  public void createDirectory(final String nome, final int padreId, final String proprietario)
      throws SQLException {
    this.getModel().getDirectoriesConnection().insert(nome, padreId, proprietario).execute();
  }

  public void createSegnalazione(final String email, final String descrizione) throws SQLException {
    this.getModel().getSegnalazioniConnection().insert(email, descrizione).execute();
  }

  public void createUtente(
      final String email,
      final String nome,
      final String cognome,
      final String password,
      final Date dataDiNascita)
      throws SQLException {
    this.getModel()
        .getUtentiConnection()
        .insert(email, nome, cognome, password, dataDiNascita)
        .execute();
  }

  public void createOperatore(
      final Integer codice, final String nome, final String password, final Date date)
      throws SQLException {
    this.getModel().getOperatoriConnection().insert(codice, nome, password, date).execute();
  }

  public void accettaSegnalazione(final Integer segnalazioneId, final Integer codiceOperatore)
      throws SQLException {
    new DatabaseOperation(
            "UPDATE Segnalazioni SET Operatore = ?, DataAccettazione = current_timestamp() WHERE Id = ?",
            codiceOperatore,
            segnalazioneId)
        .executeUpdate();
  }

  public void chiudiSegnalazione(final Integer segnalazioneId) throws SQLException {
    new DatabaseOperation(
            "UPDATE Segnalazioni SET DataChiusura = NOW() WHERE Id = ?", segnalazioneId)
        .execute();
  }

  public void createInterventoUtente(
      final Integer segnalazioneId, final String email, final String messaggio) throws SQLException {
      new DatabaseOperation(
            "INSERT INTO Interventi (Segnalazione, Utente, Messaggio) VALUES (?, ?, ?)",
            segnalazioneId,
            email,
            messaggio)
        .execute();
  }
  public void createInterventoOperatore(
          final Integer segnalazioneId, final Integer operatoreId, final String messaggio) throws SQLException {
    new DatabaseOperation(
            "INSERT INTO Interventi (Segnalazione, Operatore, Messaggio) VALUES (?, ?, ?)",
            segnalazioneId,
            operatoreId,
            messaggio)
            .execute();
  }
}

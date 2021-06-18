package cloudstore.controllers.utente;

import cloudstore.controllers.EntitiesController;

import java.math.BigInteger;
import java.sql.SQLException;

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

  public void createCondivisione(final Integer fileId, final String email, final boolean lettura, final boolean scrittura) throws SQLException {
    this.getModel().getCondivisioniConnection().insert(fileId, email, lettura, scrittura).execute();
  }

  public void createDirectory(final String nome, final int padreId, final String proprietario) throws SQLException {
    this.getModel().getDirectoriesConnection().insert(nome, padreId, proprietario).execute();
  }

  public void createSegnalazione(final String email, final String descrizione) throws SQLException {
    this.getModel().getSegnalazioniConnection().insert(email, descrizione).execute();
  }
}

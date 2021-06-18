package cloudstore.views.utente;

import cloudstore.controllers.utente.UtenteController;
import cloudstore.model.database.entities.*;
import cloudstore.views.AbstractJavaFXView;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteView extends AbstractJavaFXView {

  // Directory
  @FXML private ListView<Directory> directoriesListView;
  @FXML private ChoiceBox<Directory> padreChoiceBox;
  @FXML private TextField directoryNomeTextField;

  // Files
  @FXML private ListView<File> filesListView;
  @FXML private ChoiceBox<Directory> padreFileChoiceBox;
  @FXML private TextField nomeFileTextField;
  @FXML private TextField estensioneFileTextField;
  @FXML private TextField linkFileTextField;
  @FXML private TextField dimensioneTextField;

  // Versioni
  @FXML private ListView<Versione> versioniListView;
  @FXML private ChoiceBox<File> fileVersioneChoiceBox;
  @FXML private TextField versioneLinkTextField;
  @FXML private TextField versioneDimensioneTextField;

  // Visualizzazioni
  @FXML private ListView<Visualizzazione> visualizzazioniListView;
  @FXML private ChoiceBox<Versione> visualizzazioniVersioneChoiceBox;
  @FXML private ChoiceBox<Utente> visualizzazioniUtentiChoiceBox;

  // Download
  @FXML private ListView<Download> downloadsListView;
  @FXML private ChoiceBox<Versione> downloadVersioneChoiceBox;
  @FXML private ChoiceBox<Utente> downloadUtenteChoiceBox;

  // Preferenze
  @FXML private ListView<Preferenza> preferenzeListView;
  @FXML private ChoiceBox<File> preferitiFileChoiceBox;
  @FXML private ChoiceBox<Utente> preferitiUtenteChoiceBox;

  // Condivisi
  @FXML private ListView<Condivisione> condivisiListView;
  @FXML private ChoiceBox<File> condivisioniFileChoiceBox;
  @FXML private ChoiceBox<Utente> condivisioniUtenteChoiceBox;
  @FXML private CheckBox condivisioniLetturaCheckBox;
  @FXML private CheckBox condivisioniScritturaCheckBox;

  // Segnalazioni
  @FXML private ListView<Segnalazione> segnalazioniListView;
  @FXML private ChoiceBox<Utente> segnalazioneUtenteChoiceBox;
  @FXML private TextArea segnalazioneDescrizioneTextArea;

  private UtenteController getUtenteController() {
    return (UtenteController) this.getController();
  }

  private void updateUtenti() throws SQLException {
    this.visualizzazioniUtentiChoiceBox.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getUtenti())));
    this.downloadUtenteChoiceBox.setItems(this.visualizzazioniUtentiChoiceBox.getItems());
    this.preferitiUtenteChoiceBox.setItems(this.visualizzazioniUtentiChoiceBox.getItems());
    this.condivisioniUtenteChoiceBox.setItems(this.visualizzazioniUtentiChoiceBox.getItems());
    this.segnalazioneUtenteChoiceBox.setItems(this.visualizzazioniUtentiChoiceBox.getItems());
  }

  private void updateDirectories() throws SQLException {
    this.directoriesListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getDirectories())));
    this.padreChoiceBox.setItems(this.directoriesListView.getItems());
    this.padreFileChoiceBox.setItems(this.directoriesListView.getItems());
  }

  private void updateFiles() throws SQLException {
    this.filesListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getFiles())));
    this.fileVersioneChoiceBox.setItems(this.filesListView.getItems());
    this.preferitiFileChoiceBox.setItems(this.filesListView.getItems());
    this.condivisioniFileChoiceBox.setItems(this.filesListView.getItems());
  }

  private void updateVersioni() throws SQLException {
    this.versioniListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getVersioni())));
    this.visualizzazioniVersioneChoiceBox.setItems(this.versioniListView.getItems());
    this.downloadVersioneChoiceBox.setItems(this.versioniListView.getItems());
  }

  private void updateVisualizzazioni() throws SQLException {
    this.visualizzazioniListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(this.getUtenteController().getVisualizzazioni())));
  }

  private void updateDownloads() throws SQLException {
    this.downloadsListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getDownloads())));
  }

  private void updatePreferenze() throws SQLException {
    this.preferenzeListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getPreferenze())));
  }

  private void updateCondivisi() throws SQLException {
    this.condivisiListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getCondivisioni())));
  }

  private void updateSegnalazioni() throws SQLException {
    this.segnalazioniListView.setItems(
        new ObservableListWrapper<>(new ArrayList<>(this.getUtenteController().getSegnalazioni())));
  }

  public void setup() throws SQLException {
    this.updateUtenti();
    this.updateDirectories();
    this.updateFiles();
    this.updateVersioni();
    this.updateVisualizzazioni();
    this.updateDownloads();
    this.updatePreferenze();
    this.updateCondivisi();
    this.updateSegnalazioni();
  }

  @Override
  public void init() {

    new Thread(
            () -> {
              try {
                this.setup();
              } catch (SQLException throwables) {
                throwables.printStackTrace();
              }
            })
        .start();
  }

  @FXML
  public void aggiungiDirectory(final ActionEvent event) throws SQLException {
    final Directory directory = this.padreChoiceBox.getValue();
    final String nome = this.directoryNomeTextField.getText();
    this.getUtenteController().createDirectory(nome, directory.id, directory.proprietario);
    this.updateDirectories();
  }

  @FXML
  public void aggiungiFile(final ActionEvent event) throws SQLException {
    final Directory directory = this.padreFileChoiceBox.getValue();
    final String nome = this.nomeFileTextField.getText();
    final String estensione = this.estensioneFileTextField.getText();
    final String link = this.linkFileTextField.getText();
    final Integer dimensione = Integer.valueOf(this.dimensioneTextField.getText());
    this.getUtenteController()
        .createFile(directory.id, nome, estensione, directory.proprietario, link, dimensione);
    this.updateFiles();
    this.updateVersioni();
  }

  @FXML
  public void aggiungiVersione(final ActionEvent event) throws SQLException {
    final File file = this.fileVersioneChoiceBox.getValue();
    final String link = this.versioneLinkTextField.getText();
    final Integer dimensione = Integer.valueOf(this.versioneDimensioneTextField.getText());
    this.getUtenteController().createVersione(file.id, link, dimensione);
    this.updateFiles();
    this.updateVersioni();
  }

  @FXML
  public void aggiungiVisualizzazione(final ActionEvent event) throws SQLException {
    final Versione versione = this.visualizzazioniVersioneChoiceBox.getValue();
    final Utente utente = this.visualizzazioniUtentiChoiceBox.getValue();
    this.getUtenteController().createVisualizzazione(versione.id, utente.email);
    this.updateVisualizzazioni();
  }

  @FXML
  public void aggiungiDownload(final ActionEvent event) throws SQLException {
    final Versione versione = this.downloadVersioneChoiceBox.getValue();
    final Utente utente = this.downloadUtenteChoiceBox.getValue();
    this.getUtenteController().createDownload(versione.id, utente.email);
    this.updateDownloads();
  }

  @FXML
  public void aggiungiPreferenza(final ActionEvent event) throws SQLException {
    final Utente utente = this.preferitiUtenteChoiceBox.getValue();
    final File file = this.preferitiFileChoiceBox.getValue();
    this.getUtenteController().createPreferenza(file.id, utente.email);
    this.updatePreferenze();
  }

  @FXML
  public void aggiungiCondivisione(final ActionEvent event) throws SQLException {
    final Utente utente = this.condivisioniUtenteChoiceBox.getValue();
    final File file = this.condivisioniFileChoiceBox.getValue();
    final boolean lettura = this.condivisioniLetturaCheckBox.isSelected();
    final boolean scrittura = this.condivisioniScritturaCheckBox.isSelected();
    this.getUtenteController().createCondivisione(file.id, utente.email, lettura, scrittura);
    this.updateCondivisi();
  }

  @FXML
  public void aggiungiSegnalazione(final ActionEvent event) throws SQLException {
    final Utente utente = this.segnalazioneUtenteChoiceBox.getValue();
    final String descrizione = this.segnalazioneDescrizioneTextArea.getText();
    this.getUtenteController().createSegnalazione(utente.email, descrizione);
    this.updateSegnalazioni();
  }
}

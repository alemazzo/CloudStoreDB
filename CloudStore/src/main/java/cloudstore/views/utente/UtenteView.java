package cloudstore.views.utente;

import cloudstore.model.database.controllers.EntityController;
import cloudstore.model.database.controllers.OperationController;
import cloudstore.model.database.controllers.QueryController;
import cloudstore.model.database.entities.*;
import cloudstore.views.AbstractJavaFXView;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class UtenteView extends AbstractJavaFXView {

  @FXML private ListView<Versione> versioniListView;
  @FXML private ListView<Visualizzazione> visualizzazioniListView;
  @FXML private ListView<Download> downloadsListView;
  @FXML private ListView<Preferenza> preferenzeListView;
  @FXML private ListView<Condivisione> condivisiListView;

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

  private final EntityController<Directory> directories =
      new EntityController<>(Directory.class, "Directories");
  private final EntityController<File> files = new EntityController<>(File.class, "Files");
  private final EntityController<Versione> versioni =
      new EntityController<>(Versione.class, "Versioni");
  private final EntityController<Visualizzazione> visualizzazioni =
      new EntityController<>(Visualizzazione.class, "Visualizzazioni");
  private final EntityController<Download> downloads =
      new EntityController<>(Download.class, "Downloads");
  private final EntityController<Preferenza> preferenze =
      new EntityController<>(Preferenza.class, "Preferenze");
  private final EntityController<Condivisione> condivisioni =
      new EntityController<>(Condivisione.class, "Condivisioni");

  private void updateDirectories() throws SQLException {
    directoriesListView.setItems(
        new ObservableListWrapper<>(directories.getAll().stream().collect(Collectors.toList())));
  }

  private void updateFiles() throws SQLException {
    filesListView.setItems(
        new ObservableListWrapper<>(files.getAll().stream().collect(Collectors.toList())));
  }

  private void updateVersioni() throws SQLException {
    versioniListView.setItems(
        new ObservableListWrapper<>(versioni.getAll().stream().collect(Collectors.toList())));
  }

  private void updateVisualizzazioni() throws SQLException {
    visualizzazioniListView.setItems(
        new ObservableListWrapper<>(
            visualizzazioni.getAll().stream().collect(Collectors.toList())));
  }

  private void updateDownloads() throws SQLException {
    downloadsListView.setItems(
        new ObservableListWrapper<>(downloads.getAll().stream().collect(Collectors.toList())));
  }

  private void updatePreferenze() throws SQLException {
    preferenzeListView.setItems(
        new ObservableListWrapper<>(preferenze.getAll().stream().collect(Collectors.toList())));
  }

  private void updateCondivisi() throws SQLException {
    condivisiListView.setItems(
        new ObservableListWrapper<>(condivisioni.getAll().stream().collect(Collectors.toList())));
  }

  @Override
  public void init() {
    try {
      updateDirectories();
      updateFiles();
      updateVersioni();
      updateVisualizzazioni();
      updateDownloads();
      updatePreferenze();
      updateCondivisi();
      //
      padreChoiceBox.setItems(directoriesListView.getItems());
      padreFileChoiceBox.setItems(directoriesListView.getItems());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @FXML
  public void aggiungiDirectory(final ActionEvent event) throws SQLException {
    final Directory directory = this.padreChoiceBox.getValue();
    final String nome = this.directoryNomeTextField.getText();
    this.directories.insert(nome, directory.id, directory.proprietario);
    this.updateDirectories();
  }

  @FXML
  public void aggiungiFile(final ActionEvent event) throws SQLException {
    final Directory directory = this.padreFileChoiceBox.getValue();
    final String nome = this.nomeFileTextField.getText();
    final String estensione = this.estensioneFileTextField.getText();
    final String link = this.linkFileTextField.getText();
    final int dimensione = Integer.valueOf(this.dimensioneTextField.getText());

    final BigInteger fileId = this.files.insert(directory.id, nome, estensione, directory.proprietario, null).executeAndGetGeneratedKey(BigInteger.class);
    final BigInteger versioneId = this.versioni.insert(fileId, 1, dimensione, link).executeAndGetGeneratedKey(BigInteger.class);

    OperationController controller =
        new OperationController("UPDATE Files SET UltimaVersione = ? WHERE id = ?", versioneId, fileId);
    controller.execute();
  }
}

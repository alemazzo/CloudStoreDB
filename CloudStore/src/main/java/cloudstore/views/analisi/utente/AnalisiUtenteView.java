package cloudstore.views.analisi.utente;

import cloudstore.controllers.analisi.utente.AnalisiUtenteController;
import cloudstore.model.database.entities.Directory;
import cloudstore.model.database.entities.File;
import cloudstore.model.database.entities.Utente;
import cloudstore.model.database.operations.UtentiAnalysisOperation;
import cloudstore.model.database.query.QueryObjectResult;
import cloudstore.views.AbstractJavaFXView;
import cloudstore.views.PageLoader;
import cloudstore.views.Pages;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnalisiUtenteView extends AbstractJavaFXView {

  @FXML private ListView<QueryObjectResult> resultsListView;
  @FXML private ChoiceBox<Utente> utentiChoiceBox;
  @FXML private ChoiceBox<File> filesChoiceBox;
  @FXML private ChoiceBox<Directory> directoriesChoiceBox;

  @Override
  public void init() {
    try {
      this.utentiChoiceBox.setItems(
          new ObservableListWrapper<>(
              new ArrayList<>(this.getAnalisiUtenteController().getUtenti())));
      this.filesChoiceBox.setItems(
          new ObservableListWrapper<>(
              new ArrayList<>(this.getAnalisiUtenteController().getFiles())));
      this.directoriesChoiceBox.setItems(
          new ObservableListWrapper<>(
              new ArrayList<>(this.getAnalisiUtenteController().getDirectories())));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  private AnalisiUtenteController getAnalisiUtenteController() {
    return (AnalisiUtenteController) this.getController();
  }

  @FXML
  public void operazione24(final ActionEvent event) throws SQLException {
    final Utente utente = this.utentiChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_24, utente.email))));
  }

  @FXML
  public void operazione25(final ActionEvent event) throws SQLException {
    System.out.println("AAAAAAAAAAAAA");
    final File file = this.filesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_25, file.id))));
  }

  @FXML
  public void operazione26(final ActionEvent event) throws SQLException {
    final Utente utente = this.utentiChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_26, utente.email))));
  }

  @FXML
  public void operazione27(final ActionEvent event) throws SQLException {
    final Utente utente = this.utentiChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_27, utente.email))));
  }

  @FXML
  public void operazione28(final ActionEvent event) throws SQLException {
    final Utente utente = this.utentiChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_28, utente.email))));
  }

  @FXML
  public void operazione29(final ActionEvent event) throws SQLException {
    final Directory directory = this.directoriesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_29, directory.id))));
  }

  @FXML
  public void operazione30(final ActionEvent event) throws SQLException {
    final Directory directory = this.directoriesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_30, directory.id))));
  }

  @FXML
  public void operazione31(final ActionEvent event) throws SQLException {
    final Directory directory = this.directoriesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_31, directory.id))));
  }

  @FXML
  public void operazione32(final ActionEvent event) throws SQLException {
    final Directory directory = this.directoriesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_32, directory.id))));
  }

  @FXML
  public void operazione33(final ActionEvent event) throws SQLException {
    final File file = this.filesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_33, file.id))));
  }

  @FXML
  public void operazione34(final ActionEvent event) throws SQLException {
    final Directory directory = this.directoriesChoiceBox.getValue();
    this.resultsListView.setItems(
        new ObservableListWrapper<>(
            new ArrayList<>(
                this.getAnalisiUtenteController()
                    .getOperationResult(UtentiAnalysisOperation.OPERATION_34, directory.id))));
  }
  @FXML
  public void goToAnalisiCloudStore(final ActionEvent event) {
    PageLoader.getInstance()
            .switchPage(this.getStage(), Pages.ANALISI_CLOUDSTORE, this.getController().getModel());
  }

  @FXML
  public void goToUtenti(final ActionEvent event) {
    PageLoader.getInstance()
            .switchPage(this.getStage(), Pages.UTENTI, this.getController().getModel());
  }
}

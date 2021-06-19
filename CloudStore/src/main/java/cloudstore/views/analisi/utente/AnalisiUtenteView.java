package cloudstore.views.analisi.utente;

import cloudstore.controllers.analisi.utente.AnalisiUtentePageController;
import cloudstore.model.database.entities.Directory;
import cloudstore.model.database.entities.File;
import cloudstore.model.database.entities.Utente;
import cloudstore.model.database.operations.UtentiAnalysisOperation;
import cloudstore.model.database.query.QueryResultObject;
import cloudstore.views.AbstractJavaFXView;
import cloudstore.views.PageLoader;
import cloudstore.views.Pages;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalisiUtenteView extends AbstractJavaFXView {

  @FXML private ListView<QueryResultObject> resultsListView;
  @FXML private ChoiceBox<Utente> utentiChoiceBox;
  @FXML private ChoiceBox<File> filesChoiceBox;
  @FXML private ChoiceBox<Directory> directoriesChoiceBox;
  @FXML private TableView<UtentiAnalysisOperation> operationTable;

  @Override
  public void init() {

    final TableColumn<UtentiAnalysisOperation, Integer> codiceColumn = new TableColumn<>("Codice");
    codiceColumn.setCellValueFactory(
        u -> new SimpleIntegerProperty(u.getValue().codice).asObject());

    final TableColumn<UtentiAnalysisOperation, String> operationColumn =
        new TableColumn<>("Operazione");
    operationColumn.setCellValueFactory(u -> new SimpleStringProperty(u.getValue().operazione));

    this.operationTable.getColumns().add(codiceColumn);
    this.operationTable.getColumns().add(operationColumn);

    this.operationTable.setItems(
        new ObservableListWrapper<>(
            Arrays.stream(UtentiAnalysisOperation.values()).collect(Collectors.toList())));

    this.operationTable
        .getSelectionModel()
        .getSelectedItems()
        .addListener(
            (ListChangeListener<? super UtentiAnalysisOperation>)
                (selected) -> {
                  final UtentiAnalysisOperation operation =
                      selected.getList().stream().findFirst().get();
                  this.executeOperation(operation);
                });
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

  private void executeOperation(final UtentiAnalysisOperation operation) {
    try {
      final Utente utente = this.utentiChoiceBox.getValue();
      final Directory directory = this.directoriesChoiceBox.getValue();
      final File file = this.filesChoiceBox.getValue();

      Set<QueryResultObject> results = Set.of();

      switch (operation) {
        case OPERATION_24:
        case OPERATION_27:
        case OPERATION_28:
        case OPERATION_26:
          results = this.getAnalisiUtenteController().getOperationResult(operation, utente.email);
          break;
        case OPERATION_25:
        case OPERATION_33:
          results = this.getAnalisiUtenteController().getOperationResult(operation, file.id);
          break;
        case OPERATION_29:
        case OPERATION_34:
        case OPERATION_32:
        case OPERATION_31:
        case OPERATION_30:
          results = this.getAnalisiUtenteController().getOperationResult(operation, directory.id);
          break;
      }

      this.resultsListView.setItems(new ObservableListWrapper<>(new ArrayList<>(results)));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  private AnalisiUtentePageController getAnalisiUtenteController() {
    return (AnalisiUtentePageController) this.getController();
  }

  @FXML
  public void goToAnalisiCloudStore(final ActionEvent event) {
    PageLoader.getInstance()
        .switchPage(this.getStage(), Pages.ANALISI_CLOUDSTORE, this.getController().getModel());
  }

  @FXML
  public void goToDatabase(final ActionEvent event) {
    PageLoader.getInstance()
        .switchPage(this.getStage(), Pages.DATABASE, this.getController().getModel());
  }
}

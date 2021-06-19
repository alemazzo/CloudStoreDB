package cloudstore.views.analisi.cloudstore;

import cloudstore.controllers.analisi.cloudstore.AnalisiCloudStorePageController;
import cloudstore.model.database.operations.CloudStoreAnalysisOperation;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalisiCloudStoreView extends AbstractJavaFXView {

  @FXML private ListView<QueryResultObject> queryResultListView;
  @FXML private TableView<CloudStoreAnalysisOperation> operationTable;

  @Override
  public void init() {
    final TableColumn<CloudStoreAnalysisOperation, Integer> codiceColumn =
        new TableColumn<>("Codice");
    codiceColumn.setCellValueFactory(
        u -> new SimpleIntegerProperty(u.getValue().codice).asObject());

    final TableColumn<CloudStoreAnalysisOperation, String> operationColumn =
        new TableColumn<>("Operazione");
    operationColumn.setCellValueFactory(u -> new SimpleStringProperty(u.getValue().operazione));

    this.operationTable.getColumns().add(codiceColumn);
    this.operationTable.getColumns().add(operationColumn);

    this.operationTable.setItems(
        new ObservableListWrapper<>(
            Arrays.stream(CloudStoreAnalysisOperation.values()).collect(Collectors.toList())));

    this.operationTable
        .getSelectionModel()
        .getSelectedItems()
        .addListener(
            (ListChangeListener<? super CloudStoreAnalysisOperation>)
                (selected) -> {
                  final CloudStoreAnalysisOperation operation =
                      selected.getList().stream().findFirst().get();
                  try {
                    final Set<QueryResultObject> results =
                        this.getAnalisiController().getOperationResult(operation);
                    System.out.println(results);
                    this.queryResultListView.setItems(
                        new ObservableListWrapper<>(new ArrayList<>(results)));
                  } catch (SQLException throwables) {
                    throwables.printStackTrace();
                  }
                });
  }

  public AnalisiCloudStorePageController getAnalisiController() {
    return (AnalisiCloudStorePageController) this.getController();
  }

  @FXML
  public void goToAnalisiUtenti(final ActionEvent event) {
    PageLoader.getInstance()
        .switchPage(this.getStage(), Pages.ANALISI_UTENTE, this.getController().getModel());
  }

  @FXML
  public void goToDatabase(final ActionEvent event) {
    PageLoader.getInstance()
        .switchPage(this.getStage(), Pages.DATABASE, this.getController().getModel());
  }
}

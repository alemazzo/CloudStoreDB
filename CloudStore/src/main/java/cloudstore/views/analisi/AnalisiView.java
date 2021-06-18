package cloudstore.views.analisi;

import cloudstore.controllers.analisi.AnalisiController;
import cloudstore.controllers.analisi.Operation;
import cloudstore.model.database.controllers.DatabaseQuery;
import cloudstore.model.database.entities.Operatore;
import cloudstore.model.database.query.Query;
import cloudstore.model.database.query.QueryObjectResult;
import cloudstore.views.AbstractJavaFXView;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AnalisiView extends AbstractJavaFXView {

  @FXML private ListView<QueryObjectResult> queryResultListView;
  @FXML private TableView<Operation> operationTable;

  @Override
  public void init() {
    final TableColumn<Operation, Integer> codiceColumn = new TableColumn<>("Codice");
    codiceColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().codice).asObject());

    final TableColumn<Operation, String> operationColumn = new TableColumn<>("Operazione");
    operationColumn.setCellValueFactory(u -> new SimpleStringProperty(u.getValue().operazione));

    this.operationTable.getColumns().add(codiceColumn);
    this.operationTable.getColumns().add(operationColumn);

    this.operationTable.setItems(new ObservableListWrapper<>(Operation.operations()));
    this.operationTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Operation>) (selected) -> {
      System.out.println("SELECTEDDD");
      final int operation = selected.getList().stream().findFirst().get().codice;
      final Set<QueryObjectResult> results;
      try {
        results = this.getAnalisiController().getOperation(operation);
        System.out.println(results);
        this.queryResultListView.setItems(new ObservableListWrapper<>(new ArrayList<>(results)));
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    });
  }

  public AnalisiController getAnalisiController() {
    return (AnalisiController) this.getController();
  }

}

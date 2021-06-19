package cloudstore.controllers.analisi.cloudstore;

import cloudstore.controllers.BasicController;
import cloudstore.model.database.operations.CloudStoreAnalysisOperation;
import cloudstore.model.database.query.QueryResultObject;

import java.sql.SQLException;
import java.util.Set;

/** The controller for the Analisi CloudStore page. */
public class AnalisiCloudStorePageController extends BasicController {

  public Set<QueryResultObject> getOperationResult(final CloudStoreAnalysisOperation operation)
      throws SQLException {
    return operation.execute();
  }
}

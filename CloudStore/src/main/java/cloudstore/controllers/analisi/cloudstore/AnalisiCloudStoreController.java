package cloudstore.controllers.analisi.cloudstore;

import cloudstore.controllers.BasicController;
import cloudstore.model.database.operations.CloudStoreAnalysisOperation;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.SQLException;
import java.util.Set;

public class AnalisiCloudStoreController extends BasicController {

  public Set<QueryObjectResult> getOperationResult(final CloudStoreAnalysisOperation operation) throws SQLException {
    return operation.execute();
  }
}

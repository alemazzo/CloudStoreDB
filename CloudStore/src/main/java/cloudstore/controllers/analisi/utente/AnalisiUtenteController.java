package cloudstore.controllers.analisi.utente;

import cloudstore.controllers.EntitiesController;
import cloudstore.model.database.operations.UtentiAnalysisOperation;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.SQLException;
import java.util.Set;

public class AnalisiUtenteController extends EntitiesController {

  public Set<QueryObjectResult> getOperationResult(
      final UtentiAnalysisOperation operation, final Object... args) throws SQLException {
    return operation.execute(args);
  }
}

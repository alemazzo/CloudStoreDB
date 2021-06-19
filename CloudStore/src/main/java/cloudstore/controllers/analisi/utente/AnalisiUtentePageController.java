package cloudstore.controllers.analisi.utente;

import cloudstore.controllers.EntitiesController;
import cloudstore.model.database.operations.UtentiAnalysisOperation;
import cloudstore.model.database.query.QueryResultObject;

import java.sql.SQLException;
import java.util.Set;

/** The controller for the Analisi Utente page. */
public class AnalisiUtentePageController extends EntitiesController {

  public Set<QueryResultObject> getOperationResult(
      final UtentiAnalysisOperation operation, final Object... args) throws SQLException {
    return operation.execute(args);
  }
}

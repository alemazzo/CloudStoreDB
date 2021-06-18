package cloudstore.controllers.analisi;

import cloudstore.controllers.BasicController;
import cloudstore.model.database.controllers.DatabaseQuery;
import cloudstore.model.database.entities.Operatore;
import cloudstore.model.database.query.Query;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalisiController extends BasicController {

  public Set<QueryObjectResult> getOperation(final int operationIndex) throws SQLException {
    switch (operationIndex) {
      case 16:
        return query16();
      case 17:
        return query17();
      case 18:
        return query18();
      case 19:
        return query19();
      case 20:
        return query20();
      case 21:
        return query21();
      case 22:
        return query22();
      case 23:
        return query23();
      case 24:
        return query24();
      case 25:
        return query25();
      case 26:
        return query26();
      case 27:
        return query27();
      case 28:
        return query28();
      case 29:
        return query29();
      case 30:
        return query30();
      case 31:
        return query31();
      case 32:
        return query32();
      case 33:
        return query33();
      case 34:
        return query34();
      default:
        return Set.of();
    }
  }

  private <T extends QueryObjectResult> Set<QueryObjectResult> fromQuery(
      final String sql, final Class<T> type) throws SQLException {
    final DatabaseQuery<T> query = new DatabaseQuery<>(type, sql);
    return query.getResults().stream().map(x -> (QueryObjectResult) x).collect(Collectors.toSet());
  }

  public Set<QueryObjectResult> query16() throws SQLException {
    final String sql =
        Query.builder()
            .select("o.*, COUNT(*) as Numero")
            .from("Operatori o inner join Segnalazioni s on o.Codice = s.Operatore")
            .where("not s.DataChiusura is null")
            .groupBy("o.Codice")
            .orderBy("Numero DESC")
            .limit("1")
            .build()
            .toString();
    return this.fromQuery(sql, Operatore.class);
  }

  public static class Query17Result extends QueryObjectResult {
    public String estensione;
  }

  public Set<QueryObjectResult> query17() throws SQLException {
    final String sql =
        Query.builder()
            .select("Estensione")
            .from("Files")
            .groupBy("Estensione")
            .orderBy("COUNT(*) DESC")
            .limit("1")
            .build()
            .toString();
    return this.fromQuery(sql, Query17Result.class);
  }

  public static class Query18Result extends QueryObjectResult {
    public String email;
    public String nome;
    public String cognome;
    public Integer numeroPreferiti;
  }

  public Set<QueryObjectResult> query18() throws SQLException {
    final String sql =
        Query.builder()
            .select("u.Email, u.Nome, u.Cognome, COUNT(*) as NumeroPreferiti")
            .from("Utenti u inner join Preferenze p on u.Email = p.Utente")
            .groupBy("u.Email, u.Nome, u.Cognome")
            .build()
            .toString();
    return this.fromQuery(sql, Query18Result.class);
  }

  public Set<QueryObjectResult> query19() {
    return Set.of();
  }

  public Set<QueryObjectResult> query20() {
    return Set.of();
  }

  public Set<QueryObjectResult> query21() {
    return Set.of();
  }

  public Set<QueryObjectResult> query22() {
    return Set.of();
  }

  public Set<QueryObjectResult> query23() {
    return Set.of();
  }

  public Set<QueryObjectResult> query24() {
    return Set.of();
  }

  public Set<QueryObjectResult> query25() {
    return Set.of();
  }

  public Set<QueryObjectResult> query26() {
    return Set.of();
  }

  public Set<QueryObjectResult> query27() {
    return Set.of();
  }

  public Set<QueryObjectResult> query28() {
    return Set.of();
  }

  public Set<QueryObjectResult> query29() {
    return Set.of();
  }

  public Set<QueryObjectResult> query30() {
    return Set.of();
  }

  public Set<QueryObjectResult> query31() {
    return Set.of();
  }

  public Set<QueryObjectResult> query32() {
    return Set.of();
  }

  public Set<QueryObjectResult> query33() {
    return Set.of();
  }

  public Set<QueryObjectResult> query34() {
    return Set.of();
  }
}

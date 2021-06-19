package cloudstore.model.database.operations;

import cloudstore.model.database.controllers.DatabaseQuery;
import cloudstore.model.database.entities.Directory;
import cloudstore.model.database.entities.File;
import cloudstore.model.database.operations.results.*;
import cloudstore.model.database.query.Query;
import cloudstore.model.database.query.QueryResultObject;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

/** The operations for the Analysis of the Utenti. */
public enum UtentiAnalysisOperation {

  /*
  SELECT DISTINCT (f.*)
  FROM Utenti u, Downloads d, Versioni v, Files f
  WHERE u.Email = d.Utente and d.Versione = v.Id and v.File = f.Id
  and f.Proprietario = ?
   */
  OPERATION_24(
      Query.builder()
          .select("DISTINCT f.*")
          .from("Utenti u, Downloads d, Versioni v, Files f")
          .where(
              "u.Email = d.Utente and d.Versione = v.Id and v.File = f.Id and f.Proprietario = ?")
          .build()
          .toString(),
      File.class),

  /*
  SELECT f.*, COUNT(*) as NumeroVersioni
  FROM Files f inner join Versioni v on f.Id = v.File
  GROUP BY f.Id
  HAVING f.Id = ?
   */
  OPERATION_25(
      Query.builder()
          .select("COUNT(*) as NumeroVersioni")
          .from("Files f inner join Versioni v on f.Id = v.File")
          .where("f.Id = ?")
          .build()
          .toString(),
      Query25Result.class),

  /*
  SELECT COUNT(*)
  FROM Utenti u inner join Files f on u.Email = f.Proprietario
  WHERE u.Email = ?
   */
  OPERATION_26(
      Query.builder()
          .select("COUNT(*) as numeroFiles")
          .from("Utenti u inner join Files f on u.Email = f.Proprietario")
          .where("u.Email = ?")
          .build()
          .toString(),
      Query26Result.class),

  /*
  SELECT COUNT(*) as NumeroDirectories
  FROM Utenti u inner join Directories d on u.Email = d.Proprietario
  WHERE u.Email = ?
   */
  OPERATION_27(
      Query.builder()
          .select("COUNT(*) as NumeroDirectories")
          .from("Utenti u inner join Directories d on u.Email = d.Proprietario")
          .where("u.Email = ?")
          .build()
          .toString(),
      Query27Result.class),

  /*
  SELECT COUNT(*) as NumeroPreferenze
  FROM Utenti u inner join Preferenze p on u.Email = p.Utente
  WHERE u.Email = ?
   */
  OPERATION_28(
      Query.builder()
          .select("COUNT(*) as NumeroPreferenze")
          .from("Utenti u inner join Preferenze p on u.Email = p.Utente")
          .where("u.Email = ?")
          .build()
          .toString(),
      Query28Result.class),

  /*
  SELECT COUNT(*) as NumeroFile
  FROM Directories d inner join Files f on d.Id = f.Directory
  WHERE d.Id = ?
   */
  OPERATION_29(
      Query.builder()
          .select("COUNT(*) as NumeroFiles")
          .from("Directories d inner join Files f on d.Id = f.Directory")
          .where("d.Id = ?")
          .build()
          .toString(),
      Query26Result.class),

  /*
  SELECT f.*
  from Directories d inner join Files f on d.Id = f.Directory
  WHERE d.Id = ?
   */
  OPERATION_30(
      Query.builder()
          .select("f.*")
          .from("Directories d inner join Files f on d.Id = f.Directory")
          .where("d.Id = ?")
          .build()
          .toString(),
      File.class),

  /*
  SELECT COUNT(*) as NumeroSottoCartelle
  FROM Directories d inner join Directories d2 on d.Id = d2.Padre
  WHERE d.Id = ?
   */
  OPERATION_31(
      Query.builder()
          .select("COUNT(*) as NumeroDirectories")
          .from("Directories d inner join Directories d2 on d.Id = d2.Padre")
          .where("d.Id = ?")
          .build()
          .toString(),
      Query27Result.class),

  /*
  SELECT d.*
  FROM Directories d
  WHERE d.Padre = ?
   */
  OPERATION_32(
      Query.builder().select("*").from("Directories d").where("d.Padre = ?").build().toString(),
      Directory.class),

  /*
  SELECT f.*, v.Dimensione
  FROM Files f inner join Versioni v on v.Id = f.UltimaVersione
  WHERE f.Id = ?
   */
  OPERATION_33(
      Query.builder()
          .select("v.Dimensione")
          .from("Files f inner join Versioni v on v.Id = f.UltimaVersione")
          .where("f.Id = ?")
          .build()
          .toString(),
      Query33Result.class),

  /*
  SELECT sum(v.Dimensione) as Dimensione
  FROM Directories d, Files f, Versioni v
  WHERE d.Id = f.Directory and f.UltimaVersione = v.Id and d.Id = ?
   */
  OPERATION_34(
      Query.builder()
          .select("SUM(v.Dimensione) as Dimensione")
          .from("Directories d, Files f, Versioni v")
          .where("d.Id = f.Directory and f.UltimaVersione = v.Id and d.Id = ?")
          .build()
          .toString(),
      Query34Result.class);

  private final String sql;
  private final Class<? extends QueryResultObject> resultType;

  private UtentiAnalysisOperation(
      final String sql, final Class<? extends QueryResultObject> resultType) {
    this.sql = sql;
    this.resultType = resultType;
  }

  /**
   * Execute the operation.
   *
   * @param args the arguments of the query.
   * @return the result of the query
   * @throws SQLException exception
   */
  public Set<QueryResultObject> execute(final Object... args) throws SQLException {
    final var dbquery = new DatabaseQuery<>(this.resultType, this.sql, args);
    return dbquery.getResultsSet().stream()
        .map(x -> (QueryResultObject) x)
        .collect(Collectors.toSet());
  }
}

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
  SELECT DISTINCT f.*
  FROM Utenti u, Downloads d, Versioni v, Files f
  WHERE u.Email = d.Utente and d.Versione = v.Id and v.File = f.Id
  and u.Email = ?
   */
  OPERATION_24(
          24,
      "Visualizzare tutti i file scaricati di un utente",
      Query.builder()
          .select("DISTINCT f.*")
          .from("Utenti u, Downloads d, Versioni v, Files f")
          .where(
              "u.Email = d.Utente and d.Versione = v.Id and v.File = f.Id and u.Email = ?")
          .build()
          .toSql(),
      File.class),

  /*
  SELECT COUNT(*) as NumeroVersioni
  FROM Files f inner join Versioni v on f.Id = v.File
  WHERE f.Id = ?
   */
  OPERATION_25(25,
          "Visualizzare per un file il numero di versioni",
      Query.builder()
          .select("COUNT(*) as NumeroVersioni")
          .from("Files f inner join Versioni v on f.Id = v.File")
          .where("f.Id = ?")
          .build()
          .toSql(),
      Query25Result.class),

  /*
  SELECT COUNT(*) as NumeroFile
  FROM Utenti u inner join File f on u.Email = f.Proprietario
  WHERE u.Email = ?
   */
  OPERATION_26(26,
          "Visualizzare per un utente il numero di file",
      Query.builder()
          .select("COUNT(*) as numeroFiles")
          .from("Utenti u inner join Files f on u.Email = f.Proprietario")
          .where("u.Email = ?")
          .build()
          .toSql(),
      Query26Result.class),

  /*
  SELECT NumeroDirectory
  FROM Utenti
  WHERE u.Email = ?
   */
  OPERATION_27(27,
          "Visualizzare per un utente il numero di directory",
      Query.builder()
          .select("NumeroDirectory")
          .from("Utenti")
          .where("Email = ?")
          .build()
          .toSql(),
      Query27Result.class),

  /*
  SELECT COUNT(*) as NumeroPreferenze
  FROM Utenti u inner join Preferenze p on u.Email = p.Utente
  WHERE u.Email = ?
   */
  OPERATION_28(28,
          "Visualizzare per un utente il numero di file preferiti",
      Query.builder()
          .select("COUNT(*) as NumeroPreferenze")
          .from("Utenti u inner join Preferenze p on u.Email = p.Utente")
          .where("u.Email = ?")
          .build()
          .toSql(),
      Query28Result.class),

  /*
  SELECT COUNT(*) as NumeroFile
  FROM Directories d inner join Files f on d.Id = f.Directory
  WHERE d.Id = ?
   */
  OPERATION_29(29,
          "Visualizzare il numero di file in una cartella",
      Query.builder()
          .select("COUNT(*) as NumeroFiles")
          .from("Directories d inner join Files f on d.Id = f.Directory")
          .where("d.Id = ?")
          .build()
          .toSql(),
      Query26Result.class),

  /*
  SELECT f.*
  from Directories d inner join Files f on d.Id = f.Directory
  WHERE d.Id = ?
   */
  OPERATION_30(30,
          "Visualizzare i file in una cartella",
      Query.builder()
          .select("f.*")
          .from("Directories d inner join Files f on d.Id = f.Directory")
          .where("d.Id = ?")
          .build()
          .toSql(),
      File.class),

  /*
  SELECT COUNT(*) as NumeroDirectory
  FROM Directories d inner join Directories d2 on d.Id = d2.Padre
  WHERE d.Id = ?
   */
  OPERATION_31(31,
          "Visualizzare il numero di cartelle in una cartella",
      Query.builder()
          .select("COUNT(*) as NumeroDirectory")
          .from("Directories d inner join Directories d2 on d.Id = d2.Padre")
          .where("d.Id = ?")
          .build()
          .toSql(),
      Query31Result.class),

  /*
  SELECT *
  FROM Directories
  WHERE Padre = ?
   */
  OPERATION_32(32,
          "Visualizzare le cartelle in una directory",
      Query.builder().select("*").from("Directories").where("Padre = ?").build().toSql(),
      Directory.class),

  /*
  SELECT v.Dimensione
  FROM Files f inner join Versioni v on v.Id = f.UltimaVersione
  WHERE f.Id = ?
   */
  OPERATION_33(33,
          "Visualizzare la dimensione di un file",
      Query.builder()
          .select("v.Dimensione")
          .from("Files f inner join Versioni v on v.Id = f.UltimaVersione")
          .where("f.Id = ?")
          .build()
          .toSql(),
      Query33Result.class),

  /*
  SELECT sum(v.Dimensione) as Dimensione
  FROM Directories d, Files f, Versioni v
  WHERE d.Id = f.Directory and f.UltimaVersione = v.Id and d.Id = ?
   */
  OPERATION_34(34,
          "Visualizzare la dimensione di una cartella",
      Query.builder()
          .select("SUM(v.Dimensione) as Dimensione")
          .from("Directories d, Files f, Versioni v")
          .where("d.Id = f.Directory and f.UltimaVersione = v.Id and d.Id = ?")
          .build()
          .toSql(),
      Query34Result.class);

  public final Integer codice;
  public final String operazione;
  private final String sql;
  private final Class<? extends QueryResultObject> resultType;

  private UtentiAnalysisOperation(
      final Integer code,
      final String operation,
      final String sql,
      final Class<? extends QueryResultObject> resultType) {
    this.codice = code;
    this.operazione = operation;
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

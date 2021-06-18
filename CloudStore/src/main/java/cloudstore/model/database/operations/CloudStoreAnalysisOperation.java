package cloudstore.model.database.operations;

import cloudstore.model.database.controllers.DatabaseQuery;
import cloudstore.model.database.entities.File;
import cloudstore.model.database.entities.Operatore;
import cloudstore.model.database.entities.Utente;
import cloudstore.model.database.operations.results.Query17Result;
import cloudstore.model.database.operations.results.Query18Result;
import cloudstore.model.database.operations.results.Query19Result;
import cloudstore.model.database.query.Query;
import cloudstore.model.database.query.QueryObjectResult;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public enum CloudStoreAnalysisOperation {

  /*
  SELECT Operatore, COUNT(*) as NumeroChiusure
  FROM Segnalazioni
  WHERE not (DataChiusura is null)
  GROUP BY Operatore
  ORDER BY NumeroChiusure DESC
  LIMIT 1
   */
  OPERATION_16(
      16,
      "Visualizzare l’operatore che ha chiuso più interventi",
      Query.builder()
          .select("o.*, COUNT(*) as Numero")
          .from("Operatori o inner join Segnalazioni s on o.Codice = s.Operatore")
          .where("not s.DataChiusura is null")
          .groupBy("o.Codice")
          .orderBy("Numero DESC")
          .limit("1")
          .build()
          .toString(),
      Operatore.class),

  /*
  SELECT Estensione
  FROM Files
  GROUP BY Estensione
  ORDER BY COUNT(*) DESC
  LIMIT 1
   */
  OPERATION_17(
      17,
      "Visualizzare il tipo di file più presente",
      Query.builder()
          .select("Estensione")
          .from("Files")
          .groupBy("Estensione")
          .orderBy("COUNT(*) DESC")
          .limit("1")
          .build()
          .toString(),
          Query17Result.class),

  /*
  SELECT u.Email, u.Nome, u.Cognome, COUNT(*) as NumeroPreferiti
  FROM Utenti u inner join Preferenze p on u.Email = p.Utente
  GROUP BY u.Email, u.Nome, u.Cognome
   */
  OPERATION_18(
      18,
      "Visualizzare il numero di file preferiti per utente",
      Query.builder()
          .select("u.Email, u.Nome, u.Cognome, COUNT(*) as NumeroPreferiti")
          .from("Utenti u inner join Preferenze p on u.Email = p.Utente")
          .groupBy("u.Email, u.Nome, u.Cognome")
          .build()
          .toString(),
      Query18Result.class),

  /*
  SELECT u.Email, u.Nome, u.Cognome, COUNT(*) as NumeroCartelle
  FROM Utenti u inner join Directories d on u.Email  = d.Proprietario
  GROUP BY u.Email, u.Nome, u.Cognome
   */
  OPERATION_19(
      19,
      "Visualizzare il numero di cartelle per utente",
      Query.builder()
          .select("u.Email, u.Nome, u.Cognome, COUNT(*) as NumeroCartelle")
          .from("Utenti u inner join Directories d on u.Email = d.Proprietario")
          .groupBy("u.Email, u.Nome, u.Cognome")
          .build()
          .toString(),
      Query19Result.class),

  /*
  SELECT *
  FROM Files f inner join Condivisioni c on f.Id = c.File
  GROUP BY f.Id
  ORDER BY COUNT(*) DESC
  LIMIT 1
  */
  OPERATION_20(
      20,
      "Visualizzare il file più condiviso",
      Query.builder()
          .select("f.*")
          .from("Files f inner join Condivisioni c on f.Id = c.File")
          .groupBy("f.Id")
          .orderBy("COUNT(*) DESC")
          .limit("1")
          .build()
          .toString(),
      File.class),

  /*
  SELECT f.*
  FROM Files f, Versioni v, Visualizzazioni vi
  WHERE f.Id = v.File and v.Id = vi.Versione
  GROUP BY f.Id
  ORDER BY COUNT(*) DESC
  LIMIT 1
   */
  OPERATION_21(
      21,
      "Visualizzare il file più visualizzato",
      Query.builder()
          .select("f.*")
          .from("Files f, Versioni v, Visualizzazioni vi")
          .where("f.Id = v.File and v.Id = vi.Versione")
          .groupBy("f.Id")
          .orderBy("COUNT(*) DESC")
          .limit("1")
          .build()
          .toString(),
      File.class),

  /*
  SELECT *
  FROM Utenti u inner join Segnalazioni s on u.Email = s.Utente
  GROUP BY f.Id
  ORDER BY COUNT(*) DESC
  LIMIT 1
   */
  OPERATION_22(
      22,
      "Visualizzare l’utente che ha aperto più segnalazioni",
      Query.builder()
          .select("u.*")
          .from("Utenti u inner join Segnalazioni s on u.Email = s.Utente")
          .groupBy("u.Email")
          .orderBy("COUNT(*) DESC")
          .limit("1")
          .build()
          .toString(),
      Utente.class),

  /*
  SELECT f.Id, f.Nome, f.Estensione
  FROM Files f, Versioni v, Downloads d
  WHERE f.Id = v.File and v.Id = d.Versione
  GROUP BY f.Id, f.Nome, f.Estensione
  ORDER BY COUNT(*) DESC
  LIMIT 1
   */
  OPERATION_23(
      23,
      "Visualizzare il file più scaricato",
      Query.builder()
          .select("f.*")
          .from("Files f, Versioni v, Downloads d")
          .where("f.Id = v.File and v.Id = d.Versione")
          .groupBy("f.Id")
          .orderBy("COUNT(*) DESC")
          .limit("1")
          .build()
          .toString(),
      File.class);

  public Integer codice;
  public String operazione;
  private String query;
  private Class<? extends QueryObjectResult> resultType;

  private CloudStoreAnalysisOperation(
      final Integer codice,
      final String operazione,
      final String query,
      final Class<? extends QueryObjectResult> resultType) {
    this.codice = codice;
    this.operazione = operazione;
    this.query = query;
    this.resultType = resultType;
  }

  public Set<QueryObjectResult> execute() throws SQLException {
    final var dbquery = new DatabaseQuery<>(this.resultType, this.query);
    return dbquery.getResults().stream()
        .map(x -> (QueryObjectResult) x)
        .collect(Collectors.toSet());
  }
}
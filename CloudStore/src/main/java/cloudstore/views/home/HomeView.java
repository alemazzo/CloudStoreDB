package cloudstore.views.home;

import cloudstore.model.database.Connector;
import cloudstore.model.database.controllers.OperationController;
import cloudstore.model.database.mapper.EntityMapper;
import cloudstore.model.database.query.Query;
import cloudstore.model.database.query.QueryObjectResult;
import cloudstore.views.AbstractJavaFXView;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class HomeView extends AbstractJavaFXView {

    public static class Oggetto {
        public Integer file;
        public String utente;
        public Date dataPreferenza;

        @Override
        public String toString() {
            return "Oggetto{" +
                    "file=" + file +
                    ", utente='" + utente + '\'' +
                    ", dataPreferenza=" + dataPreferenza +
                    '}';
        }
    }


    public static class QueryXXResult extends QueryObjectResult {
        public String email;
        public String nome;
        public Long numeroCartelle;

    }

    @Override
    public void init() {

        try {

            final String sql = "INSERT INTO Operatori (Codice, Nome, Password, DataNascita) VALUES (?, ?, ?, ?)";
            OperationController controller = new OperationController(sql, "999999", "TizioCaio", "PASSWORDISSIMA", new Date());
            System.out.println(controller.execute());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        /*


        EntityMapper<QueryXXResult> mapper = new EntityMapper<>(QueryXXResult.class);
        String query = Query.builder()
                        .select("u.Email, u.Nome, COUNT(*) as NumeroCartelle")
                        .from("Utenti u inner join Directories d on u.Email = d.Proprietario")
                        .groupBy("u.Email, u.Nome")
                        .build()
                        .toString();

        try {
            PreparedStatement statement = new Connector().connect().prepareStatement(query);
            final Set<QueryXXResult> results = mapper.fromResultSet(statement.executeQuery());
            results.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        try {
            PreparedStatement statement = connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        UtentiController utenti = new UtentiController();
        DirectoriesController directories = new DirectoriesController();
        FilesController files = new FilesController();

        EntityController<Oggetto> controller = new EntityController<Oggetto>(Oggetto.class){
            @Override
            public String getTableName() {
                return "Preferenze";
            }
        };

        try {
            controller.getAll().forEach(System.out::println);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

}

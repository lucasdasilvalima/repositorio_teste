//paste this into a file called Postgres.java
import java.sql.*;

public class CreateDatabase {

    public static void createDB(Connection connection) throws SQLException {
        System.out.println("> conexão realizada");

        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Users(\n" +
                " id        SERIAL PRIMARY KEY NOT NULL, " +
                " name      VARCHAR(255), " +
                " email     VARCHAR(255) UNIQUE, " +
                " password  VARCHAR(255));";

        // criando uma tabela
        statement.executeUpdate(sql);
        System.out.println("> tabela criada");
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String url = "jdbc:postgresql://motty.db.elephantsql.com:5432/zjumrqah";
        String username = "zjumrqah";
        String password = "3_Svph3vx7kr4WpSnmyCdaatZtZXORmp";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            createDB(connection);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT *\n" +
                    "FROM information_schema.columns\n" +
                    "WHERE table_name = 'Users';");
            System.out.println("> Schemas");
            while(rs.next()) {
                System.out.println("======================================================");
                System.out.print(rs.getString(1));
                System.out.print(" ");
                System.out.print(rs.getString(2));
                System.out.print(" ");
                System.out.println(rs.getString(3));
                System.out.println("======================================================\n");
            }

            rs = st.executeQuery("SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tpg_catalog.pg_tables\n" +
                    "WHERE\n" +
                    "\tschemaname != 'pg_catalog'\n" +
                    "AND schemaname != 'information_schema';");
            System.out.println("> TABLES");
            while(rs.next()) {
                System.out.println("======================================================");
                System.out.print(rs.getString(1));
                System.out.print(" ");
                System.out.print(rs.getString(2));
                System.out.print(" ");
                System.out.print(rs.getString(3));
                System.out.print(" ");
                System.out.print(rs.getString(4));
                System.out.print(" ");
                System.out.println(rs.getString(5));
                System.out.println("======================================================\n");
            }

            System.out.println("> Users");
            rs = st.executeQuery("SELECT * FROM Users");
            while (rs.next() ) {
                System.out.println("======================================================");
                System.out.println(rs.getString(1));
                System.out.print(" ");
                System.out.println(rs.getString(2));
                System.out.print(" ");
                System.out.println(rs.getString(3));
                System.out.println("======================================================\n");
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
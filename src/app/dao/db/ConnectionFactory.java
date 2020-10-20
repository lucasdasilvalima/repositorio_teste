package app.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import app.dao.DAOProperties;

/**
 * A factory responsible to get connections with a <b>MySQL</b> database.
 *
 * @author lucasrafael
 */
public class ConnectionFactory {

    /**
     * Opens a connection with a <b>SQL</b> database. The connection will be
     * performed based on the properties information obtained in the
     * <code>DAOProperties</code> class.
     *
     * @return a jdbc <code>Connection</code>.
     * @throws SQLException if an exception occurs during the process.
     */
    public static Connection openConnection() throws SQLException {
//        String url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
//                        "useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
//                DAOProperties.getDBDAOServerName(),
//                DAOProperties.getDBDAOPort(), DAOProperties.getDBDAODBName()
//        );
//        return DriverManager.getConnection(url,
//                DAOProperties.getDBDAOUserName(),
//                DAOProperties.getDBDAOPassword());

        String url = "jdbc:postgresql://motty.db.elephantsql.com:5432/" + System.getenv("DB_USER");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWD");
        return DriverManager.getConnection(url, username, password);
    }

}

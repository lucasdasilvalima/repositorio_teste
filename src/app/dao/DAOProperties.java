package app.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents a DAO properties which define the initial configurations for other
 * classes. That is, the params necessary to make a DAO connection and to
 * set the class responsible to perform the implementations with user data.
 *
 * All the params should be loaded to a <code>Properties</code>. Usually the
 * params are stored in a properties file.
 *
 * @author lucasrafael
 */
public class DAOProperties {

    /**
     * The <code>Properties</code> of this class.
     */
    private static Properties props = new Properties();

    /*
     * Try to get the properties from a properties file.
     */
    static {
        try (InputStream in = DAOProperties.class.getResourceAsStream("/dao.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        } 
    }

    /**
     * Find the class that implement the <code>IUserDAO</code>.
     *
     * @see IUserDAO
     *
     * @return the name of the class.
     */
    public static String getUserDAOClassName() {
        return props.getProperty("usdao.class");
    }
    /**
     *
     * @return
     */
    public static String getDBDAOServerName() {
        return props.getProperty("dao.db.serverName");
    }

    public static int getDBDAOPort() {
        return Integer.parseInt(props.getProperty("dao.db.port"));
    }

    public static String getDBDAODBName() {
        return props.getProperty("dao.db.dbName");
    }

    public static String getDBDAOUserName() {
        return props.getProperty("dao.db.userName");
    }

    public static String getDBDAOPassword() {
        return props.getProperty("dao.db.password");
    }

    public static String getAddUserProcedure() {
        return props.getProperty("dao.proc.users.add");
    }

    public static String getUpdateUserProcedure() {
        return props.getProperty("dao.proc.users.update");
    }

    public static String getAddCourseProcedure() {
        return props.getProperty("dao.proc.courses.add");
    }

    public static String getRegisterCourseProcedure() {
        return props.getProperty("dao.proc.courses.register");
    }

    public static String getInsertUserImageProcedure() {
        return props.getProperty("dao.proc.user.image.add");
    }
}

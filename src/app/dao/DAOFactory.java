package app.dao;

/**
 * A factory to construct instances of <code>IUserDAO</code> types.
 *
 * @author lucasrafael
 */
public class DAOFactory {

    /**
     * Construct a new instance of a <code>IUserDAO</code> type. The
     * object type will be based on the specified class of the properties
     * information obtained in the <code>DAOProperties</code> class.
     *
     * @return a instance of a <code>IUserDAO</code> implementation.
     */
    public static IUserDAO getUserServiceDAO() {
        try {
            String daoClass = DAOProperties.getUserDAOClassName();
            return (IUserDAO) Class.forName(daoClass).newInstance();
        } catch (InstantiationException | IllegalAccessException |
                ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

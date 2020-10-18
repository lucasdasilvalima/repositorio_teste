package app.dao.exceptions;

import app.dao.IUserDAO;

import java.sql.SQLException;

/**
 * A type of <code>Exception</code> throwable by the <code>IUserDAO</code>
 * methods.
 *
 * @see IUserDAO
 *
 * @author lucasrafael
 */
public class DAOException extends Exception {

    /**
     * Stores the instance of a <code>SQLException</code> occurred in a DAO
     * transaction.
     */
    private SQLException sqlException;

    /**
     * Constructs a new <code>DAOException</code> based on a previously
     * <code>SQLException</code> occurred.
     * @param sqlException
     */
    public DAOException(SQLException sqlException) {
        this.sqlException = sqlException;
    }

    /**
     * Constructs a new <code>DAOException</code> with a specific exception
     * message.
     * @param msg   a exception message <code>String</code>.
     */
    public DAOException(String msg) {
        super(msg);
    }

    /**
     * Get the <code>SQLException</code> instance of this exception.
     *
     * @return a <code>SQLException</code> instance.
     */
    public SQLException getSqlException() {
        return sqlException;
    }

    /**
     * Set the <code>SQLException</code> instance of this exception.
     *
     * @param sqlException
     */
    public void setSqlException(SQLException sqlException) {
        this.sqlException = sqlException;
    }
}

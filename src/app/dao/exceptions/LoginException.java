package app.dao.exceptions;

/**
 * A type of <code>Exception</code> throwable by the <code>IUserDAO</code>
 * methods, related to the impossibility to login a user.
 *
 * This exception should not be related to a <code>SQLException</code>.
 *
 * @author lucasrafael
 */
public class LoginException extends DAOException {

    /**
     * Construct a new <code>LoginException</code> with a specific message.
     *
     * @param msg a exception message <code>String</code>.
     */
    public LoginException(String msg) {
        super(msg);
    }

}

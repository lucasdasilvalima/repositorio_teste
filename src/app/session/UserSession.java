package app.session;

import app.model.User;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a user session. This is useful to keep only one instance of a user
 * at time, and get information about the user logged in.
 *
 * This class extends <code>ObjectBinding</code> for boolean binding purposes.
 *
 * This class is based on a <b>singleton</b> implementation, that is, only one
 * instance is granted.
 *
 * @author lucasrafael
 */
public class UserSession extends ObjectBinding {

    /* TODO: time counter of the session, toString() method to return user info */

    /**
     * The singleton instance.
     */
    private static UserSession instance;

    /*
     * Construct the singleton instance.
     */
    static {
        instance = new UserSession();
    }

    private UserSession() {}

    /**
     * Gets the instance.
     *
     * @return <code>UserSession</code> instance.
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * The user of this session.
     */
    private User user;

    /**
     * The <code>BooleanProperty</code> that indicates if this session is open.
     */
    private BooleanProperty open = new SimpleBooleanProperty();

    /**
     * Set the current session.
     *
     * The session will be set as open automatically.
     *
     * @param   user    A <code>User</code>.
     */
    public void setSession(User user) {
        this.user = user;
        this.setOpen(true);
    }

    public User getUser() {
        return user;
    }

    public BooleanProperty openProperty() {
        return open;
    }

    public final boolean isOpen() {
        return open.get();
    }

    public final void setOpen(boolean open) {
        this.open.set(open);
    }

    @Override
    protected Object computeValue() {
        return open;
    }
}

package app.dao;

import java.io.File;
import java.util.List;
import app.dao.exceptions.DAOException;
import app.dao.exceptions.LoginException;
import app.model.User;
import app.properties.UserProperty;

/**
 * DAO interface for access and management of application data. The interface
 * establishes basic methods for <code>User</code>s data.
 *
 * This interface also provides some useful methods for encryption and
 * decryption of passwords.
 *
 * @author lucasrafael
 */
public interface IUserDAO {

    /**
     * Gets a list with all the users, from a search by a part of the name
     * provided.
     *
     * @param       nameLike            Part of the name to makes the search.
     * @return      <code>List</code>   A list with <code>User</code>s.
     * @throws      DAOException        If an exception occurs when getting the
     *                                  data.
     */
    List<User> getAll(String nameLike) throws DAOException;

    /**
     * Load a user from the data base. Used to perform a login action.
     *
     * @param       id                  The code of the user.
     * @param       password            A password to check if corresponds with
     *                                  the password stored.
     * @return      User                A <code>User</code>s.
     * @throws      DAOException        If an exception occurs when getting the
     *                                  user from the data base or an unknown
     *                                  problem.
     * @throws      LoginException      If a password is not correct or any
     *                                  other exception related to the login
     *                                  action.
     */
    User load(int id, String password)
            throws LoginException, DAOException;

    /**
     * Load a user from the data base. Used to perform a login action.
     *
     * @param       email               The user's email.
     * @param       password            A password to check if corresponds with
     *                                  the password stored.
     * @return      User                A <code>User</code>s.
     * @throws      DAOException        If an exception occurs when getting the
     *                                  user from the data base or an unknown
     *                                  problem.
     * @throws      LoginException      If a password is not correct or any
     *                                  other exception related to the login
     *                                  action.
     */
    User load(String email, String password)
            throws LoginException, DAOException;

    /**
     * Deletes a user if possible of the data base from the id code provided..
     *
     * @param       id                  Identity code
     * @throws      DAOException        If an exception occurs trying to delete
     *                                  the user of the specified type and id.
     */
    void delete(String id) throws DAOException;

    /**
     * Performs an update of the user's information. It is not necessary to
     * provide any additional param, but it is required that the identity code
     * be valid in the <code>User</code> object provided. The update
     * of the id it is not possible.
     *
     * NOTE: All the information contained in the <code>User</code> object
     * provided wil overwrite the information about the same user in the data
     * base.
     *
     * @param       user                A <code>User</code> containing
     *                                  the updated information.
     * @throws      DAOException        If an exception occurs trying to update
     *                                  the data.
     */
    void update(User user) throws DAOException;

    /**
     * Stores a user in the data base.
     *
     * @param       user                A <code>User</code> to store the user
     *                                  information in the data base.
     * @throws      DAOException        If an exception occurs trying to store
     *                                  the data.
     */
    void store(User user) throws DAOException;

    /**
     * Stores a user image in the data base.
     *
     * @param       id                  The code of the user.
     * @param       imagePath           The path to the image.
     *                                  TODO: Check path to the image argument
     * @throws      DAOException        If an exception occurs trying to store
     *                                  the data.
     */
    void insertUserImage(String id, String imagePath)
            throws DAOException;

    /**
     * Get the user image in the data base.
     *
     * @param       id                  The code of the user.
     * @throws      DAOException        If an exception occurs trying to store
     *                                  the data.
     * @return      <code>File</code>   The user image
     */
    File getUserImage(String id)
            throws DAOException;
}

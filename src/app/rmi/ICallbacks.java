package app.rmi;

import app.model.ChatGroup;
import app.model.Message;
import app.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallbacks extends Remote {

    /**
     * Notifies this user that another user is now online
     *
     * @param   user                The user who entered online
     */
    public void notifyNewUserAvailable(User user) throws RemoteException;

    /**
     * Notifies this user that another user wants to chat with
     *
     * @param   user                The user who want to chat with this user
     */
    public void notifyNewChatWith(User user) throws RemoteException;

    /**
     * Notifies this user that he is now on a group
     *
     * @param   chatGroup           The chatGroup that added this user
     */
    public void notifyNewChatWith(ChatGroup chatGroup) throws RemoteException;

    /**
     * Notifies this user that a file was send by a user on a private chat
     *
     * @param   user                The user who sent the file
     */
    public void notifyReceivingFileFrom(User user) throws RemoteException;

    /**
     * Notifies this user that a file was send by a user on a group
     *
     * @param   user                The user who sent the file
     * @param   chatGroup           The chatGroup that the file was sent
     */
    public void notifyReceivingFileFrom(User user, ChatGroup chatGroup)
            throws RemoteException;

    /**
     * Notifies this user a new message
     *
     * @param   message                The received message
     */
    public void notifyNewMessage(Message message) throws RemoteException;

}

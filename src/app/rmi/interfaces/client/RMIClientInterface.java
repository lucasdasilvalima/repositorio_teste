package app.rmi.interfaces.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;

public interface RMIClientInterface extends Remote {

    public void notifyNewUserIsOn(RMIClientInterface user) throws RemoteException;

    public void notifyUserIsOut(RMIClientInterface user) throws RemoteException;

    public void notifyNewGroup(ChatGroup group) throws RemoteException;

    public User getUser() throws RemoteException;

    public void receiveMessageFromUser(Message message) throws RemoteException;

    public void receiveFileAsMessageFromUser(FileAsMessage fileAsMessage) throws RemoteException;

    public void receiveMessageFromGroup(Message message, ChatGroup source) throws RemoteException;

    public void receiveFileAsMessageFromGroup(FileAsMessage fileAsMessage, ChatGroup user) throws RemoteException;
}

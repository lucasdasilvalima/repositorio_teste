package app.rmi;

import app.model.ChatGroup;
import app.model.Message;
import app.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISendMessage extends Remote {

    public void sendMessageToUser(User from, User to, Message message) throws RemoteException;
    public void sendMessageToGroup(User from, ChatGroup to, Message message) throws RemoteException;

}

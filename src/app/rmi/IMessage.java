package app.rmi;

import app.model.Message;
import app.model.MessageGroup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMessage extends Remote {

    public void sendMessage(Message message) throws RemoteException;
    public void sendGroupMessage(MessageGroup messageGroup) throws RemoteException;

}

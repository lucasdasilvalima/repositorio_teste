package app.rmi.interfaces.server;

import java.rmi.*;

import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.client.RMIClientInterface;

public interface IServices extends Remote {

	public boolean addNewUser(RMIClientInterface client) throws RemoteException;

	public void sendMessageToUser(User to, Message menssage) throws RemoteException;

	public void sendFileToUser(User to, FileAsMessage fileAsMessage) throws RemoteException;

	public void joinAGroup(User user, ChatGroup group) throws RemoteException;

	public void removeUserFromGroup(User user, ChatGroup group) throws RemoteException;

	public void sendMessageToGroup(ChatGroup to, Message menssage) throws RemoteException;

	public void sendFileToGroup(ChatGroup to, FileAsMessage fileAsMessage) throws RemoteException;

	public void uploadFileToServer(byte[] mybyte, String serverpath, int length) throws RemoteException;

	public byte[] downloadFileFromServer(String servername) throws RemoteException;

	public String[] listFiles(String serverpath) throws RemoteException;

	public boolean createDirectory(String serverpath) throws RemoteException;

	public boolean removeDirectoryOrFile(String serverpath) throws RemoteException;
}

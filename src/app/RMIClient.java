package app;

import app.model.ChatGroup;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.IUser;
import app.rmi.interfaces.client.RMIClientInterface;
import app.rmi.interfaces.server.IServices;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

public class RMIClient {
    private static RMIClient singleInstance = null;

    private String serverAdress = new String("rmi://");
    public IServices iServices;
    public RMIClientInterface clientInterface;

    private RMIClient(IUser user, ListView<ChatGroup> groupList,
                      javafx.scene.control.ListView<User> userList, TextFlow messagesArea) throws RemoteException, NotBoundException, MalformedURLException {
        this.clientInterface = new RMIClientImplementation(user, groupList, userList, messagesArea);
        this.serverAdress +=  System.getenv("SERVER_ADDRESS") + "/whatsut";
        System.out.println(serverAdress);
        this.iServices = (IServices) Naming.lookup(serverAdress);
//        this.iServices.addNewUser(clientInterface);
    }

    public void sendMessageToUser(Message message) throws RemoteException {
        this.iServices.sendMessageToUser(((RMIClientImplementation) clientInterface).getUser(), message);
        System.out.println("Send message complete");
    }

    public boolean setUserOn()throws RemoteException, NotBoundException, MalformedURLException {
        return this.iServices.addNewUser(clientInterface);
    }

    public static RMIClient getInstance() throws RemoteException, NotBoundException, MalformedURLException {
        if (singleInstance == null)
            return null;
//            singleInstance = new RMIClient(user);
        return singleInstance;
    }
    public static RMIClient getInstance(IUser user, ListView<ChatGroup> groupList,
                                        javafx.scene.control.ListView<User> userList,TextFlow messagesArea) throws RemoteException, NotBoundException, MalformedURLException {
        if (singleInstance == null)
            singleInstance = new RMIClient(user, groupList, userList, messagesArea);
        return singleInstance;
    }

    public Vector<User> getAllUserAvaliable(){
        return ((RMIClientImplementation) clientInterface).myContacts;
    }
}

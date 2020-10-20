package app;

import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.IUser;
import app.rmi.interfaces.client.RMIClientInterface;
import app.rmi.interfaces.server.IServices;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class RMIClient {
    private static RMIClient singleInstance = null;

    private String serverAdress = new String("rmi://");
    public IServices iServices;
    public RMIClientInterface clientInterface;

    private RMIClient(IUser user, ListView<ChatGroup> groupList,
                      ListView<User> userList,
                      TextFlow messagesArea,
                      Map<User, ArrayList<Message>> historyUser,
                      Map<Group, ArrayList<ChatGroup>> historyGroup)
            throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("[DEBUG] RMIClient");
        this.clientInterface = new RMIClientImplementation(user, groupList, userList, messagesArea,
                historyUser, historyGroup);
        this.serverAdress +=  System.getenv("SERVER_ADDRESS") + "/whatsut";
        System.out.println(serverAdress);
        this.iServices = (IServices) Naming.lookup(serverAdress);
        System.out.println("[DEBUG] END RMIClient");
//        this.iServices.addNewUser(clientInterface);
    }


    public void sendMessageAsFileToUser(FileAsMessage message) throws RemoteException {
        this.iServices.sendFileToUser(message.to, message);
    }

    public void sendMessageToUser(Message message) throws RemoteException {
        System.out.println("[DEBUG] sendMessageToUser");
        System.out.println("[DEBUG] " + message.getTo());
        System.out.println("[DEBUG] " + message);
        this.iServices.sendMessageToUser(message.getTo(), message);
        System.out.println("Send message complete");
    }

    public boolean logOutUser() {
        try {
            return this.iServices.removeUserFromApp(clientInterface);
        } catch (Exception e) {
            System.err.println("MSG "+e.getMessage());
        }
        finally{
            return true;
        }
    }

    public boolean setUserOn()throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("[DEBUG] setUserOn");
        return this.iServices.addNewUser(clientInterface);
    }

    public static RMIClient getInstance() throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("[DEBUG] getInstance");
        if (singleInstance == null)
            return null;
//            singleInstance = new RMIClient(user);
        return singleInstance;
    }
    public static RMIClient getInstance(IUser user,
                                        ListView<ChatGroup> groupList,
                                        ListView<User> userList,
                                        TextFlow messagesArea,
                                        Map<User, ArrayList<Message>> historyUser,
                                        Map<Group, ArrayList<ChatGroup>> historyGroup)
            throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("[DEBUG] getInstance");
        if (singleInstance == null)
            singleInstance = new RMIClient(user, groupList, userList,
                    messagesArea, historyUser, historyGroup);
        return singleInstance;
    }

    public Vector<User> getAllUserAvaliable(){
        return ((RMIClientImplementation) clientInterface).myContacts;
    }
}

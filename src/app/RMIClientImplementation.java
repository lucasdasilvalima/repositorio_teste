package app;

import app.controller.common.MessageFormatter;
import app.controller.common.TextFormatWrapper;
import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.IUser;
import app.rmi.interfaces.client.RMIClientInterface;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class RMIClientImplementation extends UnicastRemoteObject implements RMIClientInterface, Serializable {

    private static final long serialVersionUID = 1L;
    private User user;
    public Vector<User> myContacts;
    public ListView<ChatGroup> groupList;
    public ListView<User> userList;
    public TextFlow messagesArea;
    public Map<User, ArrayList<Message>> usersHistory;
    public Map<Group, ArrayList<ChatGroup>> groupsHistory;

    public RMIClientImplementation(IUser user, ListView<ChatGroup> groupList, ListView<User> userList,
            TextFlow messagesArea, Map<User, ArrayList<Message>> usersHistory,
            Map<Group, ArrayList<ChatGroup>> groupsHistory) throws RemoteException {
        super();
        System.out.println("[DEBUG] getInstance");
        this.groupList = groupList;
        this.userList = userList;
        this.user = (User) user;
        this.myContacts = new Vector<>();
        this.messagesArea = messagesArea;
        this.usersHistory = usersHistory;
        this.groupsHistory = groupsHistory;
    }

    @Override
    public User getUser() throws RemoteException {
        // System.out.println("[DEBUG] getUser");
        return this.user;
    }

    @Override
    public void receiveMessageFromUser(Message message) throws RemoteException {
        System.out.println("->" + message);

        if (this.usersHistory.containsKey(message.getFrom())) {
            System.out.println("[DEBUG] adicionando usuário no history: " + message.getFrom() + " -> " + message);
            this.usersHistory.get(message.getFrom()).add(message);
        } else {
            System.out.println("[DEBUG] adicionando mensagem no history -> " + message);
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(message);
            this.usersHistory.put(message.getFrom(), messages);
        }

        // User source = message.getUser();
        // TextFormatWrapper t1 = MessageFormatter.format(user.getName(), "28/28/28",
        // message.toString());
        // System.err.println(source.getEmail() + " - [Diz]: " + message.toString());
        // addMessageToFlow(t1);

    }

    public void addMessageToFlow(TextFormatWrapper text) {
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.date);
        messagesArea.getChildren().add(text.text);
        // scrollPane.setVvalue(1.0);
    }

    @Override
    public void receiveFileAsMessageFromUser(FileAsMessage fileAsMessage) throws RemoteException {
        System.out.println("[DEBUG] receiveFileAsMessageFromUser");
        String clientpath = "/tmp/client/";
        try {
            saveFileReceived(fileAsMessage, clientpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileReceived(FileAsMessage fileAsMessage, String path) throws IOException {

        File clientpathfile = fileAsMessage.content;

        FileOutputStream out;

        out = new FileOutputStream(clientpathfile);
        out.write(fileAsMessage.getByte());
        out.flush();
        out.close();
    }

    @Override
    public void receiveMessageFromGroup(Message message, ChatGroup source) throws RemoteException {
        System.out.println(source.getName() + " - [Diz]: " + message.toString());

    }

    @Override
    public void receiveFileAsMessageFromGroup(FileAsMessage fileAsMessage, ChatGroup user) throws RemoteException {
        String clientpath = "/tmp/client/";
        try {
            saveFileReceived(fileAsMessage, clientpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listAllClientes() {
        System.out.println("********************************************************");
        synchronized (myContacts) {
            for (User user : myContacts)
                System.out.println(user.getName());
        }
        System.out.println("********************************************************");
    }

    public User getRandomUser() {
        Random random = new Random();
        int index = random.nextInt(this.myContacts.size());
        return this.myContacts.get(index);
    }

    @Override
    public void notifyNewUserIsOn(RMIClientInterface user) throws RemoteException {
        if (!myContacts.contains(user)) {
            this.myContacts.add(user.getUser());
            userList.getItems().add(user.getUser());
        }
    }

    @Override
    public void notifyUserIsOut(RMIClientInterface user) throws RemoteException {
        if (myContacts.contains(user)) {
            this.myContacts.remove(user.getUser());
            userList.getItems().remove(user.getUser());
        }
    }

    @Override
    public boolean equals(Object obj) {
        RMIClientImplementation rmiClientImplementation = (RMIClientImplementation) obj;
        // COnfia no lucas rafael ;)
        try {
            return this.user.equals(rmiClientImplementation.getUser());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void notifyNewGroup(ChatGroup group) throws RemoteException {
        System.out.println(group.toString());
        // criar metodos de usuario
    }

}

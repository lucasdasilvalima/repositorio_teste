package app;

import app.controller.common.MessageFormatter;
import app.controller.common.TextFormatWrapper;
import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.IUser;
import app.rmi.interfaces.client.RMIClientInterface;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Vector;



public class RMIClientImplementation extends UnicastRemoteObject implements RMIClientInterface, Serializable {

    private static final long serialVersionUID = 1L;
    private User user;
    public Vector<User> myContacts;
    public ListView<ChatGroup> groupList;
    public javafx.scene.control.ListView<User> userList;
    public TextFlow messagesArea ;

    public RMIClientImplementation(IUser user, ListView<ChatGroup> groupList,
            javafx.scene.control.ListView<User> userList, TextFlow messagesArea) throws RemoteException {
        super();
        this.groupList = groupList;
        this.userList = userList;
        this.user = (User) user;
        this.myContacts = new Vector<>();
        this.messagesArea = messagesArea;
    }



    @Override
    public User getUser() throws RemoteException {
        return this.user;
    }

    @Override
    public void receiveMessageFromUser(Message message) throws RemoteException {
        User source = message.getUser();
        TextFormatWrapper t1 = MessageFormatter.format(user.getName(), "28/28/28", message.toString());
        System.err.println(source.getEmail() + " - [Diz]: " + message.toString());
        addMessageToFlow(t1);

    }



    public void addMessageToFlow(TextFormatWrapper text){
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.date);
        messagesArea.getChildren().add(text.text);
//        scrollPane.setVvalue(1.0);
    }

    @Override
    public void receiveFileAsMessageFromUser(FileAsMessage fileAsMessage) throws RemoteException {
        String clientpath = "/tmp/client/";
        try {
            saveFileRecived(fileAsMessage, clientpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileRecived(FileAsMessage fileAsMessage, String path) throws IOException {

        File clientpathfile = fileAsMessage.content;

        FileOutputStream out;

        out = new FileOutputStream(clientpathfile);
        out.write(fileAsMessage.getByte());
        out.flush();
        out.close();
    }

    @Override
    public void reciveMessageFromGroup(Message message, ChatGroup source) throws RemoteException {
        System.out.println(source.getName() + " - [Diz]: " + message.toString());

    }

    @Override
    public void reciveFileAsMessageFromGroup(FileAsMessage fileAsMessage, ChatGroup user) throws RemoteException {
        String clientpath = "/tmp/client/";
        try {
            saveFileRecived(fileAsMessage, clientpath);
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
    public boolean equals(Object obj) {
        User otherUser = (User) obj;
        // COnfia no lucas rafael ;)
        return this.user.equals(otherUser);
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

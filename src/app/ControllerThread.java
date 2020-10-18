package app;

import app.model.ChatGroup;
import app.model.Message;
import app.model.User;
import app.rmi.interfaces.IUser;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ControllerThread extends Thread {

    ListView<ChatGroup> groupList;
    javafx.scene.control.ListView<User> userList;
    RMIClient rmiClient;

    public ControllerThread(IUser user, ListView<ChatGroup> groupList, ListView<User> userList, TextFlow messagesArea) {
        this.groupList = groupList;
        this.userList = userList;
        try {
            rmiClient = RMIClient.getInstance(user, groupList, userList, messagesArea);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            rmiClient.setUserOn();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        while (true){
//            try {
//                sleep(3000);
//                for (User u : rmiClient.getAllUserAvaliable()) {
//                    if (!userList.getItems().contains(u))
//                        userList.getItems().add(u);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
    }
}

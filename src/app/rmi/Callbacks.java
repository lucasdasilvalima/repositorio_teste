package app.rmi;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Vector;

import app.model.ChatGroup;
import app.model.Message;
import app.model.User;

public class Callbacks extends RemoteObject implements ICallbacks {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private User user;
    private Vector<User> users;

    public Callbacks (User user) {
        this.user = user;
        this.users = new Vector<User>();
    }


    @Override
    public void notifyNewUserAvailable(User user) throws RemoteException {
        if (!this.users.contains(user))
            this.users.add(user);
        // Notificar o front que a lista dele foi atualizada,
        // nao sei como fazer isso!!
    }

    @Override
    public void notifyNewChatWith(User user) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyNewChatWith(ChatGroup chatGroup) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyReceivingFileFrom(User user) throws RemoteException {
        // TODO Auto-generated method stub
        // 

    }

    @Override
    public void notifyReceivingFileFrom(User user, ChatGroup chatGroup) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyNewMessage(Message message) throws RemoteException {
        // TODO Auto-generated method stub

    }
    
}

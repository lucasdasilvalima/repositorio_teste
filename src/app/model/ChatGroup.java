package app.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatGroup implements Serializable {

    private String name;
    private final List<User> users = new ArrayList<>();
    private final Set<User> blocked = new HashSet<>();
    private User administrator;

    public ChatGroup(String name, User administrator) {
        this.name = name;
        this.administrator = administrator;
    }

    public ChatGroup() {}

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void addUser(User user) {
        if (this.blocked.contains(user)) {
            System.out.println("Usuário " + user.getName() +
                    "bloqueado: não é possível entrar.");
            return;
        }
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void blockUser(User user) {
        this.blocked.add(user);
        this.users.remove(user);
    }

    public void unblock(User user) {
        this.blocked.remove(user);
    }

    @Override
    public String toString() {
        return this.name;
    }

}

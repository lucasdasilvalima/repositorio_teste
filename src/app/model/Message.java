package app.model;

import java.io.Serializable;

public class Message implements Serializable {

    public Message(User from, String content) {
        this.content = content;
        this.from = from;
    }

    private static final long serialVersionUID = 1L;
    String content;
    User from;

    public User getUser(){
        return this.from;
    }

    @Override
    public String toString() {
        return "[DATA]: " + content;
    }
}

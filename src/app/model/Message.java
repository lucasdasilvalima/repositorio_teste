package app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    String content;
    User from;
    User to;
    final LocalDateTime now;

    public Message(User from, User to, String content) {
        this.content = content;
        this.from = from;
        this.to = to;
        now = LocalDateTime.now();
    }

    public User getTo() {
        return to;
    }

    public User getFrom() {
        return from;
    }

    public LocalDateTime getTime() {
        return now;
    }

    public String getContent() {
        return content;
    }

    public User getUser(){
        return this.from;
    }

    @Override
    public String toString() {
        return "[DATA]: " + content;
    }
}

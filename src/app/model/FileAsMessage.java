package app.model;

import java.io.File;
import java.io.Serializable;

public class FileAsMessage implements Serializable {

    public FileAsMessage(File content, byte[] data) {
        this.content = content;
        this.data = data;
        this.lenght = (int) content.length();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public byte[] getByte() {
        return this.data;
    }

    public File content;
    public User from;
    public byte[] data;
    public int lenght;
}

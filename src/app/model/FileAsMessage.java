package app.model;

import java.io.File;
import java.io.Serializable;

public class FileAsMessage implements Serializable {

    /**
     *
     * @param from QUem esta enviando o arquivo
     * @param to quem vai recebber o arquivo
     * @param content medadados do arquivo
     * @param data arquivo em formato de byte para ser transferido
     */
    public FileAsMessage(User from, User to, File content, byte[] data) {
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
    public User to;
    public byte[] data;
    public int lenght;
}

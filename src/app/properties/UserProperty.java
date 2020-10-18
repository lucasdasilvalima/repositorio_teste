package app.properties;

import app.model.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a user, student or professor.
 *
 * It is a Property type of class, so all the attributes are encapsulated with
 * properties mainly for binding purposes.
 *
 * @author lucasrafael
 */
public class UserProperty {

    private final StringProperty name = new SimpleStringProperty();

    private final StringProperty email = new SimpleStringProperty();

    private final StringProperty password = new SimpleStringProperty();

    public UserProperty() {
    }

    public UserProperty(User user) {
        this.name.set(user.getName());
        this.email.set(user.getEmail());
        this.password.set(user.getPassword());
    }

    public String getName() {
        return this.name.get();
    }

    public String getEmail() {
        return this.email.get();
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public StringProperty getPasswordProperty() {
        return this.password;
    }

    public StringProperty getEmailProperty() {
        return this.email;
    }

    public void setAllProperties(User user) {
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
    }

    public String repr() {
        return "User Service [name=" + name + ", email=" + email
                + ", password=" + password;
    }

    @Override
    public String toString() {
        return this.name.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProperty that = (UserProperty) o;

        return this.email.get() == that.email.get();
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    public User constructUser() {
        return new User(getName(), getEmail(), getPassword());
    }

}

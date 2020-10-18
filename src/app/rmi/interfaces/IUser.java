package app.rmi.interfaces;

public interface IUser {

    public void setName(String name);
    public void setEmail(String email);
    public void setPassword(String pass);
    
    public String getName();
    public String getEmail();
    public String getPassword();
}

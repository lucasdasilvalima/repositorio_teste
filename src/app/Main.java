package app;

import app.dao.IUserDAO;
import app.dao.db.SQLUserDBDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static int MIN_WIDTH = 680;
    public static int MIN_HEIGTH = 480;

    public static int CHAT_MIN_WIDTH = 960;
    public static int CHAT_MIN_HEIGTH = 640;

    // O ideal é usar as properties aqui, mas por ora apenas queremos
    // fazer o projeto funcionar (tem muita coisa para arrumar:
    // conenction factory, etc)
    private static final IUserDAO userDAO = new SQLUserDBDAO();  // Todo: usar properties

    public static IUserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().
                getResource("../resources/fxml/login.fxml"));

        Scene login = new Scene(root);

        stage.setTitle("Chat");
        stage.setScene(login);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGTH);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}

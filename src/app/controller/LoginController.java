package app.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import app.Main;
import app.controller.common.NodeTransitions;
import app.properties.UserProperty;
import app.RMIClient;
import app.rmi.interfaces.IUser;
import app.session.UserSession;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController implements Initializable, NodeTransitions {

    /**
     * The url to be loaded when a recover password     action is requested.
     */
    private static final String RECOVER_PASSWORD_URL = "";

    /**
     * The user session.
     */
    private UserSession session = UserSession.getInstance();

    /**
     * Enter button. Used for a login action.
     */
    @FXML
    Button loginButton;

    /**
     * New user button. Used for a register action.
     */
    @FXML
    Button newButton;

    /**
     * Forget password button. Used to recover a password action.
     */
    @FXML
    Label forgetButton;

    /**
     * Password field.
     */
    @FXML
    PasswordField passField;

    /**
     * Name field.
     */
    @FXML
    TextField emailField;

    @FXML
    ProgressBar progressLogin;

    /**
     * A <code>UserProperty</code> used for login purposes.
     */
    private static UserProperty currentUser = new UserProperty();

    /**
     * Service that makes the login with the user stored in the data base.
     * This service creates a <code>Task</code> that will perform the login.
     *
     * The <code>Task</code> opens a a new window of the respective user
     * if the login occurs with success.
     *
     * In case that a exception occurs, the service will invoke a
     * notification to show.
     */
    private final Service<Void> loginService = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Login Service inicializado");
                    fadeTransition(progressLogin);
                    // Todo: obter usuário do banco de dados
                    currentUser = new UserProperty(Main.getUserDAO().load(emailField.getText(),
                            passField.textProperty().get()));
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                session.setSession(currentUser.constructUser());
                System.out.println("Login realizado com sucesso");

                try {
                    openChat();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    loginService.reset();
                    progressLogin.setVisible(false);
                }
            });
            task.setOnFailed(e -> {
                loginService.reset();
                System.out.println("Login falhou");
//                Platform.runLater(() -> errorNotification.show("Erro",
//                        task.getException().getMessage()));
                progressLogin.setVisible(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login incorreto");
                    alert.setHeaderText(null);
                    alert.setContentText("Os dados inseridos não estão corretos");
                    alert.showAndWait();
                });
            });

            return task;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         Bindings
        */
        loginButton.disableProperty().bind(emailField.textProperty().isEmpty()
                .or(passField.textProperty().isEmpty())
                .or(session.openProperty())
                .or(loginService.runningProperty()));

        currentUser.getNameProperty().
                bindBidirectional(this.emailField.textProperty());
        currentUser.getPasswordProperty().
                bindBidirectional(this.passField.textProperty());

        /*
         Effects
        */
//        fadeTransition(loginButton);
//        fadeTransition(newButton);
//        fadeTransition(passField);


//        /*
//         Sets the validation behavior.
//         */
//        raValidation.registerValidator(raField, Validator
//                .createEmptyValidator("Código necessário"));
//        passwordValidation.registerValidator(passField, Validator
//                .createPredicateValidator(o -> passField.getText() != null &&
//                                passField.getText().length() >= 6 &&
//                                passField.getText().length() <= 10,
//                        "Senha necessária"));
//
//        /*
//         Forces the RA field to accept only numbers
//        */
//        raField.textProperty().addListener((o, ov, nv) -> {
//            if (!nv.matches("\\d*")) {
//                raField.setText(nv.replaceAll("[^\\d]", ""));
//            }
//        });

    }

    @FXML
    private void openChat() throws Exception {
        System.out.println("openChat()");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().
                getResource("../../resources/fxml/chat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Main.MIN_WIDTH,
                Main.MIN_HEIGTH);
        Stage stage = new Stage();
        stage.setTitle("Chat de " + currentUser.getName());
        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.setMinWidth(Main.CHAT_MIN_WIDTH);
        stage.setMinHeight(Main.CHAT_MIN_HEIGTH);
        stage.setOnHiding(evt -> session.setOpen(false));
        stage.show();

    }

    @FXML
    private void openRegister(ActionEvent event) throws Exception {
        System.out.println("openRegister()");
        Parent registerPage = FXMLLoader.load(getClass().
                getResource("../../resources/fxml/register.fxml"));
        Scene newUserScene = new Scene(registerPage);
        Stage stage = (Stage) ((Node) (event).getSource()).getScene().
                getWindow();
        stage.setScene(newUserScene);
        stage.setMinWidth(Main.MIN_WIDTH);
        stage.setMinHeight(Main.MIN_HEIGTH);
        stage.show();
    }

    @FXML
    private void close() {
        Platform.exit();
        System.out.println("close()");
    }

    @FXML
    private void recoverPassword() throws Exception {
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(new URL(RECOVER_PASSWORD_URL)
                        .toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("recoverPassword()");
    }

    @FXML
    public void login() throws Exception {
        // Todo: VALIDAÇÃO
//        if (raValidation.isInvalid()) {
//            raField.requestFocus();
//        } else if (passwordValidation.isInvalid()) {
//            passField.requestFocus();
//        } else {
//            loginService.start();
//        }
        System.out.println("login()");
        loginService.start();
    }
}

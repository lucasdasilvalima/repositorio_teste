package app.controller;

import app.Main;
import app.controller.common.NodeTransitions;
import app.properties.UserProperty;
import app.util.Validation;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, NodeTransitions, Validation {
    /**
     * Back button. Used to back to a login scene.
     */
    @FXML
    private Button backButton;

    /**
     * Register button. Used for to register a new user action.
     */
    @FXML
    private Button registerButton;

    /**
     * Name field.
     */
    @FXML
    private TextField nameField;

    /**
     *  Password field.
     */
    @FXML
    private TextField passField;

    /**
     * Email field.
     */
    @FXML
    private TextField emailField;

    @FXML
    private Label invalidUsernameLabel;
    @FXML
    private Label invalidEmailLabel;
    @FXML
    private Label invalidPasswordLabel;

    /**
     * A <code>UserProperty</code> used for login purposes.
     */
    private static final UserProperty currentUser = new UserProperty();

//    @FXML
//    private PasswordField passField;

    /**
     * Service which registers the current user.
     * This service creates a <code>Task</code> that will perform the
     * registration.
     *
     * In case that a exception occurs, the service will invoke a
     * notification to show.
     */
    private final Service<Void> registerService = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Register Service inicializado");
                    Main.getUserDAO().store(currentUser.constructUser());
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                System.out.println(e.getSource().getMessage());
                System.out.println("Sucesso: cadastro realizado com sucesso");
                Platform.runLater(() -> {
                    invalidEmailLabel.setVisible(false);
                    invalidPasswordLabel.setVisible(false);
                    invalidUsernameLabel.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Cadastro realizado com sucesso");
                    alert.showAndWait();
                });
//                Platform.runLater(() -> infoNotification.show("Sucesso",
//                        "Cadastro realizado com sucesso"));
                registerService.reset();
            });
            task.setOnFailed(e -> {
                System.err.println(e.getSource().getMessage());
                System.out.println("Falha: Não foi possível cadastrar o novo usuário");
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Não foi possível realizar o cadastro: " +
                            e.getSource().getMessage());
                    alert.showAndWait();
                });
                registerService.reset();
//                Platform.runLater(() -> errorNotification.show("Não foi " +
//                                "possível cadastrar o novo usuário:",
//                        task.getException().getMessage()));
            });

            return task;
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         Effects
        */
//        fadeTransition(backButton);
//        fadeTransition(nameField);
//        fadeTransition(emailField);

//        /*
//         Bindings
//        */
        registerButton.disableProperty().bind(emailField.textProperty().isEmpty()
                .or(nameField.textProperty().isEmpty())
                .or(passField.textProperty().isEmpty())
                .or(registerService.runningProperty())
          );

        currentUser.getEmailProperty().
                bindBidirectional(this.emailField.textProperty());
        currentUser.getNameProperty().
                bindBidirectional(this.nameField.textProperty());
        currentUser.getPasswordProperty().
                bindBidirectional(this.passField.textProperty());

//        raValidation.registerValidator(raField, Validator
//                .createEmptyValidator("Senha necessária"));
//        nameValidation.registerValidator(nameField, Validator
//                .createEmptyValidator("Nome necessário"));
//
//        passwordValidation.registerValidator(passField, Validator
//                .createPredicateValidator(o -> passField.getText() != null &&
//                                passField.getText().length() >= 6 &&
//                                passField.getText().length() <= 10,
//                        "Senha necessária"));
//
//        String phonePattern = "^([1-9]{2}) (?:[2-8]|9[1-9])" +
//                "[0-9]{3}\\-[0-9]{4}$";
//        phoneValidation.registerValidator(phoneField, false,
//                Validator.createPredicateValidator(o -> phoneField.getText()
//                                == null || phoneField.getText().matches(phonePattern),
//                        "Telefone inválido"));
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
    private void backToLogin(ActionEvent event) throws Exception {
        System.out.println("backToLogin()");
        Parent newUserPage = FXMLLoader.load(getClass().
                getResource("../../resources/fxml/login.fxml"));
        Scene newUserScene = new Scene(newUserPage);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().
                getWindow();
        appStage.setScene(newUserScene);
        appStage.show();
        appStage.show();
    }

    @FXML
    public void register() throws Exception {
        System.out.println("register()");
        boolean valid = true;
        if (!isValidEmail(currentUser.getEmail())) {
            System.out.println("Email inválido");
            valid = false;
            invalidEmailLabel.setVisible(true);
        }
        if (!isValidName(currentUser.getName())) {
            System.out.println("Username inválido");
            valid = false;
            invalidUsernameLabel.setVisible(true);
        }
        if (!isValidPassword(currentUser.getPassword())) {
            System.out.println("Senha inválida");
            valid = false;
            invalidPasswordLabel.setVisible(true);
        }
        if (valid) {
            registerService.start();
        }
    }

    @FXML
    private void close() {
//        Platform.exit();
    }
}

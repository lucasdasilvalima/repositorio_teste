package app.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import app.ControllerThread;
import app.RMIClient;
import app.controller.common.MessageFormatter;
import app.controller.common.NodeTransitions;
import app.controller.common.StatusNotifierDaemon;
import app.controller.common.TextFormatWrapper;
import app.model.ChatGroup;
import app.model.Message;
import app.model.User;
import app.properties.UserProperty;
import app.rmi.interfaces.IUser;
import app.session.UserSession;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable, NodeTransitions {

    /**
     * The loggedUser session.
     */
    private final UserSession session = UserSession.getInstance();

    /**
     * The user logged in.
     */
    private UserProperty loggedUser;

    @FXML
    TextFlow messagesArea;

    @FXML
    ScrollPane scrollPane;

    @FXML
    Button fileButton;

    @FXML
    ColorPicker colorSelector;

    @FXML
    TextField inputArea;

    @FXML
    Label labelTyping;

    @FXML
    Label statusLabel;

    @FXML
    Button sendButton;

    @FXML
    ListView<ChatGroup> groupList;

    @FXML
    javafx.scene.control.ListView<User> userList;

    private StatusNotifierDaemon statusNotifierDaemon;

    private final Service sendMessage = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Message Service inicializado");
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                System.out.println("Message realizado com sucesso");
                Platform.runLater(() -> statusNotifierDaemon.addStatus("Mensagem enviada"));
                inputArea.setText("");
            });
            task.setOnFailed(e -> {
                System.out.println("Message falhou");
                Platform.runLater(() -> statusNotifierDaemon.addStatus("Mensagem não enviada"));
                inputArea.setText("");
            });

            return task;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statusNotifierDaemon = new StatusNotifierDaemon(statusLabel, 1000);
        statusNotifierDaemon.start();
        sendButton.disableProperty().bind(inputArea.textProperty().isEmpty());
        inputArea.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                sendMessage();
            }
        });
        scrollPane.setVvalue(1.0);
        IUser currentUser = session.getUser();//new User();
//        currentUser.setEmail("lucas@email.com");
//        currentUser.setName("lucas");
//        currentUser.setPassword("32496993llk");
        System.out.println("antes de instanciar");
        ControllerThread controllerThread = new ControllerThread(currentUser, groupList, userList, messagesArea);
        System.out.println("depois de instanciar");
        controllerThread.start();
//
        for (int i = 0; i < 50; i++) {
            TextFormatWrapper t1 = MessageFormatter.format("Lucas", "28/28/28", "Olá");
            addMessageToFlow(t1);
        }
        TextFormatWrapper t1 = MessageFormatter.status("Lucas", "enviou um arquivo");
        addStatusToFlow(t1);
        statusNotifierDaemon.addStatus("Mensagem enviada1");
        statusNotifierDaemon.addStatus("Mensagem enviada2");
        statusNotifierDaemon.addStatus("Mensagem enviada3");

    }

    private void addMessageToFlow(TextFormatWrapper text) {
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.date);
        messagesArea.getChildren().add(text.text);
        scrollPane.setVvalue(1.0);
    }

    private void addStatusToFlow(TextFormatWrapper text) {
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.text);
        scrollPane.setVvalue(1.0);
    }

    public void sendMessage(){
        String content = this.inputArea.getText().trim();
        System.out.println("sendMessage(): " + content);
        try {
            Message message = new Message(session.getUser(), content);

            RMIClient.getInstance().sendMessageToUser(message);
            RMIClient.getInstance().setUserOn();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage(ActionEvent event) throws Exception {
        System.out.println(event);
        sendMessage();

    }
}

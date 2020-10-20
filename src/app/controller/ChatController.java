package app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import app.RMIClient;
import app.controller.common.MessageFormatter;
import app.controller.common.NodeTransitions;
import app.controller.common.StatusNotifierDaemon;
import app.controller.common.TextFormatWrapper;
import app.model.ChatGroup;
import app.model.FileAsMessage;
import app.model.Message;
import app.model.User;
import app.properties.UserProperty;
import app.rmi.interfaces.IUser;
import app.session.UserSession;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable, NodeTransitions {

    /**
     * The loggedUser session.
     */
    private final UserSession session = UserSession.getInstance();

    public Map<User, ArrayList<Message>> usersHistory = new HashMap<>();
    public Map<Group, ArrayList<ChatGroup>> groupsHistory = new HashMap<>();
    final DateTimeFormatter messageDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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
    ListView<User> userList;

    private RMIClient rmiClient;

    private StatusNotifierDaemon statusNotifierDaemon;

    private final Service sendMessage = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    System.out.println("Message Service inicializado");
                    sendMessage();
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                System.out.println("Message realizado com sucesso");
                Platform.runLater(() -> statusNotifierDaemon.addStatus("Mensagem enviada"));
                inputArea.setText("");
                sendMessage.reset();
            });
            task.setOnFailed(e -> {
                System.out.println("Message falhou");
                Platform.runLater(() -> statusNotifierDaemon.addStatus("Mensagem não enviada"));
                inputArea.setText("");
                sendMessage.reset();
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

        try {
            rmiClient = RMIClient.getInstance(currentUser, groupList, userList, messagesArea, usersHistory, groupsHistory);
            rmiClient.setUserOn();
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro no servidor: " + e.getMessage());
                alert.show();
            });
        }

        userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                // Your action here
                System.out.println("Selected item: " + newValue);
                updateChatView(newValue);
            }
        });

//        new Thread(() -> {
//            while (true) {
//                System.out.println("[DEBUG] updateChatView thread");
//                try {
//                    Platform.runLater(() -> updateChatView(userList.getSelectionModel().getSelectedItem()));
//                    Thread.sleep(5000);
//                } catch (Exception e) {
//                    System.err.println("[DEBUG] " + e.getMessage());
//                }
//            }
//        }).start();

//        currentUser.setEmail("lucas@email.com");
//        currentUser.setName("lucas");
//        currentUser.setPassword("32496993llk");
//        ControllerThread controllerThread = new ControllerThread(currentUser, groupList, userList, messagesArea);
//        controllerThread.start();
//
//        for (int i = 0; i < 50; i++) {
//            TextFormatWrapper t1 = MessageFormatter.format("Lucas", "28/28/28", "Olá");
//            addMessageToFlow(t1);
//        }
//        TextFormatWrapper t1 = MessageFormatter.status("Lucas", "enviou um arquivo");
//        addStatusToFlow(t1);
//        statusNotifierDaemon.addStatus("Mensagem enviada1");
//        statusNotifierDaemon.addStatus("Mensagem enviada2");
//        statusNotifierDaemon.addStatus("Mensagem enviada3");


    }

    public void chooseFileAndSend (String filePath) {
        File file = new File(filePath);
        byte[] data = new byte[(int) file.length()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(data, 0, data.length);
            inputStream.close();
            sendFile(file, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile (File file, byte[] data) {
        try {
            FileAsMessage fileAsMessage = new FileAsMessage(session.getUser(), userList.getSelectionModel().getSelectedItem(), file, data);
            rmiClient.sendMessageAsFileToUser(fileAsMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateChatView(User user) {
        System.out.println("[DEBUG] updateChatView");
        if (usersHistory.containsKey(user)) {
            System.out.println("[DEBUG] mostrando mensagens do usuário " + user);
            for (Message message : usersHistory.get(user)) {
                TextFormatWrapper t = MessageFormatter.format(
                        message.getFrom().getName(),
                        messageDateFormatter.format(message.getTime()),
                        message.getContent());
                addMessageToFlow(t);
            }
        }
        scrollPane.setVvalue(1.0);
    }

    private void addMessageToFlow(TextFormatWrapper text) {
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.date);
        messagesArea.getChildren().add(text.text);
    }

    private void addStatusToFlow(TextFormatWrapper text) {
        messagesArea.getChildren().add(text.from);
        messagesArea.getChildren().add(text.text);
    }

    public void sendMessage(){
        String content = this.inputArea.getText().trim();
        System.out.println("sendMessage(): " + content);
        try {
            Message message = new Message(session.getUser(),
                    userList.getSelectionModel().getSelectedItem(),
                    content);
            rmiClient.sendMessageToUser(message);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage(ActionEvent event) throws Exception {
        if (inputArea.getText().isEmpty()) return;
        sendMessage.start();
    }
}

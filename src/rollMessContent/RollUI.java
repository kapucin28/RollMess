package rollMessContent;

import alerts.EmptyAlert;
import alerts.ExitAlert;
import interfaces.ObjectsTitles;
import interfaces.Scale;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * Created by TIMBULI REMUS K@puc!n on 07-May-16.
 * <p>
 * This is class in which the GUI and network connections
 * are initialized
 */
public class RollUI extends Pane implements Scale {

    // Menu variables---------------------------------------------------------------------------------------------------
    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu = new Menu(ObjectsTitles.fileMenu);
    private final Menu editMenu = new Menu(ObjectsTitles.editMenu);
    private final MenuItem save = new MenuItem(ObjectsTitles.save);
    private final MenuItem load = new MenuItem(ObjectsTitles.load);
    private final MenuItem exit = new MenuItem(ObjectsTitles.exit);
    private final MenuItem clear = new MenuItem(ObjectsTitles.clear);
    private final SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
    //------------------------------------------------------------------------------------------------------------------

    // Chat fields variables--------------------------------------------------------------------------------------------
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea();
    //------------------------------------------------------------------------------------------------------------------

    // Chat connexion variables-----------------------------------------------------------------------------------------
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private Object message;
    private String host = ObjectsTitles.host;
    private final int chatPort = 9000;
    private Socket chatSocket;
    //------------------------------------------------------------------------------------------------------------------

    // File Chooser-----------------------------------------------------------------------------------------------------
    private Stage fileStage = new Stage();
    private FileChooser chooser;
    private File file;
    private Object fileMessages;
    private ObjectOutputStream toFile;
    private ObjectInputStream fromFile;
    //------------------------------------------------------------------------------------------------------------------

    // Root pane variables----------------------------------------------------------------------------------------------
    private final GridPane root = new GridPane();
    //------------------------------------------------------------------------------------------------------------------

    // Constructor------------------------------------------------------------------------------------------------------
    public RollUI() {
        layoutSetup();
        chatThread();
        userActions();
    }
    //------------------------------------------------------------------------------------------------------------------

    // RollUI layout method---------------------------------------------------------------------------------------------
    private void layoutSetup() {

        // MenuBar setup------------------------------------------------------------------------------------------------
        editMenu.getItems().addAll(clear);
        fileMenu.getItems().addAll(save, load, separatorMenuItem, exit);
        menuBar.getMenus().addAll(fileMenu, editMenu);
        //--------------------------------------------------------------------------------------------------------------

        // Chat area setup----------------------------------------------------------------------------------------------
        textArea.setPrefHeight(SCREEN_HEIGHT / 3 + 75);
        textArea.setPrefWidth(SCREEN_WIDTH / 4);
        textField.setPrefWidth(SCREEN_WIDTH / 3);
        textArea.setPromptText(ObjectsTitles.textAreaPrompt);
        textField.setPromptText(ObjectsTitles.textFieldPrompt);
        //--------------------------------------------------------------------------------------------------------------

        // Root setup---------------------------------------------------------------------------------------------------
        root.add(menuBar, 0, 0);
        root.add(textField, 0, 1);
        root.add(textArea, 0, 2);
        root.setPrefWidth(SCREEN_WIDTH / 3);
        root.setPrefHeight(SCREEN_HEIGHT / 2);
        getChildren().add(root);
        //--------------------------------------------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------------------------------------------

    // Chat Thread method-----------------------------------------------------------------------------------------------
    private void chatThread() {
        new Thread(() -> {
            try {
                chatSocket = new Socket(host, chatPort);
                do {
                    fromServer = new ObjectInputStream(chatSocket.getInputStream());
                    message = fromServer.readObject();
                    textArea.appendText(ObjectsTitles.textAreaAppendReceived + message + "\n");
                } while (chatSocket.isConnected());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //------------------------------------------------------------------------------------------------------------------

    // User actions method----------------------------------------------------------------------------------------------
    private void userActions() {
        sendMessage();
        saveMessages();
        loadMessages();
        clearTable();
        exitSetup();
    }
    //------------------------------------------------------------------------------------------------------------------

    // Send message method----------------------------------------------------------------------------------------------
    private void sendMessage() {
        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    toServer = new ObjectOutputStream(chatSocket.getOutputStream());
                    toServer.writeObject(textField.getText() + "\n");
                    toServer.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textArea.appendText(ObjectsTitles.textAreaAppendSent + textField.getText() + "\n");
                textField.clear();
            }
        });
    }
    //------------------------------------------------------------------------------------------------------------------

    // Save messages to file--------------------------------------------------------------------------------------------
    private void saveMessages() {
        save.setOnAction(e -> {
            if (textArea.getText().isEmpty()) {
                new EmptyAlert();
            } else {
                fileStage = new Stage();
                chooser = new FileChooser();
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                        ObjectsTitles.extensionFilter,
                        ObjectsTitles.extensionType));
                file = chooser.showSaveDialog(fileStage);
                try {
                    toFile = new ObjectOutputStream(new FileOutputStream(file));
                    fileMessages = textArea.getText();
                    toFile.writeObject(fileMessages);
                    toFile.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    //------------------------------------------------------------------------------------------------------------------

    // Load messages from file------------------------------------------------------------------------------------------
    private void loadMessages() {
        load.setOnAction(e -> {
            fileStage = new Stage();
            chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    ObjectsTitles.extensionFilter,
                    ObjectsTitles.extensionType));
            file = chooser.showOpenDialog(fileStage);
            try {
                fromFile = new ObjectInputStream(new FileInputStream(file));
                fileMessages = fromFile.readObject();
                textArea.appendText(fileMessages.toString());
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    }
    //------------------------------------------------------------------------------------------------------------------

    // Clear table method-----------------------------------------------------------------------------------------------
    private void clearTable() {
        clear.setOnAction(e -> textArea.clear());
    }
    //------------------------------------------------------------------------------------------------------------------

    // Exit method -----------------------------------------------------------------------------------------------------
    private void exitSetup() {
        exit.setOnAction(e -> {
            e.consume();
            new ExitAlert();
        });
    }
    //------------------------------------------------------------------------------------------------------------------
}
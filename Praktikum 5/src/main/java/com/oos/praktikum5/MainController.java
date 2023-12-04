package com.oos.praktikum5;

import bank.PrivateBank;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;
import bank.exceptions.TransferAmountException;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class MainController implements Initializable {
    @FXML
    private Text text;
    @FXML
    private Button add;
    @FXML
    private ListView<String> accountListView;
    private final PrivateBank privateBank = new PrivateBank("Privatebank", 0.2,0.4,"Privatebank");
    private final ObservableList<String> accountList = FXCollections.observableArrayList();
    private Stage stage;
    private Parent root;
    private Scene scene;
    public void uptdateList(){
        accountList.clear();
        accountList.addAll(privateBank.getAllAccounts());
        accountListView.setItems(accountList);
    }
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init
        uptdateList();
        ContextMenu contextMenu= new ContextMenu();
        MenuItem viewAccount = new MenuItem("Account anzeigen");
        MenuItem deleteAccount = new MenuItem("Account löschen");
        contextMenu.getItems().addAll(viewAccount,deleteAccount);
        accountListView.setContextMenu(contextMenu);
        AtomicReference<String> selectedAccount= new AtomicReference<>();

        //löschen funktion
        deleteAccount.setOnAction(event -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("löschen");
            confirm.setContentText("You sure about that?");
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                try {
                    privateBank.deleteAccount(selectedAccount.toString().replace("[","").replace("]",""));
                }catch (AccountDoesNotExistException | IOException e){
                    throw new RuntimeException(e);
                }
                text.setText(selectedAccount+" wurde gelöscht!");
                uptdateList();
            }
        });
        //anzeigen funktion
        viewAccount.setOnAction(event -> {
            stage = (Stage) root.getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("accountView.fxml")));
                root = loader.load();
                AccountController accountController = loader.getController();
                accountController.setUp(privateBank,selectedAccount.toString().replace("[", "").replace("]",""));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene = new Scene(root);
            stage.setTitle("Privatebank");
            stage.setScene(scene);
            stage.show();
        });
    }
}

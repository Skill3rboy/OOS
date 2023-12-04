package com.oos.praktikum5;

import bank.PrivateBank;
import bank.Transaction;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class AccountController implements Initializable {
    @FXML
    public Text text;
    @FXML
    public MenuButton add;
    @FXML
    public Button back;
    @FXML
    public Parent root;
    @FXML
    public MenuItem ascending;
    @FXML
    public MenuItem descending;
    @FXML
    public MenuItem positive;
    @FXML
    public MenuItem negative;
    @FXML
    public Text accountName;
    @FXML
    public ListView<Transaction> transactionListView;
    private final ObservableList<Transaction> transactionsList= FXCollections.observableArrayList();
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
        back.setOnMouseClicked(mouseEvent ->{
            try {
                root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setTitle("Privatebank");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    public void setUp(PrivateBank privateBank, String name){
        //Init
        accountName.setText(name+" ["+privateBank.getAccountBalance(name)+"€]");
        update(privateBank.getTransactions(name));

        ContextMenu contextMenu= new ContextMenu();
        //Löschen button
        MenuItem delete = new MenuItem("Transaktion löschen");
        contextMenu.getItems().addAll(delete);
        transactionListView.setContextMenu(contextMenu);
        // Multi Threading
        AtomicReference<Transaction> selected= new AtomicReference<>();
        transactionListView.setOnMouseClicked(mouseEvent-> {
            selected.set(transactionListView.getSelectionModel().getSelectedItem());
        });

        //löschen funktion
        delete.setOnAction(event -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Transasktion Löschen");
            confirm.setContentText("You sure about that?");
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.isPresent()&& result.get()==ButtonType.OK){
                try {
                    privateBank.removeTransaction(name,selected.get());
                } catch (AccountDoesNotExistException | TransactionDoesNotExistException | IOException e) {
                    throw new RuntimeException(e);
                }
                text.setText(selected +" wurde gelöscht");
                update(privateBank.getTransactions(name));
                accountName.setText(name+" [" + privateBank.getAccountBalance(name)+"€]");
            }
        });

        // Sortierung ascending
        ascending.setOnAction(event -> update(privateBank.getTransactionsSorted(name,true)));

        //Sortierung descending
        descending.setOnAction(event -> update(privateBank.getTransactionsSorted(name,false)));

        //Sortierung positive
        positive.setOnAction(event -> update(privateBank.getTransactionsByType(name,true)));
        //Sortierung negative
        negative.setOnAction(event -> update(privateBank.getTransactionsByType(name,false)));
    }
    public void update(List<Transaction> transactionList){
        transactionsList.clear();
        transactionsList.addAll(transactionList);
        transactionListView.setItems(transactionsList);
    }
}

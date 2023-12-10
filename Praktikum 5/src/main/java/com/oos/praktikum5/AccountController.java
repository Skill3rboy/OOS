package com.oos.praktikum5;

import bank.*;
import bank.exceptions.*;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller for the Account view
 */
public class AccountController implements Initializable {
    /**
     * A list that allows listeners to track changes when they occur.
     */
    private final ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    /**
     * FX text block
     */
    @FXML
    public Text text;
    /**
     * FX Option for Sorting
     */
    @FXML
    public MenuButton options;
    /**
     * FX Back button
     */
    @FXML
    public Button back;
    /**
     * FX Add something button
     */
    @FXML
    public MenuButton add;
    /**
     * FX Root Block
     */
    @FXML
    public Parent root;
    /**
     * FX Sorting
     */
    @FXML
    public MenuItem ascending;
    /**
     * FX Sorting
     */
    @FXML
    public MenuItem descending;
    /**
     * FX sorting
     */
    @FXML
    public MenuItem positive;
    /**
     * FX sorting
     */
    @FXML
    public MenuItem negative;
    /**
     * FX Textblock for accountname
     */
    @FXML
    public Text accountName;
    /**
     * FX Listview block
     * A ListView displays a horizontal or vertical list of items from which the user may select,
     * or with which the user may interact. A ListView is able to have its generic type set to represent the type of data in the backing model.
     * Doing this has the benefit of making various methods in the ListView, as well as the supporting classes (mentioned below), type-safe.
     * In addition, making use of the generic supports substantially simplifies development of applications making use of ListView,
     * as all modern IDEs are able to auto-complete far more successfully with the additional type information.
     */
    @FXML
    public ListView<Transaction> transactionListView;
    /**
     * FX Payment add
     */
    @FXML
    public MenuItem payment;
    /**
     * FX tranfer add
     */
    @FXML
    public MenuItem transfer;

    /**
     * Private bank instance
     */
    private PrivateBank privateBank;

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
        back.setOnMouseClicked(mouseEvent -> { // Back button
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-view.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setTitle("Privatebank");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    /**
     * @param privateBank
     * @param name
     */
    public void setUp(PrivateBank privateBank, String name) {
        //Init
        this.privateBank = privateBank;
        accountName.setText(name + " [" + privateBank.getAccountBalance(name) + "€]");
        update(privateBank.getTransactions(name));
        ContextMenu contextMenu = new ContextMenu();

        //Delete button
        MenuItem delete = new MenuItem("Transaktion löschen");
        contextMenu.getItems().addAll(delete);
        transactionListView.setContextMenu(contextMenu);
        // Multi Threading
        AtomicReference<Transaction> selected = new AtomicReference<>();
        //Select the Item
        transactionListView.setOnMouseClicked(mouseEvent -> {
            selected.set(transactionListView.getSelectionModel().getSelectedItem());
        });

        //Delete function
        delete.setOnAction(event -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Transasktion Löschen");
            confirm.setContentText("You sure about that?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    privateBank.removeTransaction(name, selected.get());
                } catch (AccountDoesNotExistException | TransactionDoesNotExistException | IOException e) {
                    throw new RuntimeException(e);
                }
                text.setText(selected + " wurde gelöscht");
                update(privateBank.getTransactions(name));
                accountName.setText(name + " [" + privateBank.getAccountBalance(name) + "€]");
            }
        });

        // Sorting ascending
        ascending.setOnAction(event -> update(privateBank.getTransactionsSorted(name, true)));

        //Sorting descending
        descending.setOnAction(event -> update(privateBank.getTransactionsSorted(name, false)));

        //Sorting positive
        positive.setOnAction(event -> update(privateBank.getTransactionsByType(name, true)));
        //Sorting negative
        negative.setOnAction(event -> update(privateBank.getTransactionsByType(name, false)));

        //Payment add
        payment.setOnAction(event -> setTransaction(payment, name));
        //Transfer add
        transfer.setOnAction(event -> setTransaction(transfer, name));
    }

    /**
     * Updates the List
     *
     * @param transactionList
     */
    private void update(List<Transaction> transactionList) {
        transactionsList.clear();
        transactionsList.addAll(transactionList);
        transactionListView.setItems(transactionsList);
    }

    /**
     * Adds a Transaction
     *
     * @param menuItem
     * @param name
     */
    private void setTransaction(MenuItem menuItem, String name) {
        Dialog<Transaction> dialog = new Dialog<>();
        GridPane gridPane = new GridPane();

        // Label is a non-editable text control.
        Label date = new Label("Datum: ");
        Label amount = new Label("Betrag: ");
        Label description = new Label("Beschreibung: ");
        Label incomingInterest_sender = new Label();
        Label outgoingInterest_recipient = new Label();

        //Text input component that allows a user to enter a single line of unformatted text.
        TextField dateText = new TextField();
        TextField amountText = new TextField();
        TextField descriptionText = new TextField();
        TextField incomingInterest_senderText = new TextField();
        TextField outgoingInterest_recipientText = new TextField();

        //Grid just like HTML/CSS
        gridPane.add(date, 1, 1);
        gridPane.add(dateText, 2, 1);
        gridPane.add(description, 1, 2);
        gridPane.add(descriptionText, 2, 2);
        gridPane.add(amount, 1, 3);
        gridPane.add(amountText, 2, 3);
        gridPane.add(incomingInterest_sender, 1, 4);
        gridPane.add(incomingInterest_senderText, 2, 4);
        gridPane.add(outgoingInterest_recipient, 1, 5);
        gridPane.add(outgoingInterest_recipientText, 2, 5);


        ButtonType okButton = new ButtonType("Hinzufügen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().setContent(gridPane);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        Alert invalid = new Alert(Alert.AlertType.ERROR);
        dialog.show();
        //Payment Case
        if (Objects.equals(menuItem.getId(), "payment")) {
            dialog.setTitle("Payment");
            incomingInterest_sender.setText("Incoming interest: ");
            outgoingInterest_recipient.setText("Outgoing interest: ");
            dialog.setResultConverter(buttonType -> {
                if (buttonType == okButton) {
                    if (Objects.equals(dateText.getText(), "") || // Check if not empty
                            Objects.equals(amountText.getText(), "") ||
                            Objects.equals(descriptionText.getText(), "") ||
                            Objects.equals(incomingInterest_senderText.getText(), "") ||
                            Objects.equals(outgoingInterest_recipientText.getText(), "")) {
                        invalid.setContentText("Ungültige Werte");
                        Optional<ButtonType> optional = invalid.showAndWait();
                        if (optional.isPresent() && optional.get() == ButtonType.OK) {
                            text.setText("Es wurde nichts gemacht");
                        }
                    } else {
                        Payment payment = new Payment(dateText.getText(),
                                Double.parseDouble(amountText.getText()),
                                descriptionText.getText(),
                                Double.parseDouble(incomingInterest_senderText.getText()),
                                Double.parseDouble(outgoingInterest_recipientText.getText()));
                        try {
                            privateBank.addTransaction(name, payment);
                            text.setText("Neues Payment hinzugefügt");
                        } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                 TransactionAttributeException | IncomingException | OutgoingException |
                                 IOException e) {
                            throw new RuntimeException(e);
                        }

                        update(privateBank.getTransactions(name));
                        accountName.setText(name + " [" + privateBank.getAccountBalance(name) + " €]");
                    }
                }
                return null;
            });
        } else if (Objects.equals(menuItem.getId(), "transfer")) { // Transfer case
            incomingInterest_sender.setText("Sender: ");
            outgoingInterest_recipient.setText("Empfänger: ");

            dialog.setResultConverter(buttonType -> {
                if (buttonType == okButton) {
                    if (Objects.equals(dateText.getText(), "") ||// Check if not empty
                            Objects.equals(amountText.getText(), "") ||
                            Objects.equals(descriptionText.getText(), "") ||
                            Objects.equals(incomingInterest_senderText.getText(), "") ||
                            Objects.equals(outgoingInterest_recipientText.getText(), "")) {
                        invalid.setContentText("Ungültige Werte");
                        Optional<ButtonType> optional = invalid.showAndWait();
                        if (optional.isPresent() && optional.get() == ButtonType.OK) {
                            text.setText("Es wurde nicht gemacht");
                        }
                    } else {
                        if (outgoingInterest_recipientText.getText().equals(name)) { // Incoming Transfer case
                            dialog.setTitle("Incomingtransfer");
                            IncomingTransfer incomingTransfer = new IncomingTransfer(dateText.getText(),
                                    Double.parseDouble(amountText.getText()),
                                    descriptionText.getText(),
                                    incomingInterest_senderText.getText(),
                                    outgoingInterest_recipientText.getText());
                            try {
                                privateBank.addTransaction(name, incomingTransfer);
                                text.setText("Der Incomingtransfer wurde hinzugefügt");
                            } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                     TransactionAttributeException | IncomingException | IOException |
                                     OutgoingException e) {
                                throw new RuntimeException(e);
                            }
                            update(privateBank.getTransactions(name));
                            accountName.setText(name + " [" + privateBank.getAccountBalance(name) + "€]");
                        } else if (incomingInterest_senderText.getText().equals(name)) {// Outgoing Transfer case
                            dialog.setTitle("Outgoingtransfer");
                            OutgoingTransfer outgoingTransfer = new OutgoingTransfer(dateText.getText(),
                                    Double.parseDouble(amountText.getText()),
                                    descriptionText.getText(),
                                    incomingInterest_senderText.getText(),
                                    outgoingInterest_recipientText.getText());
                            try {
                                privateBank.addTransaction(name, outgoingTransfer);
                                text.setText("Outgoing Transfer hinzugefügt");
                            } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                                     TransactionAttributeException | IncomingException | OutgoingException |
                                     IOException e) {
                                throw new RuntimeException(e);
                            }
                            update(privateBank.getTransactions(name));
                            accountName.setText(name + " [" + privateBank.getAccountBalance(name) + "€]");
                        }
                    }
                }
                return null;
            });
        }
    }
}


package com.oos.praktikum5;

import bank.PrivateBank;
import bank.exceptions.*;
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

        add.setOnMouseClicked(event -> {
            text.setText("");
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Account hinzufügen");
            dialog.setHeaderText("Account hinzufügen");
            dialog.getDialogPane().setMinWidth(300);

            Label label = new Label("Name: ");
            TextField textField = new TextField();
            GridPane gridPane = new GridPane();
            gridPane.add(label,2,1);
            gridPane.add(textField,3,1);
            dialog.getDialogPane().setContent(gridPane);
            dialog.setResizable(true);
            ButtonType buttonTypeOK = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOK);
            dialog.setResultConverter(buttonType -> {
                if(buttonType== buttonTypeOK){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if(!Objects.equals(textField.getText(),"")){
                        try {
                            privateBank.createAccount(textField.getText());
                            text.setText("Account erstellt");
                        } catch (AccountAlreadyExistsException e) {
                            alert.setContentText("Account vorhanden");
                            Optional<ButtonType> optional = alert.showAndWait();
                            if(optional.isPresent() && optional.get() == ButtonType.OK)
                                text.setText("Account exisitiert");
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        uptdateList();
                    }else {
                        alert.setContentText("Ungültig");
                        Optional<ButtonType> optional = alert.showAndWait();
                        if(optional.isPresent() && optional.get() == ButtonType.OK)
                            text.setText("Account ungültig");
                    }
                }
                return null;
            });
            dialog.show();
        });
    }
}

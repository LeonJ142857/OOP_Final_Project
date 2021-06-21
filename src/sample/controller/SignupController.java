package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.HelperMethods.signUpShake;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpUsername;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private Button signUpLoginButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event-> {
            createUser();
        });
        signUpLoginButton.setOnAction(event->{
            //Take users to Login Screen
            signUpLoginButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
    private void createUser(){
        String name = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUsername.getText();
        String password = signUpPassword.getText();

        if(!name.equals("") || !lastName.equals("") || !userName.equals("") || !password.equals("")){
            User user = new User(name, lastName, userName, password);
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.signUpUser(user);
        } else{
            signUpShake(signUpFirstName, signUpLastName, signUpUsername, signUpPassword);
        }
    }
}
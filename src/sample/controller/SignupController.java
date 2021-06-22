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
        signUpButton.setOnAction(event-> { //signUpButton event handling
            createUser();
        });
        signUpLoginButton.setOnAction(event->{ //signUpLoginButton event handling
            //Take users to Login Screen
            signUpLoginButton.getScene().getWindow().hide(); // hide the current window
            FXMLLoader loader = new FXMLLoader(); //create FXMLLoader
            loader.setLocation(getClass().getResource("/sample/view/login.fxml")); //set file location
            try {
                loader.load(); //load fxml
            } catch (IOException e) { //loader failure exception
                e.printStackTrace();
            }
            Parent root = loader.getRoot(); //get the root pane of fxml
            Stage stage = new Stage(); //create stage
            stage.setScene(new Scene(root)); //set the scene of stage
            stage.show(); //show the stage
        });
    }
    private void createUser(){
        //get string from text fields
        String name = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUsername.getText();
        String password = signUpPassword.getText();

        // if no field is empty
        if(!name.equals("") || !lastName.equals("") || !userName.equals("") || !password.equals("")){
            User user = new User(name, lastName, userName, password); //encapsulate
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.signUpUser(user);
        } else{
            //shake the sign up text fields
            signUpShake(signUpFirstName, signUpLastName, signUpUsername, signUpPassword);
        }
    }
}
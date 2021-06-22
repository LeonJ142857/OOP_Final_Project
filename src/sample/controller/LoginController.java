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
import sample.model.Task;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.HelperMethods.loginShake;

public class LoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginSignUpButton;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler(); //instantiate custom made databaseHandler object

        loginButton.setOnAction(event->{//event handler for loginSignUpButton
            String loginText = loginUsername.getText().trim();//trim for any whitespaces
            String loginPwd = loginPassword.getText().trim();

            //create User object and set the username and password
            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            //get ResultSet from databaseHandler fetching data from database
            ResultSet userRow =  databaseHandler.getUser(user);
            try {
                if (userRow.next()) { //userRow is not empty
                    try {
                        Task.userId = userRow.getInt("userid");
                    } catch (SQLException throwables) { //SQLException handling when fail to fetch data
                        throwables.printStackTrace();
                    }
                    showToDoListScreen(); //takes the user to ToDoListScreen
                }
                else //userRow ResultSet is null
                    loginShake(loginUsername, loginPassword); //shake the login and password field
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        loginSignUpButton.setOnAction(event->{//event handler for loginSignUpButton
            //Take users to Sign Up screen
            loginSignUpButton.getScene().getWindow().hide(); //hide the current window
            FXMLLoader loader = new FXMLLoader(); //instantiate fxml loader
            loader.setLocation(getClass().getResource(
                    "/sample/view/signup.fxml")); //get the fxml we want to load
            try {
                loader.load(); //load the fxml file
            } catch (IOException e) { //exception handling if loading of fxml file fails
                e.printStackTrace();
            }
            Parent root = loader.getRoot(); //get the root pane of the fxml file loaded
            Stage stage = new Stage(); //create a new stage
            stage.setScene(new Scene(root)); //set the scene of the stage to contain root
            stage.showAndWait(); //show the stage and wait till other process
        });
    }
    private void showToDoListScreen(){
        loginSignUpButton.getScene().getWindow().hide(); //hide the current window
        FXMLLoader loader = new FXMLLoader(); // instantiate fxmlLoader
        loader.setLocation(getClass().getResource("/sample/view/list.fxml")); //load fxml
        try {
            loader.load(); //load the fxml
        } catch (IOException e) { //exception handling when laoding fails
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

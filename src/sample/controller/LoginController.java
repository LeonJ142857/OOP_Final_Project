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
        databaseHandler = new DatabaseHandler();

        loginButton.setOnAction(event->{
            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow =  databaseHandler.getUser(user);
            try {
                if (userRow.next()) {
                    try {
                        Task.userId = userRow.getInt("userid");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    showToDoListScreen();
                }
                else
                    loginShake(loginUsername, loginPassword);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        loginSignUpButton.setOnAction(event->{
            //Take users to Sign Up screen
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "/sample/view/signup.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
    private void showToDoListScreen(){
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/list.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

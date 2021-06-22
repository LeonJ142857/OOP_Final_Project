package sample;

import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.animations.Shake;

public class HelperMethods {
    //shake the fields in login window
    public static void loginShake(TextField loginUsername, PasswordField loginPassword){
        Shake shakerUsername = new Shake(loginUsername);
        Shake shakerPassword = new Shake(loginPassword);
        shakerUsername.shake();
        shakerPassword.shake();
    }
    //shake all the fields in sign up window
    public static void signUpShake(TextField signUpFirstName, TextField signUpLastName,
                                   TextField signUpUsername, PasswordField signUpPassword){
        Shake firstNameShaker = new Shake(signUpFirstName);
        Shake lastNameShaker = new Shake(signUpLastName);
        Shake userNameShaker = new Shake(signUpUsername);
        Shake passwordShaker = new Shake(signUpPassword);
        firstNameShaker.shake();
        lastNameShaker.shake();
        userNameShaker.shake();
        passwordShaker.shake();
    }
    //shake all the fields in task list window
    public static void listShake(TextField listTaskField, TextField listDescriptionField,
                                 DatePicker listDeadlineField){
        Shake listTaskFieldShaker = new Shake(listTaskField);
        Shake listDescriptionFieldShaker = new Shake(listDescriptionField);
        Shake listDeadlineFieldShaker = new Shake(listDeadlineField);
        listTaskFieldShaker.shake();
        listDescriptionFieldShaker.shake();
        listDeadlineFieldShaker.shake();
    }
}

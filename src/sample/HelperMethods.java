package sample;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.animations.Shake;

public class HelperMethods {
    public static void loginShake(TextField loginUsername, PasswordField loginPassword){
        Shake shakerUsername = new Shake(loginUsername);
        Shake shakerPassword = new Shake(loginPassword);
        shakerUsername.shake();
        shakerPassword.shake();
    }
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
}

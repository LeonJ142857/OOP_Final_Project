package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private final TranslateTransition translateTransition;
    public Shake(Node node){
        translateTransition = new TranslateTransition(Duration.millis(30), node);
        translateTransition.setFromX(0f);
        translateTransition.setFromY(0f);
        translateTransition.setByX(10f);
        translateTransition.setByY(5f);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
    }
    public void shake(){translateTransition.play();
    }
}

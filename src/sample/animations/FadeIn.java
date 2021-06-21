package sample.animations;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeIn {
    private final FadeTransition fadeTransition;
    public FadeIn(Node node, long duration){
        fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setFromValue(0f);
        fadeTransition.setToValue(1f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }
    public void fade(){
        fadeTransition.playFromStart();
    }
}

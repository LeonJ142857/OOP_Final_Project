package sample.animations;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeOut {
    private final FadeTransition fadeTransition;
    public FadeOut(Node node, long duration, int cycleCount){
        fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setFromValue(1f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(cycleCount);
        fadeTransition.setAutoReverse(true);
    }
    public void fade(){
        fadeTransition.playFromStart();
    }
}

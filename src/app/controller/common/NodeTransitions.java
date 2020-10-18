package app.controller.common;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Interface responsible for providing default methods for transition node
 * animations.
 *
 * TODO: Improve transitions methods.
 */
public interface NodeTransitions {

    default void fadeTransition(Node e){
        if (!e.isVisible()) e.setVisible(true);
        FadeTransition x=new FadeTransition(new Duration(600),e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.EASE_IN);
        x.play();
    }

}

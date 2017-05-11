package application;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class carousel extends Application {
 
    // Width and height of image in pixels
    private final double IMG_WIDTH = 1200;
    private final double IMG_HEIGHT = 800;
 
    private final int NUM_OF_IMGS = 3;
    private final int SLIDE_FREQ = 4; // in secs
 
    @Override
    public void start(Stage stage) throws Exception {
        //root code
        StackPane root = new StackPane();
 
        Pane clipPane = new Pane();
        // To center the slide show incase maximized
        clipPane.setMaxSize(IMG_WIDTH, IMG_HEIGHT);
        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));
 
        HBox imgContainer = new HBox();
        //image view
        ImageView imgGreen = new ImageView("https://s-media-cache-ak0.pinimg.com/736x/a1/d8/c4/a1d8c4570516473054e5f8bc42479ec5.jpg");
        ImageView imgBlue = new ImageView("https://cdn-images.threadless.com/threadless-shop/products/6889/1272x920design_01.jpg");
        ImageView imgRose = new ImageView("https://s-media-cache-ak0.pinimg.com/originals/7a/58/0d/7a580dfd535c765f15e68f18f93860f8.jpg");
 
        imgContainer.getChildren().addAll(imgGreen, imgBlue, imgRose);
        clipPane.getChildren().add(imgContainer);
        root.getChildren().add(clipPane);
 
        Scene scene = new Scene(root, IMG_WIDTH, IMG_HEIGHT);
        stage.setTitle("Image Slider");
        stage.setScene(scene);
        startAnimation(imgContainer);
        stage.show();
    }
 
    //start animation
    private void startAnimation(final HBox hbox) {
        //error occured on (ActionEvent t) line
        //slide action
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
            trans.setByX(-IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
        //eventHandler
        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
 
        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_IMGS; i++) {
            if (i == NUM_OF_IMGS) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
            }
        }
//add timeLine
        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
 
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();
    }
 
    //call main function
    public static void main(String[] args) {
        launch(args);
    }
}
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Pause {

    //Method to pause currently running timers
    static void pause(){
        for (Character c : LevelControl.getCharacters()) {
            c.stopTimer();
        }
        for (Item i : LevelControl.getItems()) {
            switch(i.getClass().getSimpleName()) {
                case("Bomb"):
                    ((Bomb) i).stopTimer();
                    break;
                case("Explosion"):
                    ((Explosion) i).stopTimer();
                    break;
            }
        }
        PlayerApplication.paused = true;
    }

    //Method to unpause currently running timers
    static void unpause(){
        for (Character c : LevelControl.getCharacters()) {
            c.resumeTimer();
        }
        PlayerApplication.paused = false;
    }
}

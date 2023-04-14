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

public class PlayerApplication extends Application {
	
	private static Group livingCharacters;
	private static Group visibleItems;
	
	private static Stage primaryStage;
	static boolean paused;
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(createScene());
		primaryStage.show();
		eventHandlerInitialisation(primaryStage.getScene());
	}
	
	
	public static void updateScene() {
		Platform.runLater(new Runnable() {
		@Override public void run() {
			Scene s = createScene();
			primaryStage.setScene(s);
		    eventHandlerInitialisation(s);
		    }
		});
	}
	
	private static Scene createScene() {
		//calculates width and height of the map from the X & Y dimensions of the grid array
		//and the size of each tile
		int width = Grid.getGrid().length * Cube.getCubeSize();
		int height = Grid.getGrid()[0].length* Cube.getCubeSize();
				
		//instantiates the map canvas and loads image
		ImageView imageView = new ImageView(Grid.getMapImage());
			     
		//adds player and map to the group and adds them to the application
		//Once items and NPC are programmed they'll need to be added here
		livingCharacters = loadImageBlocks(new ArrayList<ImageBlock>(LevelControl.getCharacters()));
		visibleItems = loadImageBlocks(new ArrayList<ImageBlock>(LevelControl.getItems()));
				
		Group group = new Group(imageView, livingCharacters, visibleItems); 
		return new Scene(group, width, height); 
	}

	private static Group loadImageBlocks(ArrayList<ImageBlock> imageArray) {
		Group temp = null;
		for (ImageBlock c : imageArray) {
			if (temp != null) {
				temp = new Group(temp, c);
			} else {
				temp = new Group(c);
			}
		}
		return temp;
	}

	//Instantiates events for Application
	private static void eventHandlerInitialisation(Scene scene) {
		//This runs when a key is pressed
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				//calls keyPressed method and passes the code of the pressed key
				keyPressed(key.getCode().toString());
			}
		});
		//This runs when a key is released
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				LevelControl.getPlayer().direction=null;
			}
		});
		//This runs when the User tries to close the application 
		scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				//This just stops the timer from continuously ticking after the program has closed
				//Will need to add the other timers into here 		
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
			}
		});
	}
		
	private static void keyPressed(String key) {
		if (key == "ESCAPE") {

			if (paused == false) {
				//Calls pause method within Pause.
				Pause.pause();
			} else if (paused == true){
				//Calls unpause method within Pause
				Pause.unpause();
				}
			} else {
				LevelControl.getPlayer().direction=key;
				LevelControl.getPlayer().updateCharacterView("Player/");
			}

	}

	//build the level from the provided level file
	//WIP - Currently using hardcoded level
	public static void main(String[] args) throws NumberFormatException, IOException {
		LevelControl.initialiseLevel("Level-1");
		launch(args);
		paused = false;
	}
}
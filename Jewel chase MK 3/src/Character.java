import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public abstract class Character extends ImageBlock{
	
	//direction contains the direction the character is facing
	protected String direction;
	
	//int movementSpeed contains the length between each timer tick
	//is defined in each character subclass
	protected int movementSpeed;
	
	//The java time class that allows us to regularly schedule events
	protected Timer timer;
	
	//abstract class forcing all concrete subclasses of character to implement timerTick() 
	protected abstract void timerTick();
	
	//Constructor instantiates the timer of each character and 
	public Character (int x, int y, int movementSpeed, String direction, String path) {
		super(path + direction + ".png", x, y);
		this.direction =  direction;
		this.movementSpeed = movementSpeed;
		 
		
		timer = new Timer();
		//creates timer and sets it to tick at the movement speed
		timer.scheduleAtFixedRate(new TimerTask(){

	          @Override
	          public void run() {
	        	  //Runs the timerTick method that's implemented in the sub-most class every movement speed milliseconds
	        	  timerTick();
	          }
	    }, 0, movementSpeed);
	}
	
	
	//moves the character and checks for a collision with 
	protected void moveWithCollision(int x, int y) {
		move(x, y);
		checkForCollisionWithAssassin(x,y);
	}
	
	protected void destroySelf() {
		System.out.println("");
		System.out.println("Character death");
		System.out.println("dead character = " + getClass().getSimpleName());
		System.out.println("Killed at (" + getxLocation() + "," + getyLocation() + ")");
		System.out.println("Player score = " + Player.getScore());
		LevelControl.removeCharacter(this);
		PlayerApplication.updateScene();
		stopTimer();
	}
	

	protected void destroyCharacter(Character i) {
		Platform.runLater(new Runnable() {
			@Override public void run() {
				destroySelf();
			}
		});
	}
	
	protected void simpleMovement() {
		switch (direction) {
		case "UP":
			moveWithCollision(getxLocation(), getyLocation() - 1);
			break;
		case "DOWN":
			moveWithCollision(getxLocation(), getyLocation() + 1);
			break;
		case "LEFT":
			moveWithCollision(getxLocation() - 1, getyLocation());
			break;
		case "RIGHT":
			moveWithCollision(getxLocation() + 1, getyLocation());
			break;
		}
	}
	
	private void checkForCollisionWithAssassin(int x, int y) {
		Boolean flag = false;
		for (Character c : LevelControl.getCharacters()) {
			if (c.getxLocation() == x && c.getyLocation() == y 
					&& c != this
					&& c.getClass().getSimpleName() == "FlyingAssassin") {
				flag = true;
			}
		}
		
		if (flag) {
			destroySelf();
		}
	}
	
	public void stopTimer() {
		timer.cancel();
	}

	public void resumeTimer() {
		timer = new Timer();
		//creates timer and sets it to tick at the movement speed
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				//Runs the timerTick method that's implemented in the sub-most class every movement speed milliseconds
				timerTick();
			}
		}, 0, movementSpeed);
	}
	
	protected void updateCharacterView(String path) {
		updateImageView(path + direction + ".png");
	}


}

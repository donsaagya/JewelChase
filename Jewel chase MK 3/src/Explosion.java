import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends NonCollectableItem implements BoundaryCheck {

	private static final String EXPLOSION_PATH = "ItemImages/Explosion/";
	private static final String PNG = ".png";
	private String explosionDirection;
	
	private static final int TICK_SPEED = 50;
	
	private int tickCount = 1;
	private Timer timer;
	
	public Explosion(int x, int y, String explosionDirection, ArrayList<Item> items,
															  ArrayList<Character> characters) {
		super(EXPLOSION_PATH + explosionDirection + "/" + 4 + PNG, x, y);
		this.explosionDirection = explosionDirection;

		createTimer();
		destroyStuffInCube(items, characters);
		
	}
	
	private void destroyStuffInCube(ArrayList<Item> items, ArrayList<Character> characters) {
		destroyItems(items);
		destroyCharacters(characters);
	}
	
	private void destroyItems(ArrayList<Item> items) {
		try {
		for(Item i: items) {
			if(i.getxLocation() == getxLocation() && i.getyLocation() == getyLocation()
					 && !(i.getClass().getSimpleName().equals("Explosion"))) {
				switch (i.getClass().getSimpleName()) {
				case("Bomb"):
					((Bomb) i).explode();
				case("Door"):
				case("Gate"):
					break;
				default:
					System.out.println();
					System.out.println("Item Destroyed - " + i.getClass().getSimpleName());
					System.out.println("Item at (" + getxLocation() + "," + getyLocation() + ")");
					
					((RemoveableItem) i).removeItem(i);
				}
			}
		}
		} catch (Exception e) {
			
		}
	}
	
	private void destroyCharacters(ArrayList<Character> characters) {
		for(Character c: characters) {
			if(c.getxLocation() == getxLocation() && c.getyLocation() == getyLocation()) {
					c.destroyCharacter(c);
				}
			}
		
	}
	
	private void createTimer() {
		timer = new Timer();
		//creates timer and sets it to tick at the movement speed
		timer.scheduleAtFixedRate(new TimerTask(){

	          @Override
	          public void run() {
	        	  //Runs the timerTick method that's implemented in the sub-most class every movement speed milliseconds
	        	  timerTick();
	          }
	    }, 0, TICK_SPEED);
	}
	
	private void timerTick() {
		switch (tickCount) {
			case 5:
				timer.cancel();
				removeItem(this);
				break;
			case 2:
				extendExplosion();
			default:
				updateImageView(EXPLOSION_PATH + explosionDirection + "/" + tickCount + PNG);
				tickCount++;
		}
	}
	
	private void extendExplosion() {
		int x = getxLocation();
		int y = getyLocation();
		switch(explosionDirection) {
			case("Centre"):
				centreExplosionExtension(x, y);
				break;
			case("Left"):
				if (checkWithinBounds(x - 1, y)){
					createExplosionItem(x - 1, y, "Left");
				}
				break;
			case("Right"):
				if (checkWithinBounds(x + 1, y)){
					createExplosionItem(x + 1, y, "Right");
				}
				break;
			case("Up"):
				if (checkWithinBounds(x, y - 1)){
					createExplosionItem(x, y - 1, "Up");
				}
				break;
			case("Down"):
				if (checkWithinBounds(x, y + 1)){
					createExplosionItem(x, y + 1, "Down");
				}
				break;
		}
	}
	
	private void centreExplosionExtension(int x, int y) {
		if (checkWithinBounds(x - 1, y)){
			createExplosionItem(x - 1, y, "Left");
		}
		if (checkWithinBounds(x + 1, y)){
			createExplosionItem(x + 1, y, "Right");
		}
		if (checkWithinBounds(x, y - 1)){
			createExplosionItem(x, y - 1, "Up");
		}
		if (checkWithinBounds(x, y + 1)){
			createExplosionItem(x, y + 1, "Down");
		}
	}
	
	private boolean checkWithinBounds(int x, int y) {
		return checkWithinBounds(new ArrayList<Integer> (Arrays.asList(x,y)), Grid.getGrid());
	}
	
	private void createExplosionItem(int x, int y, String direction) {
		String[] itemData = {"Explosion", "" + x, "" + y, direction};
		LevelControl.createExplosion(itemData);
		PlayerApplication.updateScene();
	}
	
	public void stopTimer() {
		timer.cancel();
	}

	public void resumeTimer() {
		createTimer();
	}
}

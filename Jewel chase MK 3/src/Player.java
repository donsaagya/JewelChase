import java.util.ArrayList;

public class Player extends ComplexMovementCharacter{

	private static final int PLAYER_SPEED = 100;
	private static final String IMAGE_PATH = "Player/";
	private static int score;
	
	public Player(int x, int y, String dir) {
		super(x, y, PLAYER_SPEED, dir, IMAGE_PATH);
		direction = null;
		score = 0;
	}
	
	public void playerMoveUp() {
		ArrayList<String> availablePaths = Grid.getGrid()[getxLocation()][getyLocation()].getAvailablePaths();
		moveIfNotNull(availablePaths.get(2));
	}
	
	public void playerMoveDown() {
		ArrayList<String> availablePaths = Grid.getGrid()[getxLocation()][getyLocation()].getAvailablePaths();
		moveIfNotNull(availablePaths.get(3));
	}
	
	public void playerMoveRight() {
		ArrayList<String> availablePaths = Grid.getGrid()[getxLocation()][getyLocation()].getAvailablePaths();
		moveIfNotNull(availablePaths.get(1));
	}
	
	public void playerMoveLeft() {
		ArrayList<String> availablePaths = Grid.getGrid()[getxLocation()][getyLocation()].getAvailablePaths();
		moveIfNotNull(availablePaths.get(0));
	}
	
	private void moveIfNotNull(String destination) {
		if (destination != null) {
			String[] coods = destination.split(",");
			moveWithCollision(Integer.parseInt(coods[0]),
							  Integer.parseInt(coods[1]));
		}
	}
	
	@Override
	protected void timerTick() {
		if (direction != null) {
			switch(direction) {
			case ("RIGHT") :
				playerMoveRight();
				break;
			case ("LEFT") :
				playerMoveLeft();
				break;
			case ("DOWN") :
				playerMoveDown();
				break;
			case ("UP") :
				playerMoveUp();
				break;	
			}	
		}
	}

	public static int getScore() {
		return score;
	}

	public static void addToScore(int score) {
		Player.score += score;
	}
}

public class Door extends NonRemoveableItem{

	private static final String DOOR_PATH = "ItemImages/Door.png";
	
	public Door(int x, int y) {
		super(DOOR_PATH, x, y);
		// TODO Auto-generated constructor stub
	}

	public static void checkForGameEnd() {
		endGame();
	}
	
	private static void endGame() {
		
	}

}

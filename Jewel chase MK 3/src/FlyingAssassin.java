public class FlyingAssassin extends Character{
	
	private static final int MOVEMENT_SPEED = 500;
	private static final String IMAGE_PATH = "FlyingAssassin/";
	
	public FlyingAssassin(int x, int y, String direction) {
		super(x, y, MOVEMENT_SPEED , direction, IMAGE_PATH);
		this.direction = direction;
	}

	@Override
	protected void timerTick() {
		int gridWidth = Grid.getGrid().length;
		int gridHeight = Grid.getGrid()[0].length;
		checkForMapEdge(gridWidth, gridHeight);
		simpleMovement();
		updateCharacterView(IMAGE_PATH);
	}
	
	private void checkForMapEdge(int gridWidth, int gridHeight) {
		switch (direction) {
		case "UP":
			if (getyLocation() == 0) {
				direction = "DOWN";
			}
			break;
		case "DOWN":
			if (getyLocation() == gridHeight - 1) {
				direction = "UP";
				updateCharacterView(IMAGE_PATH);
			}
			break;
		case "LEFT":
			if (getxLocation() == 0) {
				direction = "RIGHT";
				updateCharacterView(IMAGE_PATH);
			}
			break;
		case "RIGHT":
			if (getxLocation() == gridWidth - 1) {
				direction = "LEFT";
				updateCharacterView(IMAGE_PATH);
			}
			break;
		}
	}

}

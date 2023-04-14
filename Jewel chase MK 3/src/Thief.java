import java.util.ArrayList;

public class Thief extends ComplexMovementCharacter  implements BoundaryCheck {

	private static final int MOVEMENT_SPEED = 400;
	private static final String IMAGE_PATH = "Thief/";
	private String colour;
	
	public Thief(int x, int y, String direction, String colour) {
		super(x, y, MOVEMENT_SPEED, direction, IMAGE_PATH + colour + "/");
		this.colour = colour;
	}

	@Override
	protected void timerTick() {
		correctDirection();
		simpleMovement();
		updateCharacterView(IMAGE_PATH + colour + "/");
	}
	
	private void correctDirection() {
		switch (findCurrentState()) {
		case (1):
			turnLeft();
			break;
		case (2):
			break;
		case (3):
			turnRight();
			break;
		case (4):
			turnBack();
			break;
		}
	}
	
	private int findCurrentState() {
		Cube[][] grid = Grid.getGrid();
		
		if (checkForStateOne(grid)) {
			return 1;
		} else if (checkForStateTwo(grid)) {
			return 2;
		} else if (checkForStateThree(grid)) {
			return 3;
		} else {
			return 4;
		}
	}
	
	private String getColourCode() {
		switch(colour) {
		case "RED" :
			return "R";
		case "GREEN" :
			return "G";
		case "BLUE" :
			return "B";
		case "YELLOW" :
			return "Y";
		case "CYAN" :
			return "C";
		case "MAGENTA": 
			return "M";
		}
		return null;
	}

	private boolean checkForStateOne(Cube[][] grid) {
		ArrayList<Integer> cords = getCubeOnLeft();
		
		if(checkWithinBounds(cords, grid)) {
			if (checkForCollisionWithItem(cords.get(0), cords.get(1), false)) {
				return (grid[cords.get(0)][cords.get(1)].getUniqueColoursInCube().contains(getColourCode()));
			}
		}
		return false;
	}
	
	private boolean checkForStateTwo(Cube[][] grid) {
		ArrayList<Integer> cords = getCubeOnForward();
		
		if(checkWithinBounds(cords, grid)) {

			if (checkForCollisionWithItem(cords.get(0), cords.get(1), false)) {
			return (grid[cords.get(0)][cords.get(1)].getUniqueColoursInCube().contains(getColourCode()));
			}
		}
		return false;
	}
	
	private boolean checkForStateThree(Cube[][] grid) {
		ArrayList<Integer> cords = getCubeOnRight();
		
		if(checkWithinBounds(cords, grid)) {
			if (checkForCollisionWithItem(cords.get(0), cords.get(1), false)) {
				return (grid[cords.get(0)][cords.get(1)].getUniqueColoursInCube().contains(getColourCode()));
			}
		}
		return false;
	}
		
	private ArrayList<Integer> getCubeOnLeft() {
		ArrayList<Integer> cordinates = new ArrayList<Integer>();
		switch (direction) {
		case ("DOWN"):
			cordinates.add(getxLocation() + 1);
			cordinates.add(getyLocation());
			break;
		case ("UP"):
			cordinates.add(getxLocation() - 1);
			cordinates.add(getyLocation());
			break;
		case ("RIGHT"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() - 1);
			break;
		case ("LEFT"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() + 1);
			break;
		}
		return cordinates;
	}
	
	private ArrayList<Integer> getCubeOnRight() {
		ArrayList<Integer> cordinates = new ArrayList<Integer>();
		switch (direction) {
		case ("DOWN"):
			cordinates.add(getxLocation() - 1);
			cordinates.add(getyLocation());
			break;
		case ("UP"):
			cordinates.add(getxLocation() + 1);
			cordinates.add(getyLocation());
			break;
		case ("RIGHT"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() + 1);
			break;
		case ("LEFT"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() - 1);
			break;
		}
		return cordinates;
	}
	
	private ArrayList<Integer> getCubeOnForward() {
		ArrayList<Integer> cordinates = new ArrayList<Integer>();
		switch (direction) {
		case ("DOWN"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() + 1);
			break;
		case ("UP"):
			cordinates.add(getxLocation());
			cordinates.add(getyLocation() - 1);
			break;
		case ("RIGHT"):
			cordinates.add(getxLocation() + 1);
			cordinates.add(getyLocation());
			break;
		case ("LEFT"):
			cordinates.add(getxLocation() - 1);
			cordinates.add(getyLocation());
			break;
		}
		return cordinates;
	}
	
	private void turnRight() {
		switch (direction) {
		case ("DOWN"):
			direction = "LEFT";
			break;
		case ("UP"):
			direction = "RIGHT";
			break;
		case ("RIGHT"):
			direction = "DOWN";
			break;
		case ("LEFT"):
			direction = "UP";
			break;
		}
	}

	private void turnBack() {
		switch (direction) {
		case ("DOWN"):
			direction = "UP";
			break;
		case ("UP"):
			direction = "DOWN";
			break;
		case ("RIGHT"):
			direction = "LEFT";
			break;
		case ("LEFT"):
			direction = "RIGHT";
			break;
		}
	}

	private void turnLeft() {
		switch (direction) {
		case ("DOWN"):
			direction = "RIGHT";
			break;
		case ("UP"):
			direction = "LEFT";
			break;
		case ("RIGHT"):
			direction = "UP";
			break;
		case ("LEFT"):
			direction = "DOWN";
			break;
		}
	}


}

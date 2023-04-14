import java.util.ArrayList;

public class SmartThief extends ComplexMovementCharacter {

	private static final int MOVEMENT_SPEED = 500;
	private static final String IMAGE_PATH = "SmartThief.png";
	
	public SmartThief(int x, int y, String direction) {
		super(x, y, MOVEMENT_SPEED, direction, IMAGE_PATH);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void timerTick() {
		// TODO Auto-generated method stub
		
	}
	
	private void findPath() {
		int[][] shortestPaths = dijkstrasAlgorithm(new Point(getxLocation(), getyLocation()));
	}
	
	private int[][] dijkstrasAlgorithm (Point source) {
		int width = Grid.getGrid().length;
		int height = Grid.getGrid()[0].length;
		int[][] shortestPaths = new int[width]
									   [height];
		Point[] unvisitedNodes = new Point[width * height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == source.getX() && x == source.getY()) {
					shortestPaths[x][y] = 0;
				} else {
					shortestPaths[x][y] = -1;
					unvisitedNodes[x + y] = new Point(x, y);
				}
			}
		}

		shortestPaths = recursiveFunction(shortestPaths, source, 0);
		
		return null;
		
	}
	
	private int[][] recursiveFunction(int[][] graph, Point currentVertix, int distance) {
		graph[currentVertix.getX()][currentVertix.getY()] = distance;
		
		
		return null;
	}
	

}




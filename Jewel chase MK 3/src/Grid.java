import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.*;

public class Grid {
	
	private final static String MAP_FILE_NAME = "/MapLayout.txt";
	
	//2 dimensional array holding the individual tiles
	static private Cube grid[][];
	//writable Image containing the whole map image
	static private WritableImage mapImage;
	
	
	//all the tiles that have been visted in the path finding algorithm
	private ArrayList<String> visitedTiles = new ArrayList<String>();
	
	public Grid(String levelName) throws NumberFormatException, IOException {
		buildGrid(levelName);
		buildMapImage();
		drawGrid();
		//calls path finding algorithm and passes x=0 and y=0 as starting values
		findPathways(0,0); 
		//Debugging method
		outputGrid();
	}

	//Debugging method - outputs every X & Y location and all the pathways from said location
	private static void outputGrid() {
		//Goes through the grid array outputting the pathways in each cube
		for (Cube[] cubeRow : grid) {
			for (Cube cube : cubeRow) {
				System.out.println(cube.getX() + "," + cube.getY());
				
				for (String path : cube.getAvailablePaths()) {
					System.out.print("(" + path + ")");
				}
				System.out.println();
				System.out.println();
			}
		}
	}
	
	//Fills the grid array with cubes created from data in the level file
	private static void buildGrid(String levelName) throws NumberFormatException, IOException  {
		//Instantiates Scanner for reading the Map file

		System.out.println(levelName + MAP_FILE_NAME);
		Scanner fileScanner = new Scanner(new File(levelName + MAP_FILE_NAME));

		//Reads the first two lines containing X & Y dimensions and instantiates grid
		grid = new Cube[Integer.parseInt(fileScanner.nextLine())]
						[Integer.parseInt(fileScanner.nextLine())];
				
		//Goes through the text file two lines at a time as a block of 2x2 characters makes a tile
		for (int y = 0; y < grid[0].length; y++) {
			//read the 2 lines at the one after another making into scanners 
			//using a space as the delimiter
			Scanner line1 = new Scanner(fileScanner.nextLine());
			Scanner line2 = new Scanner(fileScanner.nextLine());
			line1.useDelimiter(" ");
			line2.useDelimiter(" ");
			
			//Go through each line reading 2 characters at a tile
			//and passing the colour values and the X & Y locations to the newly created cube
			for(int x = 0; x < grid.length; x++) {
				String abColours = line1.next();
				String cdColours = line2.next();
				
				Cube temp = new Cube(x, y, abColours.charAt(0), abColours.charAt(1), 
						cdColours.charAt(0), cdColours.charAt(1));
				
				//add the newly created cube to the appropriate location in the grid
				grid[x][y] = temp;
				
			}
			line1.close();
			line2.close();
		}
		fileScanner.close();
	}
	
	//Creates an image containing every square on the map
	//Meaning we can just show the entire image instead of each individual tile
	//everytime the application refreshes
	private static void buildMapImage() throws IOException {
		
		//Instantiates useful variables
		int width = grid.length;
		int height = grid[0].length;
		int cubeSize = Cube.getCubeSize();
		//Each cube is made of 4 smaller cubes
		//This holds the width of one of the smaller cubes
		int QuaterCubeSize = Cube.getQuaterCubeSize();

		//Instantiates new WritableImage
		mapImage = new WritableImage(width * cubeSize, height * cubeSize);
		
		//Goes through every Cube in the grid 
		//and draws each of its sections to the writable image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				drawImageSection(x * cubeSize,
								 y * cubeSize,
								 grid[x][y].getaColour());
				drawImageSection(x * cubeSize + QuaterCubeSize,
						         y * cubeSize,
						         grid[x][y].getbColour());
				drawImageSection(x * cubeSize,
								 y * cubeSize + QuaterCubeSize,
								 grid[x][y].getcColour());
				drawImageSection(x * cubeSize + QuaterCubeSize,
								 y * cubeSize + QuaterCubeSize,
								 grid[x][y].getdColour());
			}
		}
	}

	//Draws a cube the size of a QuaterCube, at the location passed to it
	//in the colour passed to it
	private static void drawImageSection(
			int x, int y, String colour) {
		
		//Instantiates a PixelWriter
		//This allows us to colour individual pixels on the map 
		PixelWriter image_writer = mapImage.getPixelWriter();
		
		Color temp = null;
		
		//Sets temp to the appropriate colour
		switch(colour) {
		case "R" :
			temp = Color.RED;
			break;
		case "G" :
			temp = Color.GREEN;
			break;
		case "B" :
			temp = Color.BLUE;
			break;
		case "Y" :
			temp = Color.YELLOW;
			break;
		case "C" :
			temp = Color.CYAN;
			break;
		case "M" :
			temp = Color.MAGENTA;
			break;
		}
		
		//Goes through each pixel in the section and colours it
		for(int i = 0; i < Cube.getQuaterCubeSize(); i++) {
			for(int z = 0; z < Cube.getQuaterCubeSize(); z++) {
				image_writer.setColor(i + x, z + y, temp);
			}
		}
		
	}
	
	//Draws a black grid on the map helping to distinguish between same coloured cubes
	private static void drawGrid() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				drawSquare(x * Cube.getCubeSize(), y * Cube.getCubeSize());

			}
		}
	}
	
	//Draw 2 lines on the North and West faces of each tile
	//When called repeatedly by drawGrid over the entire map it creates the grid
	private static void drawSquare(int xOffset, int yOffset) {
		PixelWriter image_writer = mapImage.getPixelWriter();
		
		for (int i = 0; i < Cube.getCubeSize(); i++) {
				image_writer.setColor(xOffset + i, yOffset, Color.BLACK);
				image_writer.setColor(xOffset, yOffset + i, Color.BLACK);
		}
	}
	
	//This method recursively goes through every tile on the map
	//finding the tile that the player or a thief would land on
	//if there were to go in any direction
	//it then stores this information in each cube 
	//meaning every cube knows where you can go too from itself
	private void findPathways(int x, int y) {
		
		//Visited tiles is an array that contains every cube that has already been processed
		if (!visitedTiles.contains(x + "," + y)) {
			//If the tile the method is currently processing isn't on the list
			//1. Add it to the list 
			visitedTiles.add(x + "," + y);
			//2. Process it
			
			
			//adds each color in the cube to the array coloursInCube while removing duplicates
			ArrayList<String> coloursInCube = grid[x][y].getUniqueColoursInCube();
			
			String closestLeftPath = null;
			String closestRightPath = null;
			String closestVerticalPath = null;
			String closestDownwardsPath = null;
			
			//for each unique color in the cube 
			for (String color: coloursInCube) {
				//Find the closest square in any given direction
				//That has the same colour as it
				String closestLeftPathWithColor = findSimilarColor(x,y, -1, 0, color);
				String closestRightPathWithColor = findSimilarColor(x, y, +1, 0, color);
				String closestVerticalPathWithColor = findSimilarColor(x, y, 0, -1, color);
				String closestDownwardsPathWithColor = findSimilarColor(x, y, 0, +1, color);
				
				//Compare the length of the path for the current color
				//with the length of the current shortest found path
				closestLeftPath = comparePath(closestLeftPath,closestLeftPathWithColor,x,y);
				closestRightPath = comparePath(closestRightPath,closestRightPathWithColor,x,y);
				closestVerticalPath = comparePath(closestVerticalPath,closestVerticalPathWithColor,x,y);
				closestDownwardsPath = comparePath(closestDownwardsPath,closestDownwardsPathWithColor,x,y);
			}
			
			//Passes shortest paths to respective cube in grid array
			ArrayList<String> availablePaths = new ArrayList<String>(Arrays.asList(
					closestLeftPath,
					closestRightPath,
					closestVerticalPath,
					closestDownwardsPath
					));
			grid[x][y].setAvailablePaths(availablePaths);
			
			//recursePathsways is a method that calls this method again 
			//but on the destinations from this cube
			recursePathways(closestLeftPath);
			recursePathways(closestRightPath);
			recursePathways(closestVerticalPath);
			recursePathways(closestDownwardsPath);
			
		}
	}
	
	//Checks the path isn't null,
	//breaks the path into it's X & Y locations 
	//and calls findPathways on the cube
	private void recursePathways(String Path) {
		if (Path != null) {
			String[] point = Path.split(",");
			int tempX = Integer.parseInt(point[0]);
			int tempY = Integer.parseInt(point[1]);
			
			findPathways(tempX, tempY);
		}
	}
	
	//Returns the shortest path from the two provided
	private String comparePath(String pathA, String pathB, int x, int y) {
		if (pathA != null && pathB != null) {
			if (findDistance(pathA, x,y) > findDistance(pathB, x,y)) {
				return pathB;
			}
			return pathA;
		} else {
			if (pathB != null ) {
				return pathB;
			}
			return pathA;
		}
	}
	
	private int findDistance(String point ,int x1, int y1) {
		//pythagoras theorem
		String[] pointArray = point.split(",");
		int x2 = Integer.parseInt(pointArray[0]);
		int y2 = Integer.parseInt(pointArray[1]);
		
		return (int) Math.sqrt(Math.pow(Math.abs(x1-x2), 2) + Math.pow(Math.abs(y1-y2), 2));
	}
	
	//recursively goes through a line of cubes until it finds one that contains the same colour as the one passes to it
	//Or it reaches the outside of the map
	//x & y = the current x & y location
	//xInc & yInc = the x & y increment each time the method recurses 
	//colour = the colour the method is trying to locate
	private String findSimilarColor(int x, int y, int xInc, int yInc, String colour) {
		x += xInc;
		y += yInc;
		
		//Checks the location is within bounds
		if(x >= 0 && x < grid.length) {
			if(y >= 0 && y < grid[0].length) {
				
				//Checks if the cube at (x,y) contains the colour it's looking for
				if (colour.equals(grid[x][y].getaColour()) ||
						colour.equals(grid[x][y].getbColour()) ||
						colour.equals(grid[x][y].getcColour()) ||
						colour.equals(grid[x][y].getdColour())) {
					
					//If so it returns with the current location
					return (x + "," + y);
				} else {
					//Otherwise it recurses
					return findSimilarColor(x, y, xInc, yInc, colour);
				}
				
			}
		}
		return null;
	}
	
	public static Image getMapImage() {
		return mapImage;
	}
	
	public static Cube[][] getGrid() {
		return grid;
	}
	
}

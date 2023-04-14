import java.util.ArrayList;

public class Cube {
	
	//Constants containing the sizes of the cubes
	final static private int  CUBE_SIZE = 60;
	
	private ArrayList<String> availablePaths;
	
	private int x,y;
	private String aColour, bColour, cColour, dColour;
	
	//Returns the unique colors in cube
	public ArrayList<String> getUniqueColoursInCube() {
		ArrayList<String> coloursInCube = new ArrayList<String>();
		coloursInCube.add(aColour);
		
		if (!coloursInCube.contains(bColour)) {
			coloursInCube.add(bColour);
		}
		if (!coloursInCube.contains(cColour)) {
			coloursInCube.add(cColour);
		}
		if (!coloursInCube.contains(dColour)) {
			coloursInCube.add(dColour);
		}
		
		return coloursInCube;
	}
	
	public Cube(int x, int y, char aColour, 
			char bColour, char cColour, char dColour) {
		this.x = x;
		this.y = y;
		this.aColour = "" + aColour;
		this.bColour = "" + bColour;
		this.cColour = "" + cColour;
		this.dColour = "" + dColour;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public String getaColour() {
		return aColour;
	}

	public String getbColour() {
		return bColour;
	}
	
	public String getcColour() {
		return cColour;
	}

	public String getdColour() {
		return dColour;
	}

	public static int getCubeSize() {
		return CUBE_SIZE;
	}

	public static int getQuaterCubeSize() {
		return CUBE_SIZE / 2;
	}

	public ArrayList<String> getAvailablePaths() {
		return availablePaths;
	}

	public void setAvailablePaths(ArrayList<String> availablePaths) {
		this.availablePaths = availablePaths;
	}

}

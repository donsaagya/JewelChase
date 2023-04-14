import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class LevelControl {
	
	private final static String NPC_FILE_NAME = "/NPCLocations.txt";
	private final static String ITEM_FILE_NAME = "/ItemLocations.txt";
	
	private static Grid grid;
	private static ArrayList<Character> characters;
	private static ArrayList<Item> items;
	private static Timer gameTimer;
	
	private static int timeLeft = 60000;
	
	private static void startGameTimer() {
		gameTimer = new Timer();
		//creates timer and sets it to tick at the movement speed
		gameTimer.scheduleAtFixedRate(new TimerTask(){

	          @Override
	          public void run() {
	        	  timerTick();
	          }
	    }, 0, 1000);
	}
	
	public static void initialiseLevel(String levelName) throws NumberFormatException, IOException {
		computeGrid(levelName);
		computeItems(levelName);
		computeCharacters(levelName);
		startGameTimer();
	}
	
	public static void removeGate(Color c) {
		for (Item i : items) {
			if (i.getClass().getSimpleName() == "Gate") {
				Gate gate = (Gate) i;
				if (gate.getColour(gate.getColour()) == c) {
					gate.removeItem();
				}
			}
		}
	}
	
	private static void timerTick() {
		modifyTimeLeft(-1000);
	}
	
	private static void computeGrid(String levelName) throws NumberFormatException, IOException {
		grid = new Grid("Level-1");
	}
	
	private static void computeCharacters(String levelName) throws FileNotFoundException  {
		
		characters = new ArrayList<Character>();
		characters.add(new Player(0, 0, "DOWN"));
		
		Scanner fileScanner = new Scanner(new File(levelName + NPC_FILE_NAME));
		int amountOfNPCs = fileScanner.nextInt();
		fileScanner.nextLine();
		
		for (int i = 0; i < amountOfNPCs; i++) {
			String[] characterData = fileScanner.nextLine().split(",");
			
			if (characterData[0].equals("Flying")) {
				characters.add(new FlyingAssassin(Integer.parseInt(characterData[1]), 
												  Integer.parseInt(characterData[2]), 
														  		   characterData[3])); 
				
			} else if (characterData[0].equals("Thief")) {
				characters.add(new Thief(Integer.parseInt(characterData[1]), 
						 				 Integer.parseInt(characterData[2]), 
						 								  characterData[3],
						 								  characterData[4]));
						
			} else if (characterData[0].equals("SThief")) {
				characters.add(new SmartThief(Integer.parseInt(characterData[1]), 
											  Integer.parseInt(characterData[2]), 
												 			   characterData[3]));
			}
		}
		fileScanner.close();
	}
	
	private static void computeItems(String levelName) throws FileNotFoundException {

		items = new ArrayList<Item>();
		
		Scanner fileScanner = new Scanner(new File(levelName + ITEM_FILE_NAME));
		int amountOfItems = fileScanner.nextInt();
		fileScanner.nextLine();
		
		for (int i = 0; i < amountOfItems; i++) {
			String[] itemData = fileScanner.nextLine().split(",");
			createItem(itemData);
		}
		fileScanner.close();
	}
	
	private static void createItem(String[] itemData) {
		switch(itemData[0]) {
		case("Cent"):
			items.add(new Cent(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Penny"):
			items.add(new Penny(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Ruby"):
			items.add(new Ruby(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Diamond"):
			items.add(new Diamond(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Clock"):
			items.add(new Clock(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Bomb"):
			items.add(new Bomb(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Gate"):
			items.add(new Gate(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2]), itemData[3]));
			break;
		case("Lever"):
			items.add(new Lever(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2]), itemData[3]));
			break;
		case("Door"):
			items.add(new Door(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2])));
			break;
		case("Explosion"):
			items.add(new Explosion(Integer.parseInt(itemData[1]),
					Integer.parseInt(itemData[2]), itemData[3], items, characters));
			break;
		}
	}
	

	
	public static void createExplosion(String[] itemData) {
		if (itemData[0].equals("Explosion")) {
			createItem(itemData);
		}
	}
	
	public static void removeCharacter(Character c) {
		Platform.runLater(new Runnable() {
			@Override public void run() {
				int temp = characters.indexOf(c);
				if (temp != -1) {
					characters.remove(temp);
				}
			}
		});
	}
	
	public static void removeItem(Item c) {
			Platform.runLater(new Runnable() {
				@Override public void run() {
					int temp = items.indexOf(c);
					if (temp != -1) {
						items.remove(items.indexOf(c));
					}
				}
			});
//		}
	}
	
	public static ArrayList<Character> getCharacters() {
		return characters;
	}

	public static Player getPlayer() {
		return (Player) characters.get(0);
	}

	public static ArrayList<Item> getItems() {
		return items;
	}

	public static int getTimeLeft() {
		return timeLeft;
	}

	public static void modifyTimeLeft(int amount) {
		timeLeft += amount;
	}

	public static Grid getGrid() {
		return grid;
	}
}

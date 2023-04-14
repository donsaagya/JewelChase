
public abstract class CollectableItem extends RemoveableItem{

	public CollectableItem(String path, int x, int y) {
		super(path, x, y);
	}
	
	public void collectItem(String character) {
		System.out.println("");
		System.out.println("Item collected = " + getClass().getSimpleName());
		System.out.println("Collected by = " + character);
		System.out.println("Collected at (" + getxLocation() + "," + getyLocation() + ")");
		System.out.println("Player score = " + Player.getScore());
		System.out.println("Time Left = " + LevelControl.getTimeLeft());
		
		removeItem();
	}

}

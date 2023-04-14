
public class Clock extends CollectableItem{

	private static final String CLOCK_PATH = "ItemImages/Clock.png";
	private static final int TIME_INCREASE_AMOUNT = 10000;
	
	public Clock(int x, int y) {
		super(CLOCK_PATH, x, y);
	}

	@Override
	public void collectItem(String character) {
		super.collectItem(character);
		LevelControl.modifyTimeLeft(TIME_INCREASE_AMOUNT);
		System.out.println("New Time Left = " + LevelControl.getTimeLeft());
	}

}

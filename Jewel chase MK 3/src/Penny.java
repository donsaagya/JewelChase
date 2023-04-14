
public class Penny extends Loot{

	private static final String PENNY_PATH = "ItemImages/Penny.png";
	private static final int PENNY_SCORE_VALUE = 2;
	
	public Penny(int x, int y) {
		super(PENNY_PATH, x, y);
		value = PENNY_SCORE_VALUE;
	}
}
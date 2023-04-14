
public class Diamond extends Loot{

	private static final String DIAMOND_PATH = "ItemImages/Diamond.png";
	private static final int DIAMOND_SCORE_VALUE = 10;
	
	public Diamond(int x, int y) {
		super(DIAMOND_PATH, x, y);
		value = DIAMOND_SCORE_VALUE;
	}
}



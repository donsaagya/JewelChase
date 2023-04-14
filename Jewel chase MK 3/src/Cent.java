public class Cent extends Loot{

	private static final String CENT_PATH = "ItemImages/Cent.png";
	private static final int CENT_SCORE_VALUE = 1;
	
	public Cent(int x, int y) {
		super(CENT_PATH, x, y);
		value = CENT_SCORE_VALUE;
	}
}

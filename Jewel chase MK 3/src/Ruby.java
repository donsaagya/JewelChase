
public class Ruby extends Loot{

	private static final String RUBY_PATH = "ItemImages/Ruby.png";
	private static final int RUBY_SCORE_VALUE = 5;
	
	public Ruby(int x, int y) {
		super(RUBY_PATH, x, y);
		value = RUBY_SCORE_VALUE;
	}
}



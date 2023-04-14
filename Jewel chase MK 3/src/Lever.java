
public class Lever extends CollectableItem implements AddColourToImage {
	
	private static final String LEVER_PATH = "ItemImages/Lever.png";
	private static final int COLOUR_BLOCK_WIDTH = 12;
	private static final int COLOUR_BLOCK_HEIGHT = 46;
	private static final int COLOUR_BLOCK_X_OFFSET = 51;
	private static final int COLOUR_BLOCK_Y_OFFSET = 16;
	
	private String colour;
	
	public Lever(int x, int y, String colour) {
		super(LEVER_PATH, x, y);
		this.colour = colour;
		
		updateImageView(addColourBlockToImage(getImage(), COLOUR_BLOCK_WIDTH,
														  COLOUR_BLOCK_HEIGHT,
														  COLOUR_BLOCK_X_OFFSET,
														  COLOUR_BLOCK_Y_OFFSET,
														  colour));
	}

	@Override
	public void collectItem(String character) {
		super.collectItem(character);
		LevelControl.removeGate(getColour(colour));
	}
			
}

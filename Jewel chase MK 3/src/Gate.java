public class Gate extends NonCollectableItem implements AddColourToImage {
	
	private static final String GATE_PATH = "ItemImages/Gate.png";
	private static final int COLOUR_BLOCK_WIDTH = 34;
	private static final int COLOUR_BLOCK_HEIGHT = 39;
	private static final int COLOUR_BLOCK_X_OFFSET = 73;
	private static final int COLOUR_BLOCK_Y_OFFSET = 72;
	
	private String colour;
	
	public Gate(int x, int y, String colour) {
		super(GATE_PATH, x, y);
		this.colour = colour;
		
		updateImageView(addColourBlockToImage(getImage(), COLOUR_BLOCK_WIDTH,
														  COLOUR_BLOCK_HEIGHT,
														  COLOUR_BLOCK_X_OFFSET,
														  COLOUR_BLOCK_Y_OFFSET,
														  colour));
	}

	public String getColour() {
		return colour;
	}
	
}

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//Interface that adds methods to lever and gate items
public interface AddColourToImage {
	
	//addColourBlockToImage add the coloured block to the lever & gate items
	//it takes the base image(Gate.png or Lever.png)
	//the dimensions and location of the block that needs colouring
	//and the colour of the block.
	//Then uses the WriteableImage's inbuilt methods colours the individual pixels of the block
	default Image addColourBlockToImage(Image baseImage, int colourBlockWidth, 
			 int colourBlockHeight, int colourBlockXOffset, int colourBlockYOffset , String colour) {
		
		WritableImage image = new WritableImage(baseImage.getPixelReader(), 
				 (int) baseImage.getWidth(), (int) baseImage.getHeight());
		PixelWriter image_writer = image.getPixelWriter();
		for (int x = 0; x < colourBlockWidth; x++) {
			for (int y = 0; y < colourBlockHeight; y++) {
				image_writer.setColor(colourBlockXOffset + x, colourBlockYOffset + y, getColour(colour));
			}
		}
		return image;
	}
	 
	//Takes a string representing a colour and returns the appropriate colour class
	 default Color getColour(String colour) {
		switch(colour) {
			case "RED" :
				return Color.RED;
			case "GREEN" :
				return Color.GREEN;
			case "BLUE" :
				return Color.BLUE;
			case "YELLOW" :
				return Color.YELLOW;
			case "CYAN" :
				return Color.CYAN;
			case "MAGENTA" :
				return Color.MAGENTA;
			}
		return null;
	}
}
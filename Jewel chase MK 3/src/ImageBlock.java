import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ImageBlock extends ImageView{
	
	protected void updateImageView(String path) {
		setImage(new Image(path));
	}
	protected void updateImageView(Image image) {
		setImage(image);
	}

	public int getxLocation() {
		return (int) getX() / Cube.getCubeSize();
	}

	public int getyLocation() {
		return (int) getY() / Cube.getCubeSize();
	}
	
	public void move(int x, int y) {
		setX(x * Cube.getCubeSize());
		setY(y * Cube.getCubeSize());
	}
	
	
	public ImageBlock(String path, int x, int y) {
		updateImageView(path);
		setFitHeight(Cube.getCubeSize()); 
		setFitWidth(Cube.getCubeSize()); 

		move(x,y);
	}
	
}

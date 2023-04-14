import javafx.application.Platform;

public abstract class RemoveableItem extends Item {
	
	protected void removeItem() {
		LevelControl.removeItem(this);
		PlayerApplication.updateScene();
	}

	protected void removeItem(Item i) {
		Platform.runLater(new Runnable() {
			@Override public void run() {
				LevelControl.removeItem(i);
				PlayerApplication.updateScene();
			}
		});
	}
	
	public RemoveableItem(String path, int x, int y) {
		super(path, x, y);
		// TODO Auto-generated constructor stub
	}

}

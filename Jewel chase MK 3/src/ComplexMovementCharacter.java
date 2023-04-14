public abstract class ComplexMovementCharacter extends Character{

	public ComplexMovementCharacter(int x, int y, int movementSpeed, 
			String direction, String path) {
		super(x, y, movementSpeed, direction, path);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void moveWithCollision(int x, int y) {
		if (checkForCollisionWithItem(x,y, true)) {
			super.moveWithCollision(x,y);
		}
	}
	
	protected boolean checkForCollisionWithItem(int x, int y, boolean collectItem) {
		try {
			for (Item i : LevelControl.getItems()) {
				if (i.getxLocation() == x && i.getyLocation() == y) {
					
					switch (i.getClass().getSimpleName()) {
						case("Explosion"):
							if (collectItem) {
								destroySelf();
							}
						case("Cent"):
						case("Penny"):
						case("Ruby"):
						case("Diamond"):
						case("Clock"):
						case("Lever"):
						case("Door"):
							if (collectItem) {
								((CollectableItem) i).collectItem(getClass().getName());
							}
							return true;
						case("Bomb"):
							((Bomb) i).startCountDown();
						case("Gate"):
							return false;
							
					}
					
				}
			}
		} catch (Exception e){
		}
		return true;
	}
}

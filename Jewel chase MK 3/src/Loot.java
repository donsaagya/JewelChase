public abstract class Loot extends CollectableItem{
	
	protected int value;
	
	public Loot(String path, int x, int y) {
		super(path, x, y);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void collectItem(String character) {
		if(character.equals(LevelControl.getPlayer().getClass().getSimpleName())) {
			Player.addToScore(value);
		}
		super.collectItem(character);
	}

}

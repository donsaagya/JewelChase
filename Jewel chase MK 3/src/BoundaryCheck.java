import java.util.ArrayList;
public interface BoundaryCheck {
	default boolean checkWithinBounds(ArrayList<Integer> cords, Cube[][] grid) {
		int x = cords.get(0);
		int y = cords.get(1);
		
		if (x > -1 && x < grid.length ) {
			if (y > -1 && y < grid[0].length) {
				return true;
			}
		}
		return false;
	}
}
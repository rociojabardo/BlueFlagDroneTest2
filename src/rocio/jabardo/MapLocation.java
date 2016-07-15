package rocio.jabardo;

/**
 * Class that implements the location in the map. The location is made by two coordinates (x,y)
 * It is set at the coordinates (0,0) when it is created.
 * Coordinates can be negative
 * @author rociojabardovelasco
 *
 */
public class MapLocation {

	private int x;
	private int y;
	private boolean turn;

	/**
	 * Class constructor. Coordinates (x,y) are set as (0,0)
	 */
	public MapLocation(boolean turn) {
		super();
		this.x = 0;
		this.y = 0;
		this.turn=turn;
	}

	/**
	 * Method that returns coordinate 'x'
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method that returns coordinate 'y'
	 * @return int
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method that returns whether is the turn of the drone or not
	 * @return true if it is the turn of the drone, false in other case
	 */

	public boolean isTurn() {
		return turn;
	}

	/**
	 * Method that sets the turn of the drone
	 * @param turn true if it is the turn of the drone, false in other case
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	/**
	 * Method that moves the current location to the West
	 */
	public void moveWest() {
		y--;

	}

	/**
	 * Method that moves the current location to the Norht
	 */
	public void moveNorth() {
		x--;

	}

	/**
	 * Method that moves the current location to the South
	 */
	public void moveSouth() {
		x++;

	}

	/**
	 * Method that moves the current location to the East
	 */
	public void moveEast() {
		y++;

	}




}

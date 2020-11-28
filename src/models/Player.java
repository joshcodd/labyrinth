package models;
import java.util.ArrayList;

/**
 *
 */
public class Player {
	Coord currentPosition;
	Coord[] previousPositions;
	int playerNumber;
	ArrayList<ActionTile> actionTiles;
	PlayerProfile profile;


	/**
	 * @param playerNumber
	 * @param playerProfile
	 */
	public Player(int playerNumber, PlayerProfile playerProfile) {
		this.playerNumber = playerNumber;
		this.profile = playerProfile;
		actionTiles = new ArrayList<>();
		this.previousPositions = new Coord[2];
	}

	/**
	 * @return
	 */
	public Coord getCurrentPosition() {
		return currentPosition;
	}
	
	public void setCurrentPosition(Coord xy) {
		this.currentPosition = xy;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * @param destination
	 */
	public void movePlayer (Coord destination) {
		previousPositions[1] = previousPositions[0];
		previousPositions[0] = currentPosition;
		currentPosition = destination;
	}

	public void addActionTile (ActionTile tile) {
		actionTiles.add(tile);
	}

	/**
	 * @param tile
	 */
	public void removeActionTile (ActionTile tile) {
		actionTiles.removeIf(x -> x.getClass() == tile.getClass());
	}

	public ArrayList<ActionTile> getActionTiles () {
		return actionTiles;
	}

	public Coord getPrevPosition (int index) {
		return previousPositions[index];
	}
	
	public void setPrevPosition(int index, Coord xy) {
		previousPositions[index] = xy;
	}
	
	public PlayerProfile getProfile() {
		return profile;
	}

	public void setProfile(PlayerProfile profile) {
		this.profile = profile;
	}

}
	

	
	

	

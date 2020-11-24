package models;
import java.util.ArrayList;

public class Player {
	Coord currentPosition;
	Coord[] previousPositions;
	int playerNumber;
	ArrayList<ActionTile> actionTiles;
	PlayerProfile profile;


	public Player(int playerNumber, PlayerProfile playerProfile) {
		this.playerNumber = playerNumber;
		this.profile = playerProfile;
		actionTiles = new ArrayList<>();
		this.previousPositions = new Coord[2];
	}

	public Coord getCurrentPosition() {
		return currentPosition;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void movePlayer (Coord destination) {
		currentPosition = destination;
	}

	public void addActionTile (ActionTile tile) {
		actionTiles.add(tile);
	}

	public void removeActionTile (int index) {
		actionTiles.remove(index);
	}

	public ArrayList<ActionTile> getActionTiles () {
		return actionTiles;
	}

	public Coord getPrevPosition (int index) {
		return previousPositions[index];
	}
}
	

	
	

	

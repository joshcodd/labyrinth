package game;
import java.util.ArrayList;

public class Player {
	Coord currentPosition;
	Coord[] previousPositions;
	int playerNumber;
	ArrayList<ActionTile> actionTiles;


	public Player(int playerNumber) {
		this.playerNumber = playerNumber;
		actionTiles = new ArrayList<>();
		this.previousPositions = new Coord[2];
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
	

	
	

	

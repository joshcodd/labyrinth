package models;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that initializes player for the current game
 * @author
 * @version 1.0
 */
public class Player {

	private Coord currentPosition;	// current players location on the board
	private Coord[] previousPositions;	//list or players previous moves
	private int playerNumber;	//players number
	private ArrayList<ActionTile> actionTiles;	// list of the players tiles
	private PlayerProfile profile;	//details about the user
	private String colour = "Auto-Assign";	// sets the colour the player will have
	private boolean startFirst = false; // indicates if player start

	/**
	 * initialises the player object, sets profiles details, bag for the users tile and sets players positions
	 * @param playerNumber players given number
	 * @param playerProfile users details
	 */
	public Player(int playerNumber, PlayerProfile playerProfile) {
		this.playerNumber = playerNumber;
		this.profile = playerProfile;
		actionTiles = new ArrayList<>();
		this.previousPositions = new Coord[]{new Coord(-1, -1), new Coord(-1, -1)};
	}

	/**
	 * used to edit the players positions throughout the game
	 * @return the location of player
	 */
	public Coord getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * used to sets the players starting position
	 * @param xy x and y updated co ordinates to be set
	 */
	public void setCurrentPosition(Coord xy) {
		this.currentPosition = xy;
	}

	/**
	 * @return users player's number
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * updates players location on the board throughout the game
	 * @param destination users selected position
	 */
	public void movePlayer (Coord destination) {
		previousPositions[1] = previousPositions[0];
		previousPositions[0] = currentPosition;
		currentPosition = destination;
	}

	/**
	 * appends action tiles to players list once drawn
	 * @param tile action tile that the player drew
	 */
	public void addActionTile (ActionTile tile) {
		actionTiles.add(tile);
	}

	/**
	 * removes action tiles to players list once used
	 * @param tile action tile to be added to players
	 */
	public void removeActionTile (ActionTile tile) {
		actionTiles.removeIf(x -> x.getClass() == tile.getClass());
	}

	/**
	 * used to display players list of action tiles to use in their current turn
	 * @return list of action tiles in hand
	 */
	public ArrayList<ActionTile> getActionTiles () {
		return actionTiles;
	}

	/**
	 * used to locates players previous moves if the backtrack tile is placed on the player
	 * @param index the index of position to be returned
	 * @return players previous position
	 */
	public Coord getPrevPosition (int index) {
		return previousPositions[index];
	}

	/**
	 * used to record players previous moves
	 * @param index to store the move in
	 * @param xy the players position
	 */
	public void setPrevPosition(int index, Coord xy) {
		previousPositions[index] = xy;
	}

	/**
	 * used to pass the user profile
	 * @return the profile of the user for that game
	 */
	public PlayerProfile getProfile() {
		return profile;
	}

	/**
	 * @return the name of the user for that game
	 */
	public String getProfileName(){
		return this.profile.getPlayerName();
	}

	/**
	 * @return the colour of the player in that game
	 */
	public String getColour(){
		return this.colour;
	}

	/**
	 * sets the players colour selected by the user
	 * @param newColour the players colour selected
	 */
	public void setColour(String newColour){
		this.colour = newColour;
	}

	/**
	 * used to determine which player is to start at the beginning
	 * @return if the player is starting or not
	 */
	public boolean isStartingFirst(){
		return this.startFirst;
	}

	/**
	 * sets the player who will play the first move
	 * @param Starting if the player is playing first or not
	 */
	public void setFirst(boolean Starting){
		this.startFirst = Starting;
	}

	/**
	 * used to display details of the player class for developers
	 * @return details of the player
	 */
	@Override
	public String toString() {
		return getProfileName() + " " + getColour();
	}
}
	

	
	

	

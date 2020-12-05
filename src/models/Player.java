package models;
import java.util.ArrayList;

/**
 * Class that represent a player piece.
 * @author Chi Hang Tse
 * @version 1.0
 */
public class Player {
    private Coord currentPosition;
    private Coord[] previousPositions;
    private int playerNumber;
    private ArrayList<ActionTile> actionTiles;
    private PlayerProfile profile;
    private String colour = "Auto-Assign";
    private boolean canBackTrack = true;
    private boolean startFirst = false;

    /**
     * Creates a player.
     * @param playerNumber Player number in game.
     * @param playerProfile The player profile of the player.
     */
    public Player(int playerNumber, PlayerProfile playerProfile) {
        this.playerNumber = playerNumber;
        this.profile = playerProfile;
        actionTiles = new ArrayList<>();
        this.previousPositions = new Coord[]{new Coord(-1, -1),
                new Coord(-1, -1)};
    }

    /**
     * Gets the current position of the player.
     * @return The coordinate of the player on the board.
     */
    public Coord getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the players current position.
     * @param xy The coordinate of their current position.
     */
    public void setCurrentPosition(Coord xy) {
        this.currentPosition = xy;
    }

    /**
     * Gets the players number.
     * @return The users number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Moves the players position to a new position.
     * @param destination The position to move to.
     */
    public void movePlayer(Coord destination) {
        previousPositions[1] = previousPositions[0];
        previousPositions[0] = currentPosition;
        currentPosition = destination;
    }

    /**
     * Adds a action tile to the players available action tiles.
     * @param tile The action tile to add.
     */
    public void addActionTile(ActionTile tile) {
        actionTiles.add(tile);
    }

    /**
     * Removes a specified action tile from the players available
     * action tiles.
     * @param tile The action tile to be removed.
     */
    public void removeActionTile(ActionTile tile) {
        actionTiles
                .removeIf(x -> x.getClass() == tile.getClass());
    }

    /**
     * Gets all of the players available action tiles.
     * @return The players available action tiles.
     */
    public ArrayList<ActionTile> getActionTiles() {
        return actionTiles;
    }

    /**
     * Gets one of the specified previous positions of a player.
     * @param index The index of position to be returned.
     * @return The players previous position.
     */
    public Coord getPrevPosition(int index) {
        return previousPositions[index];
    }

    /**
     * Sets the users previous position.
     * @param index Index of the previous position to set.
     * @param xy The players previous position.
     */
    public void setPrevPosition(int index, Coord xy) {
        previousPositions[index] = xy;
    }

    /**
     * Gets the profile of the player.
     * @return The profile of the user.
     */
    public PlayerProfile getProfile() {
        return profile;
    }

    /**
     * Gets the name of the players player profile.
     * @return The name of the players player profile.
     */
    public String getProfileName() {
        return this.profile.getPlayerName();
    }

    /**
     * Gets the current colour of the player.
     * @return The colour of the player.
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Gets if it is possible for the player to backtrack.
     * @return Whether the player can backtrack or not.
     */
    public boolean canBackTrack() {
        return this.canBackTrack;
    }

    /**
     * Sets if it is possible for the player to backtrack.
     * @param canBackTrack The value to be assigned to canBackTrack.
     */
    public void setCanBackTrack(boolean canBackTrack) {
        this.canBackTrack = canBackTrack;
    }

    /**
     * Sets the colour of the player.
     * @param newColour The colour to be set.
     */
    public void setColour(String newColour) {
        this.colour = newColour;
    }

    /**
     * Gets if the player is starting first or not.
     * @return If the player is starting or not.
     */
    public boolean isStartingFirst() {
        return this.startFirst;
    }

    /**
     * Sets if the player is starting first or not.
     * @param starting If the player is playing first or not.
     */
    public void setFirst(boolean starting) {
        this.startFirst = starting;
    }

}

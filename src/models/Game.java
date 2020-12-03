package models;
import java.io.FileNotFoundException;

/**
 * Initialises gameplay objects. Starts and ends game. Manages game turns.
 * @author Neil Woodhouse
 * @studentID 851182
 */
public class Game {
    private Player[] players;
    private int numPlayers;
    private GameBoard gameBoard;
    private TileBag tileBag = new TileBag();
    private Tile currentTile = null;
    private boolean isOver = false;
    private int currentPlayer = 0;
    private String levelName;

    /**
     * Constructs a new game from a level file.
     * @param gameFilename The file to load the game board from.
     * @param players The players playing this game.
     */
    public Game(String gameFilename, Player[] players) throws FileNotFoundException {
        this.players = players;
        this.numPlayers = players.length;
        this.gameBoard = FileHandler.loadNewGame(gameFilename, players, tileBag);
        this.levelName = gameFilename;
    }

    /**
     * Constructs an existing or already saved game.
     * @param board The game board for this game.
     * @param players The players playing this game.
     * @param fileName The name of the file this game was previously construced from.
     */
    public Game(GameBoard board, Player[] players, String fileName) {
		this.gameBoard = board;
		this.players = players;
		this.levelName = fileName;
	}

	/**
     * Sets the next player to be the current player.
     */
    public void nextPlayer() {
        currentTile = null;
        currentPlayer = (currentPlayer + 1) % numPlayers;
    }

    /**
     * ends game if the player has reached the gaol tile
     * @param player
     * @return
     */
    public boolean checkWin(Player player) {
            Coord playerPos = player.getCurrentPosition();
        return gameBoard.getTileAt(playerPos).getShape() == ShapeOfTile.GOAL_TILE;
    }

    /**
     * Wraps the player around to the other side of the game board if when a tile is inserted,
     * they are pushed off.
     * @param direction The direction from which a tile is being inserted from.
     * @param index The row or column index the tile is being inserted into.
     */
    public void updatePlayerPositions(String direction, int index){
        for (Player player : players) {
            int currX = player.getCurrentPosition().getX();
            int currY = player.getCurrentPosition().getY();
            switch (direction) {
                case "LEFT":
                    if (currX == index && currY == gameBoard.getWidth() - 1) {
                        player.movePlayer(new Coord(currX, 0));
                    } else if (currX == index){
                        player.movePlayer(new Coord(currX, currY + 1));
                    }
                    break;
                case "RIGHT":
                    if (currX == index && currY == 0 ) {
                        player.movePlayer(new Coord(currX, gameBoard.getWidth() - 1));
                    } else if (currX == index){
                        player.movePlayer(new Coord(currX, currY - 1));
                    }
                    break;
                case "DOWN":
                    if (currY == index && currX == gameBoard.getHeight() - 1) {
                        player.movePlayer(new Coord(0, currY));
                    } else if (currY == index){
                        player.movePlayer(new Coord(currX + 1, currY));
                    }
                    break;
                case "UP":
                    if (currY == index && currX == 0) {
                        player.movePlayer(new Coord(gameBoard.getHeight() - 1, currY));
                    } else if (currY == index){
                        player.movePlayer(new Coord(currX - 1, currY));
                    }
                    break;
            }
        }
    }

    public int getCurrentPlayerNum() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public String getCurrentPlayerName(){
        return players[currentPlayer].getProfileName();
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public TileBag getTileBag() {
        return tileBag;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setTileBag(TileBag tileBag) {
        this.tileBag = tileBag;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
    
    public void setCurrentPlayer(int p) {
    	this.currentPlayer = p;
    }

    public String getLevelName() {
        return levelName;
    }
}

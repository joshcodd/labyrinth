package models;
import java.io.FileNotFoundException;

/**
 * Models a game of Labyrinth, handling all aspects
 * relating to gameplay.
 * @author Neil Woodhouse
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
     * Creates a new game from a level file.
     * @param gameFilename The file to load the game board from.
     * @param players The players playing this game.
     */
    public Game(String gameFilename, Player[] players)
            throws FileNotFoundException {
        this.players = players;
        this.numPlayers = players.length;
        this.gameBoard = FileHandler
                .loadNewGame(gameFilename, players, tileBag);
        this.levelName = gameFilename;
    }

    /**
     * Creates an existing or already saved game.
     * @param board The game board for this game.
     * @param players The players playing this game.
     * @param fileName The name of the file this game was
     *                 previously created from.
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
     * Checks to see if a player has reached a goal tile, therefore declaring
     * a win.
     * @param player The player the check.
     * @return If the player has won or not.
     */
    public boolean checkWin(Player player) {
        Coord playerPos = player.getCurrentPosition();
        return gameBoard.getTileAt(playerPos).getShape()
                == ShapeOfTile.GOAL_TILE;
    }

    /**
     * Wraps the player around to the other side of the game board if when
     * a tile is inserted, they are pushed off.
     * @param direction The direction from which a tile is being inserted from.
     * @param index The row or column index the tile is being inserted into.
     */
    public void updatePlayerPositions(String direction, int index) {
        for (Player player : players) {
            // current position of player.
            int currX = player.getCurrentPosition().getX();
            int currY = player.getCurrentPosition().getY();
            switch (direction) {
                case "LEFT":
                    if (currX == index && currY == gameBoard.getWidth() - 1) {
                        player.movePlayer(new Coord(currX, 0));
                    } else if (currX == index) {
                        player.movePlayer(new Coord(currX, currY + 1));
                    }
                    break;
                case "RIGHT":
                    if (currX == index && currY == 0) {
                        player.movePlayer(new Coord(currX,
                                gameBoard.getWidth() - 1));
                    } else if (currX == index) {
                        player.movePlayer(new Coord(currX, currY - 1));
                    }
                    break;
                case "DOWN":
                    if (currY == index && currX == gameBoard.getHeight() - 1) {
                        player.movePlayer(new Coord(0, currY));
                    } else if (currY == index) {
                        player.movePlayer(new Coord(currX + 1, currY));
                    }
                    break;
                case "UP":
                    if (currY == index && currX == 0) {
                        player.movePlayer(new Coord(
                                gameBoard.getHeight() - 1, currY));
                    } else if (currY == index) {
                        player.movePlayer(new Coord(currX - 1, currY));
                    }
                    break;
            }
        }
    }

    /**
     * Gets the number of the current player.
     * @return The current players number.
     */
    public int getCurrentPlayerNum() {
        return currentPlayer;
    }

    /**
     * Gets the current player.
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    /**
     * Gets the name of the current player.
     * @return The current players name.
     */
    public String getCurrentPlayerName() {
        return players[currentPlayer].getProfileName();
    }

    /**
     * Gets all players in the game.
     * @return All players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Gets the number of players in the game.
     * @return The number of players in the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Gets the game board that the game is playing on.
     * @return The game board.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets the tile bag that the game is using.
     * @return The tile bag.
     */
    public TileBag getTileBag() {
        return tileBag;
    }

    /**
     * Gets the games current tile.
     * @return Current tile.
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the games current tile.
     * @param currentTile The current tile to set.
     */
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    /**
     * Gets if the game is currently over.
     * @return If the game is over or not.
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Sets the players that are playing the game.
     * @param players The players to set.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Sets the number of players playing the game.
     * @param numPlayers The number of players.
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Sets the tile bag that the game is using.
     * @param tileBag The tile bag for the game to use.
     */
    public void setTileBag(TileBag tileBag) {
        this.tileBag = tileBag;
    }

    /**
     * Sets if the game is over or not.
     * @param over If the game is over or not.
     */
    public void setOver(boolean over) {
        isOver = over;
    }

    /**
     * Sets the games current player.
     * @param player The player to set.
     */
    public void setCurrentPlayer(int player) {
        this.currentPlayer = player;
    }

    /**
     * Gets the name of the game file that the board is using.
     * @return The name of the game file.
     */
    public String getLevelName() {
        return levelName;
    }
}

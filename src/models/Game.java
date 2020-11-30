package models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    /**
     * initialise the game object if its a loaded game
     * @param gameFilename
     * @param playerNames
     * @throws FileNotFoundException
     */
    public Game(String gameFilename, String[] playerNames) throws FileNotFoundException {
        players = new Player[playerNames.length];
        numPlayers = players.length;
        for (int i = 0; i < playerNames.length; i++) {
            PlayerProfile currentProfile = FileHandler.loadProfile(playerNames[i]);
            players[i] = new Player(i, currentProfile);
        }

        gameBoard = FileHandler.loadNewGame(gameFilename, players, tileBag);
    }

    /**
     * initialise the game object when the current game is re selected
     * @param board the game boards details to run the game on
     * @param playerNames list of players playing
     * @throws FileNotFoundException
     */
    public Game(GameBoard board, String[] playerNames) throws FileNotFoundException {
        players = new Player[playerNames.length];
        numPlayers = players.length;
        for (int i = 0; i < playerNames.length; i++) {
            PlayerProfile currentProfile = FileHandler.loadProfile(playerNames[i]);
            players[i] = new Player(i, currentProfile);
        }
        gameBoard = board;
    }

    public Game(GameBoard board) {
		this.gameBoard = board;
	}

	/**
     *  Allows the next player to play
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
     * changers the players location on the board when they have selected a possible tile to move to
     * @param direction
     * @param index
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

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
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
}

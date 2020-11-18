package game;

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
    private boolean gameOver = false;
    private int currentPlayer = 0;

    public Game(String gameFilename, String[] playerNames) {
        players = new Player[playerNames.length];
        numPlayers = players.length;
        for (int i = 0; i < playerNames.length; i++) {
            try {
                PlayerProfile currentProfile = FileHandler.loadProfile(playerNames[i]);
                players[i] = new Player(i, currentProfile);
            } catch (FileNotFoundException e) {
                System.out.println("Error: Player profile file not found. Please check the filepath of the game save files.");
                //TODO Exit to level select scene
            }
        }

        try {
            gameBoard = FileHandler.loadNewGame(gameFilename, players, tileBag);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The specified game file could not be found. Please check that you're providing a filepath to a valid game file location.");
            //TODO Exit to level select scene
        }
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Starts the gameplay loop. Stops when a player wins the game.
     */
    public void startGame() {

        while (!gameOver) {
            playerTurn(currentPlayer);
            currentPlayer = (currentPlayer + 1) % numPlayers;
        }
    }

    private void playerTurn(int playerNum) {

    }

}

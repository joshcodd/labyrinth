package game;

import java.io.FileNotFoundException;

/**
 * Initialises gameplay objects. Starts and ends game. Manages game turns.
 * @author Neil Woodhouse
 * @studentID 851182
 */
public class Game {
    private Player[] players;
    private GameBoard gameBoard;
    private TileBag tileBag = new TileBag();

    public Game(String gameFilename, String[] playerNames) {
        players = new Player[4];
        for (int i = 0; i < playerNames.length; i++) {
            try {
                PlayerProfile currentProfile = FileHandler.loadProfile(playerNames[i]);
            } catch (FileNotFoundException e) {
                System.out.println("Error: Player profile file not found. Please check the filepath of the game save files.");
                //TODO Exit to level select scene
            }
            players[i] = new Player(i, otherArgs);
        }

        try {
            gameBoard = FileHandler.loadGameFile(gameFilename, players, tileBag);
        } catch(FileNotFoundException e) {
            System.out.println("Error: The specified game file could not be found. Please check that you're providing a filepath to a valid game file location.");
            //TODO Exit to level select scene
        }
    }


}

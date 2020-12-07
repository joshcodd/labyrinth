package models;

import java.io.IOException;

/**
 * Represents a player profile.
 * Stores all information about a person playing the game.
 * @author Luka Zec
 */
public class PlayerProfile {
    private int gamesPlayed;
    private int wins;
    private int losses;
    private String playerName;

    /**
     * Creates a player profile.
     * @param playerName The name of the person playing.
     * @param wins The amount of wins the player has.
     * @param losses The amount of losses the player has.
     * @param gamesPlayed The amount of games the player has played.
     */
    public PlayerProfile(String playerName, int wins, int losses,
                         int gamesPlayed) {
        this.playerName = playerName;
        this.wins = wins;
        this.losses = losses;
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Increments the amount of wins the player has.
     */
    public void incrementWins() {
        this.wins = wins + 1;

    }

    /**
     * Increments the amount of losses the player has.
     */
    public void incrementLosses() {
        this.losses = losses + 1;

    }

    /**
     * Increments the amount of games the player has played.
     */
    public  void incrementGamesPlayed() {
        this.gamesPlayed = gamesPlayed + 1;

    }

    /**
     * Saves the players current statistics to file.
     */
    public void save() throws IOException {
        FileHandler.saveProfile(playerName, wins, losses, gamesPlayed);
    }

    /**
     * Gets the name of the player.
     * @return The name of the player.
     */
    public  String getPlayerName() {
        return playerName;
    }

    /**
     * Gets players number of wins.
     * @return The number of wins.
     */
    public  int getNumberOfWins() {
        return wins;
    }

    /**
     * Gets players number of losses.
     * @return The number of losses.
     */
    public  int getNumberOfLosses() {
        return losses;
    }

    /**
     * Gets players number of games played.
     * @return The number of games played.
     */
    public  int getNumberOfGamesPlayed() {
        return gamesPlayed;
    }

}

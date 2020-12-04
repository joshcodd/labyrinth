package models;

import java.io.IOException;

/**
 * Class that represents payer profile info
 * @author Luka Zec 
 * @StudentID: 987856
 */
public class PlayerProfile {
	private int gamesPlayed;
	private int wins;
	private int losses;
	private String playerName;

	/**
	 * used to initialise the users profile when created as well sets their leaderboard details
	 * @param playerName users name
	 * @param wins number of game wins normally 0
	 * @param losses number of game losses normally 0
	 * @param gamesPlayed number of game played normally 0
	 */
	public PlayerProfile(String playerName, int wins,int losses,int gamesPlayed) {
		setplayerName(playerName);
		this.wins = wins;
		this.losses = losses;
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * sets the users chosen name to the player objects name
	 * @param Name name to be set
	 */
	public void setplayerName(String Name) {
		this.playerName = Name;

	}

	/**
	 * increments users wins
	 */
	public void incrementWins(){
		this.wins = wins + 1;
		
	}

	/**
	 * increments user losses
	 */
	public void incrementLosses(){
		this.losses = losses + 1;
		
	}

	/**
	 * increment how many games user played
	 */
	public  void incrementGamesPlayed() {
		this.gamesPlayed = gamesPlayed + 1;

	}

	/**
	 * enables the possibility to save the player to the file for the leaderboard
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileHandler.saveProfile(playerName, wins, losses, gamesPlayed);
	}

	/**
	 * @return users name to used or displayed
	 */
	public  String getPlayerName() {
		return playerName;
	}

	/**
	 * @return number of user wins to used or displayed
	 */
	public  int getNumberofWins() {
		return wins;
	}

	/**
	 * @return number of user loss to used or displayed
	 */
	public  int getNumberofLosses() {
		return losses;
	}

	/**
	 * @return number of user games to used or displayed
	 */
	public  int getNumberofGamesPlayed() {
		return gamesPlayed;
	}


}

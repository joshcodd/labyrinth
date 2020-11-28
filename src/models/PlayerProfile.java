package models;

import java.io.IOException;

/**
 * Class that represents payer profile info
 * 
 * @author Luka Zec 
 * @StudentID: 987856
 * 
 *
 */
public class PlayerProfile {
	private int gamesPlayed;
	private int wins;
	private int losses;
	private String playerName;

	/**
	 *
	 * @param playerName
	 * @param wins
	 * @param losses
	 * @param gamesPlayed
	 */
	public PlayerProfile(String playerName, int wins,int losses,int gamesPlayed) {
		setplayerName(playerName);
		this.wins = wins;
		this.losses = losses;
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * sets the users choosen name to the player objects name
	 * @param Name name to be set
	 */
	public void setplayerName(String Name) {
		this.playerName = Name;

	}

	public void incrementWins(){
		this.wins = wins + 1;
		
	}
	public void incrementLosses(){
		this.losses = losses + 1;
		
	}

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

	public  String getPlayerName() {
		return playerName;
	}
	
	public  int getNumberofWins() {
		return wins;
	}

	public  int getNumberofLosses() {
		return losses;
	}
	public  int getNumberofGamesPlayed() {
		return gamesPlayed;
	}


}

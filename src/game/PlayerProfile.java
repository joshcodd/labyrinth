package  game;

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
	
	public PlayerProfile(String playerName, int wins,int losses,int gamesPlayed) {
		setplayerName(playerName);
		this.wins = wins;
		this.losses = losses;
		this.gamesPlayed = gamesPlayed;
		
		
		
	}

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

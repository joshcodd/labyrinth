package  game;

/**
 * Class that represents payer profile info
 * 
 * @author Luka Zec studnet num: 987856
 * 
 *
 */
public class PlayerProfile {
	private int gamesPlayed;
	private int wins;
	private int losses;
	private String playerName;
	
	public PlayerProfile(int gamesPlayed, int wins,int losses, String playerName) {
		setplayerName(playerName);
		incrementWins(wins);
		incrementLosses(losses);
		incrementPlayed(gamesPlayed);
		
		
		
	}

	public void setplayerName(String Name) {
		this.playerName = Name;

	}

	public void incrementWins(int win){
		this.wins = wins + win;
		
	}
	public void incrementLosses(int lost){
		this.losses = losses + lost;
		
	}

	public  void incrementGamesPlayed(int numOfGames) {
		this.gamesPlayed = this.gamesPlayed + numOfGames;

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

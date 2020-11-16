
/**
 * Class that represents player profile info
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
		setWins(wins);
		setWins(losses);
		setNumberOfGamesPlayed(gamesPlayed);
		FileReader.saveGameProflei(getPlayerName(),getNumberofWins(), getNumberofLosses(),getNumberofGamesPlayed());
		
		
		
	}

	public void setplayerName(String Name) {
		this.playerName = Name;

	}

	public void setWins(int win){
		this.wins = wins + win;
		
	}
	public void setLosses(int lost){
		this.losses = losses + lost;
		
	}

	public  void setNumberOfGamesPlayed(int numOfGames) {
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

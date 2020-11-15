public class Player extends PlayerProfile {
	
	
	private coord x;
	private coord y;
	private int numOfPlayer;
	private Player [] players;
	
	
	public Player (int numOfPlayer){
		players = new Player [];
		for (int x = 0; x == numOfPlayer; x++) {
			initalizePlayer(n);
		}	
		}
	}
	
	public static void activePlayer() {
		if(player == numOfPlayer ){
			player = 1;
		else 
			player++;
		}
	}
	
	
	public static void initalizePlayer (){
		switch(numOfPlayer) {
			case 1:
			PlayerProfile.setName() = "Player1";
			setPlayerPosX();
			setPlayerPosY();
			break;
			
			case 2:
			PlayerProfile.setName() = "Player2";
			setPlayerPosX();
			setPlayerPosY();
			break;
			
			case 3:
			PlayerProfile.setName() = "Player3";
			setPlayerPosX();
			setPlayerPosY();
			break;
			
			case 4:
			PlayerProfile.setName() = "Player4";
			setPlayerPosX();
			setPlayerPosY();
			break;
			}
	}
	
	public ActionTile getActionTiles(){
		return ActionTile [];
	} 
	
	public coord getPastPositions() {
		return Coord[x,y];
	}
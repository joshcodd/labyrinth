package game;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * FileHandler class for saving and loading levels and profiles
 * @author AndrewCarruthers
 * @StudentID 987747
 */
public class FileHandler {
	
	
	/**
	 * Reads in a level from file, populates TileBag and sets player start locations
	 * @param fileName level file name.
	 * @param players list of players.
	 * @param bag empty tile bag.
	 * @return a constructed game board.
	 */
	public static GameBoard loadNewGame(String fileName,Player[] players,TileBag bag) throws FileNotFoundException {
		File level = new File(fileName.concat(".txt"));
		Scanner line = new Scanner(level);
		GameBoard board = loadNewGame(line, bag);
		playerStartLocations(line,players);
		line.close();
		return board;
	}
	
	private static GameBoard loadNewGame (Scanner line, TileBag bag) { 
		HashMap<Coord,FloorTile> fixedTiles = new HashMap<>(); 
		line.useDelimiter(",");
		int height = line.nextInt();
		int width = line.nextInt();
		int k = line.nextInt(); //number of fixed tiles to create
		for (int i = 0; i != k; i++) {
			
			//set coords for current tile
			int x = line.nextInt();
			int y = line.nextInt();
			Coord location = new Coord(x,y);
			//set shape for current tile
			String tileShape = line.next();
			ShapeOfTile shape = null;
			switch(tileShape) {
			case "BEND":
				shape = ShapeOfTile.BEND;
				break;
			case "T":
				shape = ShapeOfTile.T_SHAPE;
				break;
			case "STRAIGHT":
				shape = ShapeOfTile.STRAIGHT;
				break;
			case "CROSSROADS":
				shape = ShapeOfTile.CROSSROADS;
				break;
			case "GOAL":
				shape = ShapeOfTile.GOAL_TILE;
				break;
			}
			
			int orientation = line.nextInt();
			
			//adds to hashmap
			FloorTile fixedTile = new FloorTile(orientation,true,shape); 
			fixedTiles.put(location,fixedTile);
		}
		
		//next section populates TileBag
		ShapeOfTile newShape = null;
		int bendTile = line.nextInt(); 
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.BEND;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int tTile = line.nextInt(); 
		for (int i = 0; i != tTile; i++) {
			newShape = ShapeOfTile.T_SHAPE;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int straightTile = line.nextInt(); 
		for (int i = 0; i != straightTile; i++) {
			newShape = ShapeOfTile.STRAIGHT;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int crossTile = line.nextInt(); 
		for (int i = 0; i != crossTile; i++) {
			newShape = ShapeOfTile.CROSSROADS;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int iceTile = line.nextInt();
		for (int i = 0; i != iceTile; i++) {
			Tile newTile = new IceTile(); 
			bag.addTile(newTile);
		}
		int fireTile = line.nextInt();
		for (int i = 0; i != fireTile; i++) {
			Tile newTile = new FireTile();
			bag.addTile(newTile);
		}
		int backTile = line.nextInt();
		for (int i = 0; i != backTile; i++) {
			Tile newTile = new BackTrackTile();
			bag.addTile(newTile);
		}
		int doubleTile = line.nextInt();
		for (int i = 0; i != doubleTile; i++) {
			Tile newTile = new DoubleMoveTile();
			bag.addTile(newTile); 
		}
		
		//construct Game board for the level
		GameBoard board = new GameBoard(height,width,fixedTiles,bag);
		return board;
	}
	
	private static void playerStartLocations(Scanner line,Player[] players) {
		for (Player player: players) {
			int x = line.nextInt();
			int y = line.nextInt();
			Coord xy = new Coord(x,y);
			player.movePlayer(xy);
		}	
	}
	
	public static GameBoard loadOldGame(String fileName, TileBag bag) {
		return null;
		//TODO load current game method
	}

	public static void saveGameFile (String saveName) {
		//TODO save game method player + positions + their action tiles, gameboard hashmap
	}	
	
	/**
	 * Gets a list of all player names from existing profiles.
	 * @return Arraylist of player names.
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> getAllNames() throws FileNotFoundException{
		File file = new File("PlayerProfiles.txt");
		Scanner line = new Scanner(file);
		line.useDelimiter(",");
		ArrayList<String> players = new ArrayList<String>();
		String name = "";
		while (line.hasNext()) {
			name = line.next();
			players.add(name);
			line.nextLine();
		}
		line.close();
		return players;
	}
	
	/**
	 * Loads in a player profile from the profiles file.
	 * @param playerName player's name.
	 * @return a player's profile.
	 */
	public static PlayerProfile loadProfile (String playerName) throws FileNotFoundException {
		File playerFile = new File("PlayerProfiles.txt");
		Scanner line = new Scanner(playerFile);
		PlayerProfile p = loadProfile(line,playerName);
		line.close();
		return p;
	}
	
	private static PlayerProfile loadProfile (Scanner line,String playerName) {
		line.useDelimiter(",");
		String name = null;
		int wins = 0;
		int losses = 0;
		int gamesPlayed = 0;
		while (line.hasNext()) {
			if (line.hasNext(playerName)) {
				name = line.next();
				wins = line.nextInt();
				losses = line.nextInt();
			}
			line.nextLine();
		}
		PlayerProfile p = new PlayerProfile(name,wins,losses,gamesPlayed);
		if (p.getPlayerName() == null) {
			System.out.println("Player Does not exist");
		}
		return p;
	}
	
	/**
	 * Saves a player's profile to file.
	 * @param playerName player's name.
	 * @param wins player's wins.
	 * @param losses player's losses.
	 * @param gamesPlayed player's total games played.
	 * @throws IOException 
	 */
	public static void saveProfile (String playerName,int wins ,int losses,int gamesPlayed) throws IOException {
		File file = new File("PlayerProfiles.txt");
		BufferedReader read = new BufferedReader(new FileReader(file));
		String newFile = "";
		String line = read.readLine();
		while (line != null) {
			if (line.contains(playerName)) {
				line = playerName + "," + wins + "," + losses + "," + gamesPlayed;
			}
			newFile = newFile + line + "\n";
		}
		read.close();
		
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}
}
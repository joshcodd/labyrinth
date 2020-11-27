package models;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * FileHandler class for saving and loading gamefiles.levels and profiles
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
		File level = new File("src/gamefiles/levels/"+fileName.concat(".txt"));
		Scanner line = new Scanner(level);
		GameBoard board = loadNewGame(line, bag);
		playerStartLocations(line,players);
		line.close();
		return board;
	}
	private static GameBoard loadNewGame (Scanner line, TileBag bag) {
		HashMap<Coord,FloorTile> fixedTiles = new HashMap<>(); 
		Scanner scan = new Scanner(line.next());
		scan.useDelimiter(",");
		int height = scan.nextInt();
		int width = scan.nextInt();
		scan.close();
		line.nextLine();
		int k = line.nextInt(); //number of fixed tiles to create
		for (int i = 0; i != k; i++) {
			scan = new Scanner(line.next());
			scan.useDelimiter(",");
			int x = scan.nextInt();
			int y = scan.nextInt();
			Coord location = new Coord(x,y); 
			String tileShape = scan.next();
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
			int orientation = scan.nextInt();
			scan.close();
			line.nextLine();
			FloorTile fixedTile = new FloorTile(orientation,true,shape); 
			fixedTiles.put(location,fixedTile);
		}
		//next section populates TileBag
		ShapeOfTile newShape = null;

		int bendTile = line.nextInt();
		System.out.println(bendTile);
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.BEND;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int tTile = line.nextInt(); 
		for (int i = 0; i != tTile; i++) {
			newShape = ShapeOfTile.T_SHAPE;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int straightTile = line.nextInt(); 
		for (int i = 0; i != straightTile; i++) {
			newShape = ShapeOfTile.STRAIGHT;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int crossTile = line.nextInt(); 
		for (int i = 0; i != crossTile; i++) {
			newShape = ShapeOfTile.CROSSROADS;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int iceTile = line.nextInt();
		for (int i = 0; i != iceTile; i++) {
			Tile newTile = new IceTile(); 
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int fireTile = line.nextInt();
		for (int i = 0; i != fireTile; i++) {
			Tile newTile = new FireTile();
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int backTile = line.nextInt();
		for (int i = 0; i != backTile; i++) { 
			Tile newTile = new BackTrackTile();
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int doubleTile = line.nextInt();
		for (int i = 0; i != doubleTile; i++) {
			Tile newTile = new DoubleMoveTile();
			bag.addTile(newTile); 
		}
		
		line.nextLine();
		int goalTile = line.nextInt();
		for (int i = 0; i != goalTile; i++) {
			newShape = ShapeOfTile.GOAL_TILE;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile); 
		}
		//construct Game board for the level
		GameBoard board = new GameBoard(height,width,fixedTiles,bag);
		return board;
	}
	private static void playerStartLocations(Scanner line,Player[] players) {
		Scanner read;
		for (Player player: players) {
			read = new Scanner(line.next());
			read.useDelimiter(",");
			int x = read.nextInt();
			int y = read.nextInt();
			read.close();
			line.nextLine();
			Coord xy = new Coord(x,y);
			player.movePlayer(xy);
		}	
	}
	
	public static GameBoard loadOldGame(String fileName, TileBag bag) {
		return null;
		
	}
	
	public static void saveGameFile (String saveName,Game game) {
	//	File file = new File("src/gamefiles/saves/" + saveName + ".txt");
		String newFile = "";
		String line = "";
		GameBoard g = game.getGameBoard();
		//line 1 : h,w
		int height = g.getHeight();
		int width = g.getWidth();
		newFile = height + "," + width + "\n";
		//next, all tiles on the board
		for (int i = 0; i < height; i++) {
			for (int j = 0 ; j < width ; j++) {
				Tile t = g.getTileAt(new Coord(i,j));
				if (t instanceof FloorTile) {
					ShapeOfTile shape = ((FloorTile)t).getShape();
					int o = ((FloorTile)t).getOrientation();
					boolean isFixed = ((FloorTile)t).isFixed();
					line = i + "," + j + "," + shape + "," + o + "," + isFixed;
				}
				if (t instanceof ActionTile) {
					//>>??
				}
				newFile = newFile + line + "\n";
			}
		}
		//next, all tiles in tile bag
		newFile = newFile + ">TileBag\n";
		TileBag bag = game.getTileBag();
		int bend = 0;
		int tShape = 0;
		int straight = 0;
		int cross = 0;
		int ice = 0;
		int fire = 0;
		int back = 0;
		int doubleMove = 0;
		int goal = 0;
		while (bag.drawTile() != null) {
			Tile t = bag.drawTile();
			if (t instanceof FloorTile) {
				ShapeOfTile shape = ((FloorTile)t).getShape();
				switch(shape) {
				case BEND:
					bend++;
					break;
				case T_SHAPE:
					tShape++;
					break;
				case STRAIGHT:
					straight++;
					break;
				case CROSSROADS:
					cross++;
					break;
				case GOAL_TILE:
					goal++;
					break;
				}
			}
			if (t instanceof IceTile) {
				ice++;
			}
			if (t instanceof FireTile) {
				fire++;
			}
			if (t instanceof BackTrackTile) {
				back++;
			}
			if (t instanceof DoubleMoveTile) {
				doubleMove++;
			}
		}
		newFile = newFile + bend + "\n" + tShape + "\n" + straight + "\n" + cross + "\n" + ice + "\n" + fire + "\n" + back + "\n" + doubleMove + "\n" + goal + "\n";
		//save current tile + ort
		//save game.isover
		//save current player(s)
		//save to leaderboard?
	}	

	/**
	 * Reads in leaderboard information about players for a specified level
	 * @param levelName name of level leaderboard to be loaded.
	 * @return An arraylist of player names
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> loadLeaderboard(String levelName) throws FileNotFoundException {
		File leaderboard = new File("src/gamefiles/leaderboard.txt");
		Scanner line = new Scanner(leaderboard);
		ArrayList<String> playerlist = loadLeaderboard(line,levelName);
		line.close();
		return playerlist;
	}
	private static ArrayList<String> loadLeaderboard(Scanner line, String levelName) {
		ArrayList<String> players = new ArrayList<String>();
		String name = "";
		while (line.hasNext()) {
			Scanner check = new Scanner(line.next());
			check.useDelimiter(":");
			if(check.hasNext(levelName)) {
				check.next();
				Scanner names = new Scanner(check.next());
				names.useDelimiter(",");
				while(names.hasNext()) {
					name = names.next();
					players.add(name);
				}
				names.close();
			}
			check.close();
			if (line.hasNextLine()){
				line.nextLine();
			}

		}
		return players;
	}
	
	/**
	 * Saves a new player name to the specified level's leaderboard.
	 * @param levelName current level
	 * @param playerName player name
	 * @throws IOException
	 */
	public static void saveLeaderboard(String levelName, String playerName) throws IOException {
		File file = new File("src/gamefiles/leaderboard.txt");
		Scanner read = new Scanner(new FileReader(file));
		String newFile = "";
		String line = "";
		Boolean found = false;
		while (read.hasNextLine()) {
			line = read.nextLine();
			if (line.contains(levelName)) {
				found = true;
				if(!line.contains(playerName)) {
					line.concat(","+playerName);
				}
			}
			newFile = newFile + line + "\n";
		}
		if (!found){
			line = levelName + ":" + playerName;
			newFile = newFile + line +"\n";
		}
		read.close();
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}

	/**
	 * Gets a list of all player names from existing profiles.
	 * @return Arraylist of player names.
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> getAllNames() throws FileNotFoundException{
		File file = new File("src/gamefiles/players.txt");
		Scanner line = new Scanner(file);
		line.useDelimiter(",");
		ArrayList<String> players = new ArrayList<String>();
		String name = "";
		while (line.hasNext()) {
			name = line.next();
			players.add(name);

			if (line.hasNext()){
				line.nextLine();
			}
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
		File playerFile = new File("src/gamefiles/players.txt");
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
		File file = new File("src/gamefiles/players.txt");
		Scanner read = new Scanner(new FileReader(file));
		String newFile = "";
		String line = "";
		Boolean found = false;
		while (read.hasNextLine()) {
			line = read.nextLine();
			System.out.println(line);
			if (line.contains(playerName)) {
				line = playerName.toLowerCase() + "," + wins + "," + losses + "," + gamesPlayed;
				found = true;
			}
			newFile = newFile + line + "\n";
		}
		if (!found){
			newFile = newFile + playerName + "," + wins + "," + losses + "," + gamesPlayed + "\n";
		}
		read.close();
		
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}


	public static void deleteProfile (String playerName) throws IOException {
		File file = new File("src/gamefiles/players.txt");
		Scanner read = new Scanner(new FileReader(file));
		String newFile = "";
		String line = "";
		while (read.hasNextLine()) {
			line = read.nextLine();
			System.out.println(line);
			if (!line.contains(playerName)) {
				newFile = newFile + line + "\n";
			}
		}
		read.close();
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}
}
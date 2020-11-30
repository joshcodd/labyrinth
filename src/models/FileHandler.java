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
 * FileHandler class for saving and loading gamefiles.levels and profiles.
 * @author AndrewCarruthers
 * @StudentID 987747
 */
public class FileHandler {

	/**
	 * Reads in a level from file, populates TileBag and sets player start locations.
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
	/**
	 * Private method handling information assignment from file.
	 * @param line Scanner holding file to be read.
	 * @param bag tilebag to populate
	 * @return gameboard of the levelfile.
	 */
	private static GameBoard loadNewGame (Scanner line, TileBag bag) {
		HashMap<Coord,FloorTile> fixedTiles = new HashMap<>(); 
		Scanner scan = new Scanner(line.next()); 
		scan.useDelimiter(",");
		int height = scan.nextInt(); // scan in height + width
		int width = scan.nextInt();
		scan.close();
		line.nextLine();
		int k = line.nextInt(); //number of fixed tiles to create
		for (int i = 0; i != k; i++) { // loops for the number of times to number of fixed tiles
			scan = new Scanner(line.next()); //set scanner & delimiter
			scan.useDelimiter(",");
			int x = scan.nextInt();
			int y = scan.nextInt();
			Coord location = new Coord(x,y); //create coord object
			String tileShape = scan.next(); //reading in tile shape and setting as enumeration
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
			FloorTile fixedTile = new FloorTile(orientation,true,shape); //construct tile for this loop
			fixedTiles.put(location,fixedTile); //put tile in hashmap at coord
		}
		//next section populates TileBag
		ShapeOfTile newShape = null;

		int bendTile = line.nextInt(); //number of bend tiles to put in bag
		System.out.println(bendTile);
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.BEND;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		
		line.nextLine();
		int tTile = line.nextInt();  //num T tiles to put in bag
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
		
		line.nextLine();//option to add goal tiles to the tilebag, players could get lucky & win instantly by drawing a goal tile
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
	/**
	 * Private method setting player's start locations.
	 * @param line Scanner holding level file.
	 * @param players list of players playing the game.
	 */
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
			player.setCurrentPosition(xy);
		}	
	}
	
	/**
	 * Reads in a game from file, loads the game and all attributes
	 * @param fileName file name of game to be loaded
	 * @return A constructed game with all attributes preserved.
	 * @throws FileNotFoundException
	 */
	public static Game continueGame(String fileName) throws FileNotFoundException {
		File level = new File("src/gamefiles/saves/"+fileName.concat(".txt"));
		Scanner read = new Scanner(level);
		Game game = continueGame(read);
		read.close();
		return game;
	}
	/**
	 * Private method handling information assignment from a save file.
	 * @param read Scanner containing the save file.
	 * @return A constructed Game with all elements assigned.
	 * @throws FileNotFoundException
	 */
	private static Game continueGame(Scanner read) throws FileNotFoundException {
		HashMap<Coord,FloorTile> boardMap = new HashMap<>();
		HashMap<Coord,ActionTile> actionMap = new HashMap<>();//Initialising variables, lists, hashmaps & enumerations
		Scanner line;
		int height = 0;
		int width = 0;
		TileBag bag = new TileBag();
		Tile currentTile = null;
		Boolean isOver = false;
		int currentPlayer = 0;
		int numPlayers = 0;
		Player[] players = null;
		ShapeOfTile shape = null;
		//parse entire file until empty, checking for certain ">text" checks to begin input retrieval phases.
		while(read.hasNext()){ 
			if(read.hasNext(">Height+Width")) { //read in height + width settings
				read.nextLine();
				line = new Scanner(read.nextLine());
				line.useDelimiter(",");
				height = line.nextInt();
				width = line.nextInt();
			}
			if(read.hasNext(">Board")) { // read in board and save to hashmaps of boardMap<coord><FloorTile> and actionMap<coord><ActionTile> 
				read.nextLine();
				for (int i = 0; i < height; i++) {// loops til height of board reached
					for (int j = 0; j < width; j++) {// loops til width of board reached
						line = new Scanner(read.nextLine());
						line.useDelimiter(",");
						int x = line.nextInt();
						int y = line.nextInt();
						Coord xy = new Coord(x,y);
						if (line.hasNextInt()) {//action tiles are saved as (x,y,int turnsSinceUse,typeOfActionTile) 
							int turns = line.nextInt();
							String type = line.next();
							ActionTile t = null; // deciding which action tile to construct
							if(type == "fire") {
								t = new FireTile();	
							}
							if(type == "ice") {
								t = new IceTile();	
							}
							if(type == "back") {
								t = new BackTrackTile();	
							}
							if(type == "dMove") {
								t = new DoubleMoveTile();	
							}
							t.setTurnsSinceUse(turns); //set tile expiry date
							actionMap.put(xy,t); //put tile in action map
						} else { //floor tiles are saved as (x,y,boolean,orientation,shape)
							String fixed = line.next();
							int o = line.nextInt();
							String tileShape = line.next();
							switch(tileShape) { //setting tile shape as enumeration
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
							line.close();
							FloorTile t = new FloorTile(o,Boolean.getBoolean(fixed),shape); //construct Floortile
							boardMap.put(xy,t); //put tile in map
						}
					}
				}
			}
			if(read.hasNext(">TileBag")) { //read in tilebag tiles
				read.nextLine();
				int bend = read.nextInt(); //number of bend tiles to put in bag
				for (int i = 0; i != bend; i++) {
					shape = ShapeOfTile.BEND;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int tShape = read.nextInt(); // num T tiles to go in bag
				for (int i = 0; i != tShape; i++) {
					shape = ShapeOfTile.T_SHAPE;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int straight = read.nextInt(); //straight tiles 
				for (int i = 0; i != straight; i++) {
					shape = ShapeOfTile.STRAIGHT;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int cross = read.nextInt(); //crossroads
				for (int i = 0; i != cross; i++) {
					shape = ShapeOfTile.CROSSROADS;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine(); //ice tiles
				int ice = read.nextInt();
				for (int i = 0; i != ice; i++) {
					Tile t = new IceTile();
					bag.addTile(t);
				}
				read.nextLine();
				int fire = read.nextInt(); //fire tiles
				for (int i = 0; i != fire; i++) {
					Tile t = new FireTile();
					bag.addTile(t);
				}
				read.nextLine();
				int back = read.nextInt(); //backTrack tiles
				for (int i = 0; i != back; i++) {
					Tile t = new BackTrackTile();
					bag.addTile(t);
				}
				read.nextLine();
				int doubleMove = read.nextInt(); //double move tiles
				for (int i = 0; i != doubleMove; i++) {
					Tile t = new DoubleMoveTile();
					bag.addTile(t);
				}
				read.nextLine();
				int goal = read.nextInt(); //goal tiles
				for (int i = 0; i != goal; i++) {
					shape = ShapeOfTile.GOAL_TILE;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
			}
			if(read.hasNext(">CurrentTile")) { //read in current tile information
				read.nextLine();
				line = new Scanner(read.nextLine());
				line.useDelimiter(",");
				int o = line.nextInt();
				String fixed = line.next();
				String tileShape = line.next();
				line.close();
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
				currentTile = new FloorTile(o,Boolean.getBoolean(fixed),shape);
			}
			if(read.hasNext(">IsOver")) { //reads in isOver (bool)
				read.nextLine(); //>>>is this needed?
				String over = read.next();
				isOver = Boolean.getBoolean(over);
			}
			if(read.hasNext(">CurrentPlayer")) { // reads in current player number
				read.nextLine();
				currentPlayer = read.nextInt();
			}
			if(read.hasNext(">Players")) { // reads in players information
				read.nextLine();
				numPlayers = read.nextInt(); //number of players in this game
				players = new Player[numPlayers]; // set list size to number of players
				for (int i = 0; i < numPlayers; i++) { // loops for the number of players in this game
					line = new Scanner(read.nextLine());
					line.useDelimiter(",");
					int playerNum = line.nextInt(); //player number
					int x = line.nextInt();
					int y = line.nextInt();
					int x0 = line.nextInt();
					int y0 = line.nextInt();
					int x1 = line.nextInt();
					int y1 = line.nextInt();
					Coord xy = new Coord(x,y); //current coordinate
					Coord xy0 = new Coord(x0,y0); //last position
					Coord xy1 = new Coord(x1,y1);//last-last position
					String name = line.next();//player name
					Player p = new Player(playerNum,loadProfile(name));//construct player, loading profile from file matching player's name
					players[playerNum] = p; // insert player in playerlist at index == their player number
					p.setCurrentPosition(xy); //set player's current position
					p.setPrevPosition(0,xy0); //set last position
					p.setPrevPosition(1,xy1); //set last position -1
					int fire = line.nextInt(); // number of fire tiles this player has
					for (int j = 0; j < fire; j++) {
						ActionTile t = new FireTile();
						p.addActionTile(t);
					}
					int ice = line.nextInt();// number of ice tiles this player has
					for (int j = 0; j < ice; j++) {
						ActionTile t = new IceTile();
						p.addActionTile(t);
					}
					int back = line.nextInt();// number of Backtrack tiles this player has
					for (int j = 0; j < back; j++) {
						ActionTile t = new BackTrackTile();
						p.addActionTile(t);
					}
					int dMove = line.nextInt();// number of double move tiles this player has
					for (int j = 0; j < dMove; j++) {
						ActionTile t = new DoubleMoveTile();
						p.addActionTile(t);
					}
					line.close();
				}
			}
			read.nextLine(); //advance while loop to next line
		}
		GameBoard board = new GameBoard(height,width,boardMap,actionMap); //construct game board
		Game game = new Game(board); //create new game
		game.setCurrentTile(currentTile); //assign details about game
		game.setTileBag(bag);
		game.setOver(isOver);
		game.setCurrentPlayer(currentPlayer);
		game.setPlayers(players);
		game.setNumPlayers(numPlayers);
		return game;
	}
	
	/**
	 * Saves a game to file
	 * @param saveName save name for current game
	 * @param game current game
	 * @throws IOException
	 */
	public static void saveGameFile (String saveName,Game game) throws IOException {
		File file = new File("src/gamefiles/saves/" + saveName + ".txt");
		String newFile = "";
		String line = "";
		GameBoard g = game.getGameBoard();
		
		//line 1 : h,w
		newFile = ">Height+Width\n";
		int height = g.getHeight();
		int width = g.getWidth();
		newFile = newFile + height + "," + width + "\n";
		
		//next, all tiles on the board: FloorTile(x,y,orientation ,isFixed,shape) ActionTile(x,y,int turnsSinceUse, typeOfActiontile)
		newFile = newFile + ">Board\n";
		for (int i = 0; i < height; i++) { // loops til height reached
			for (int j = 0 ; j < width ; j++) { // loops til width reached
				Tile t = g.getTileAt(new Coord(i,j)); //get tile at current coord
				if (t instanceof FloorTile) { //save tile as floortile
					ShapeOfTile shape = ((FloorTile)t).getShape();
					int o = ((FloorTile)t).getOrientation();
					boolean isFixed = ((FloorTile)t).isFixed();
					line = i + "," + j + "," + isFixed + "," + o + "," + shape;
				}
				if (t instanceof ActionTile) { //save tile as action tile
					int turns = ((ActionTile) t).getTurnsSinceUse();
					String type = ""; // checks type of Actiontile to be saved
					if(t instanceof FireTile) {
						type = "fire";
					}
					if(t instanceof IceTile) {
						type = "ice";
					}
					if(t instanceof BackTrackTile) {
						type = "back";
					}
					if(t instanceof DoubleMoveTile) {
						type = "dmove";
					}
					line = i + "," + j + "," + turns + "," + type;
				}
				newFile = newFile + line + "\n"; 
			}
		}
		//next, all tiles in tile bag (new line for each type of tile)
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
		while (bag.drawTile() != null) { //depopulates back one at a time and counts how many of each tile exist.
			Tile t = bag.drawTile();
			if (t instanceof FloorTile) { //check tile's shape
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
		
		//save current tile (orientation, isFixed, shape)...should this be a coord?
		newFile = newFile + ">CurrentTile\n";
		FloorTile currentTile = (FloorTile) game.getCurrentTile();
		ShapeOfTile shape = currentTile.getShape();
		int o = currentTile.getOrientation();
		Boolean isFixed = currentTile.isFixed();
		newFile = newFile + o + "," + isFixed + "," + shape + "\n";
		
		//save game.isover(bool)....do we need this?
		newFile = newFile + ">IsOver\n";
		Boolean over = game.isOver();
		newFile = newFile + over + "\n";
		
		//save current player(int player#)
		newFile = newFile + ">CurrentPlayer\n";
		int p = game.getCurrentPlayerNum();
		newFile = newFile + p + "\n";
		
		//save players (name, playerNum, actionTiles, current position, previous positions)
		newFile = newFile + ">Players\n";
		int numPlayers = game.getNumPlayers();
		newFile = newFile + numPlayers + "\n";
		Player[] players = game.getPlayers();
		for (Player player:players) { // loops for each player in player[] 
			int playerNum = player.getPlayerNumber();
			int x = player.getCurrentPosition().getX();
			int y = player.getCurrentPosition().getY();
			int x0 = player.getPrevPosition(0).getX();//get prev position
			int y0 = player.getPrevPosition(0).getY();
			int x1 = player.getPrevPosition(1).getX();//get prev position -1
			int y1 = player.getPrevPosition(1).getY();
			String name = player.getProfile().getPlayerName();
			line = playerNum + "," + x + "," + y + "," + x0 + "," + y0 + "," + x1 + "," + y1 + "," + name;
			ArrayList<ActionTile> tiles = player.getActionTiles(); //get player's action tiles
			int fireTile = 0;
			int iceTile = 0;
			int backTile = 0;
			int doubleTile = 0;
			while(tiles != null) { //while they still have tiles
				ActionTile tile = tiles.remove(0);
				if (tile instanceof FireTile) {
					fireTile++;
				}
				if (tile instanceof IceTile) {
					iceTile++;
				}
				if (tile instanceof BackTrackTile) {
					backTile++;
				}
				if (tile instanceof DoubleMoveTile) {
					doubleTile++;
				}
			}
			line = line + "," + fireTile + "," + iceTile +  "," + backTile + "," + doubleTile;
			newFile = newFile + line + "\n";
		}
		
		//write file
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
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
	/**
	 * Private method to read in leaderboard from file
	 * @param line scanner containing leaderboard.txt file
	 * @param levelName name of level to load
	 * @return a list of names that have played on this level
	 */
	private static ArrayList<String> loadLeaderboard(Scanner line, String levelName) {
		ArrayList<String> players = new ArrayList<String>();
		String name = "";
		boolean found = false;
		while (line.hasNext()) {
			Scanner check = new Scanner(line.next());
			check.useDelimiter(":");
			if(check.hasNext(levelName)) { //if current line is the level we're looking for 
				check.next();
				found = true;
				Scanner names = new Scanner(check.next()); //set up scanner2
				names.useDelimiter(",");
				while(names.hasNext()) {
					name = names.next();
					players.add(name); //add name to list of names
				}
				names.close();
			}
			check.close();
			if (!found){ //advance scanner
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
		File file = new File("src/gamefiles/leaderboard.txt"); //load leaderboard file
		Scanner read = new Scanner(new FileReader(file));
		String newFile = "";//initialize new text 
		String line = "";
		Boolean found = false;
		while (read.hasNextLine()) { //loop while there is more file
			line = read.nextLine();
			if (line.contains(levelName)) { // if the line is the level we're looking for
				found = true;
				if(!line.contains(playerName)) {
					line.concat(","+playerName);
				}
			}
			newFile = newFile + line + "\n";
		}
		if (!found){ // if the level could not be found, create
			line = levelName + ":" + playerName;
			newFile = newFile + line +"\n";
		}
		read.close();
		//write file
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
	/**
	 * Private method to load in a profile from file
	 * @param line Scanner containing players.txt
	 * @param playerName player profile to be loaded
	 * @return specified player profile
	 */
	private static PlayerProfile loadProfile (Scanner line,String playerName) {
		line.useDelimiter(",");
		String name = null;
		int wins = 0;
		int losses = 0;
		int gamesPlayed = 0;
		while (line.hasNext()) { // while there is more file
			if (line.hasNext(playerName)) { //check if line has player's name
				name = line.next();
				wins = line.nextInt();
				losses = line.nextInt();
			}
			line.nextLine();
		}
		PlayerProfile p = new PlayerProfile(name,wins,losses,gamesPlayed);//make player to return
		if (p.getPlayerName() == null) { //player not found
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
		File file = new File("src/gamefiles/players.txt"); //load file
		Scanner read = new Scanner(new FileReader(file));
		String newFile = ""; // new text 
		String line = "";
		Boolean found = false;
		playerName = playerName.toLowerCase();
		while (read.hasNextLine()) { // loop whole file
			line = read.nextLine();
			if (line.contains(playerName)) { //update existing player details
				line = playerName + "," + wins + "," + losses + "," + gamesPlayed;
				found = true;
			}
			newFile = newFile + line + "\n";
		}
		if (!found){ //player not found, add to bottom of file
			newFile = newFile + playerName + "," + wins + "," + losses + "," + gamesPlayed + "\n";
		}
		read.close();
		//write file
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}

	/**
	 * Deletes a player's profile from file.
	 * @param playerName name of user to be deleted
	 * @throws IOException
	 */
	public static void deleteProfile (String playerName) throws IOException {
		File file = new File("src/gamefiles/players.txt"); //
		Scanner read = new Scanner(new FileReader(file));
		String newFile = ""; // new text
		String line = "";
		while (read.hasNextLine()) { //loop whole file
			line = read.nextLine();
			if (!line.contains(playerName)) { //all lines except specified player
				newFile = newFile + line + "\n";
			}
		}
		read.close();
		//write file
		FileWriter write = new FileWriter(file);
		write.write(newFile);
		write.close();
	}
}
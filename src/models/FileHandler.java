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
	private static Game continueGame(Scanner read) throws FileNotFoundException {
		HashMap<Coord,FloorTile> boardMap = new HashMap<>();
		HashMap<Coord,ActionTile> actionMap = new HashMap<>();
		Scanner line;
		int height = 0;
		int width = 0;
		TileBag bag = new TileBag();
		Tile currentTile = null;
		int currentPlayer = 0;
		int numPlayers = 0;
		Player[] players = null;
		ShapeOfTile shape = null;

		while(read.hasNext()){
			if(read.next().equals(">Height+Width")) {
				String lines = read.next();
				line = new Scanner(lines);
				line.useDelimiter(",");
				height = line.nextInt();
				width = line.nextInt();
			}
			if(read.hasNext(">Board")) {
				read.next();
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						String lines = read.next();
						line = new Scanner(lines);
						line.useDelimiter(",");
						int x = line.nextInt();
						int y = line.nextInt();
						Coord xy = new Coord(x,y);
						boolean fixed = line.next().equals("true");
						int o = line.nextInt();
						String tileShape = line.next();
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
						line.close();
						FloorTile t = new FloorTile(o,fixed,shape);
						boardMap.put(xy,t);
					}
				}
			}
			if(read.hasNext(">ActionBoard")) {
				read.next();
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						line = new Scanner(read.next());
						line.useDelimiter(",");
						int x = line.nextInt();
						int y = line.nextInt();
						Coord xy = new Coord(x,y);
						int turns = line.nextInt();
						String type = line.next();
						ActionTile t = null;
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
						t.setTurnsSinceUse(turns);
						actionMap.put(xy,t);
						line.close();
					}
				}
			}
			if(read.hasNext(">TileBag")) {
				read.next();
				int bend = read.nextInt();

				for (int i = 0; i != bend; i++) {
					shape = ShapeOfTile.BEND;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int tShape = read.nextInt();
				for (int i = 0; i != tShape; i++) {
					shape = ShapeOfTile.T_SHAPE;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int straight = read.nextInt();
				for (int i = 0; i != straight; i++) {
					shape = ShapeOfTile.STRAIGHT;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int cross = read.nextInt();
				for (int i = 0; i != cross; i++) {
					shape = ShapeOfTile.CROSSROADS;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
				read.nextLine();
				int ice = read.nextInt();
				for (int i = 0; i != ice; i++) {
					Tile t = new IceTile();
					bag.addTile(t);
				}
				read.nextLine();
				int fire = read.nextInt();
				for (int i = 0; i != fire; i++) {
					Tile t = new FireTile();
					bag.addTile(t);
				}
				read.nextLine();
				int back = read.nextInt();
				for (int i = 0; i != back; i++) {
					Tile t = new BackTrackTile();
					bag.addTile(t);
				}
				read.nextLine();
				int doubleMove = read.nextInt();
				for (int i = 0; i != doubleMove; i++) {
					Tile t = new DoubleMoveTile();
					bag.addTile(t);
				}
				read.nextLine();
				int goal = read.nextInt();
				for (int i = 0; i != goal; i++) {
					shape = ShapeOfTile.GOAL_TILE;
					Tile t = new FloorTile(1,false,shape);
					bag.addTile(t);
				}
			}
			if(read.hasNext(">CurrentTile")) {
				read.nextLine();
				line = new Scanner(read.nextLine());
				line.useDelimiter(",");
				if(line.hasNextInt()) {
					int turns = line.nextInt();
					String type = line.next();
					ActionTile t = null;
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
					t.setTurnsSinceUse(turns);
					currentTile = t;
				} else {
					String fixed = line.next();
					int o = line.nextInt();
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
				line.close();
			}
			if(read.hasNext(">CurrentPlayer")) {
				read.next();
				currentPlayer = read.nextInt();
			}
			if(read.hasNext(">Players")) {
				read.next();
				numPlayers = read.nextInt();
				players = new Player[numPlayers];
				for (int i = 0; i < numPlayers; i++) {
					line = new Scanner(read.next());
					line.useDelimiter(",");
					int playerNum = line.nextInt();
					int x = line.nextInt();
					int y = line.nextInt();
					int x0 = line.nextInt();
					int y0 = line.nextInt();
					int x1 = line.nextInt();
					int y1 = line.nextInt();
					Coord xy = new Coord(x,y);
					Coord xy0 = new Coord(x0,y0);
					Coord xy1 = new Coord(x1,y1);
					String name = line.next();
					Player p = new Player(playerNum,loadProfile(name));
					players[playerNum] = p;
					p.setCurrentPosition(xy);
					p.setPrevPosition(0,xy0);
					p.setPrevPosition(1,xy1);
					int fire = line.nextInt();
					for (int j = 0; j < fire; j++) {
						ActionTile t = new FireTile();
						p.addActionTile(t);
					}
					int ice = line.nextInt();
					for (int j = 0; j < ice; j++) {
						ActionTile t = new IceTile();
						p.addActionTile(t);
					}
					int back = line.nextInt();
					for (int j = 0; j < back; j++) {
						ActionTile t = new BackTrackTile();
						p.addActionTile(t);
					}
					int dMove = line.nextInt();
					for (int j = 0; j < dMove; j++) {
						ActionTile t = new DoubleMoveTile();
						p.addActionTile(t);
					}
					line.close();
				}
			}
			read.nextLine();
		}
		GameBoard board = new GameBoard(height,width,boardMap,actionMap);
		//System.out.println(height);
		//System.out.println(width);
		Game game = new Game(board);
		game.setCurrentTile(currentTile);
		game.setTileBag(bag);
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

		//next, all tiles on the board: FloorTile(x,y,orientation ,isFixed,shape) 
		newFile = newFile + ">Board\n";
		for (int i = 0; i < height; i++) {
			for (int j = 0 ; j < width ; j++) {
				FloorTile t = g.getTileAt(new Coord(i,j));
				ShapeOfTile shape = t.getShape();
				int o = t.getOrientation();
				boolean isFixed = t.isFixed();
				line = i + "," + j + "," + isFixed + "," + o + "," + shape;
				newFile = newFile + line + "\n";
			}
		}

		//next, all tiles on action board: ActionTile(x,y,turns since use, type of actiontile)
		newFile = newFile + ">ActionBoard\n";
		for (int i = 0; i < height; i++) {
			for (int j = 0 ; j < width ; j++) {
				ActionTile t = g.getAction(new Coord(i,j));
				int turns = ((ActionTile) t).getTurnsSinceUse();
				String type = "";
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
		while (bag.getSize() > 0) {
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

		//save current tile FloorTile(isFixed,orientation, shape) & ActionTile(turns,type)

		Tile currentTile = game.getCurrentTile();
		if(!(currentTile == null)) {
			newFile = newFile + ">CurrentTile\n";
			if(currentTile instanceof FloorTile) {
				ShapeOfTile shape = ((FloorTile) currentTile).getShape();
				int o = ((FloorTile) currentTile).getOrientation();
				Boolean isFixed = ((FloorTile) currentTile).isFixed();
				line = isFixed + "," + o + "," + shape;
			}
			if(currentTile instanceof ActionTile) {
				int turns = ((ActionTile) currentTile).getTurnsSinceUse();
				String type = "";
				if (currentTile instanceof FireTile) {
					type = "fire";
				}
				if(currentTile instanceof IceTile) {
					type = "ice";
				}
				if(currentTile instanceof BackTrackTile) {
					type = "back";
				}
				if(currentTile instanceof DoubleMoveTile) {
					type = "dmove";
				}
				line = turns + "," + type;
			}
			newFile = newFile + line + "\n";
		}

		//save current player(name)
		newFile = newFile + ">CurrentPlayer\n";
		int p = game.getCurrentPlayerNum();
		newFile = newFile + p + "\n";

		//save players (name, playerNum, actionTiles, current position, previous positions)
		newFile = newFile + ">Players\n";
		int numPlayers = game.getNumPlayers();
		newFile = newFile + numPlayers + "\n";
		Player[] players = game.getPlayers();
		for (Player player:players) {
			int playerNum = player.getPlayerNumber();
			int x = player.getCurrentPosition().getX();
			int y = player.getCurrentPosition().getY();
			int x0 = player.getPrevPosition(0).getX();
			int y0 = player.getPrevPosition(0).getY();
			int x1 = player.getPrevPosition(1).getX();
			int y1 = player.getPrevPosition(1).getY();
			String name = player.getProfile().getPlayerName();
			line = playerNum + "," + x + "," + y + "," + x0 + "," + y0 + "," + x1 + "," + y1 + "," + name;

			ArrayList<ActionTile> tiles = player.getActionTiles();
			int fireTile = 0;
			int iceTile = 0;
			int backTile = 0;
			int doubleTile = 0;
			while(tiles.size() > 0) {
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
		playerName = playerName.toLowerCase();
		while (read.hasNextLine()) {
			line = read.nextLine();
			System.out.println(line);
			if (line.contains(playerName)) {
				line = playerName + "," + wins + "," + losses + "," + gamesPlayed;
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

	/**
	 * Deletes a player's profile from file.
	 * @param playerName name of user to be deleted
	 * @throws IOException
	 */
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
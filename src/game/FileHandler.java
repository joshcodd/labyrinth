package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * FileHandler class for saving and loading levels and players
 * @author AndrewCarruthers
 * @StudentID 987747
 */
class FileHandler {
	
	
	/**
	 * Opens and reads in a level from file
	 * @param fileName level file name.
	 * @param players list of players.
	 * @param bag empty tile bag.
	 * @return a constructed game board.
	 */
	public static GameBoard loadGameFile(String fileName,Players[] players,TileBag bag) {
		GameBoard board = new GameBoard(0, 0, null, bag);
		try { 
			File level = new File(fileName.concat("txt"));
			Scanner line = new Scanner(level);
			board = FileHandler.loadGameFile(line,bag);
			int x1 = line.nextInt();
			int y1 = line.nextInt();
			int x2 = line.nextInt();
			int y2 = line.nextInt();
			int x3 = line.nextInt();
			int y3 = line.nextInt();
			int x4 = line.nextInt();
			int y4 = line.nextInt();
			Coord p1 = new Coord(x1,y1);
			Coord p2 = new Coord(x2,y2);
			Coord p3 = new Coord(x3,y3);
			Coord p4 = new Coord(x4,y4);
			//needs player locations saved to each player
			
			line.close();
		   } catch (FileNotFoundException e) {
			   System.out.println("Error, No file exists.");
			   e.printStackTrace();
		   }
		return board;
	}
	
	private static GameBoard loadGameFile (Scanner line, TileBag bag) {
		HashMap<Coord,Tile> fixedTiles = new HashMap<>(); 
		line.useDelimiter(",");
		int height = line.nextInt();
		int width = line.nextInt();
		//number of fixed tiles to create
		int k = line.nextInt();
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
			Tile fixedTile = new FloorTile(orientation,true,shape); 
			fixedTiles.put(location,fixedTile);
		}
		
		ShapeOfTile newShape = null;
		
		int bendTile = line.nextInt(); // adding bend tiles
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.BEND;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int tTile = line.nextInt(); // adding t tiles
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.T_SHAPE;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int straightTile = line.nextInt(); // adding straight tiles
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.STRAIGHT;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int crossTile = line.nextInt(); // adding crossroad tiles
		for (int i = 0; i != bendTile; i++) {
			newShape = ShapeOfTile.CROSSROADS;
			Tile newTile = new FloorTile(1,false,newShape);
			bag.addTile(newTile);
		}
		int iceTile = line.nextInt();
		for (int i = 0; i != iceTile; i++) {
			Tile newTile = new IceTile(); //need ice constructor
			bag.addTile(newTile);
		}
		int fireTile = line.nextInt();
		for (int i = 0; i != fireTile; i++) {
			Tile newTile = new FireTile(); //need fire constructor
			bag.addTile(newTile);
		}
		int backTile = line.nextInt();
		for (int i = 0; i != backTile; i++) {
			Tile newTile = new BackTile(); //  back constructor
			bag.addTile(newTile);
		}
		int doubleTile = line.nextInt();
		for (int i = 0; i != doubleTile; i++) {
			Tile newTile = new DoubleTile(); //double constructor
			bag.addTile(newTile);
		}
		
		//construct Game board for the level
		GameBoard board = new GameBoard(height,width,fixedTiles,bag);
		return board;
	}
	
	public static void saveGameFile (String saveName) {
		
	}
	
	public static void loadProfile (String fileName) {
		
	}
	
	public static void saveProfile (String player,int wins ,int losses) {
		
	}
}
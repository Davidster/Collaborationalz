/*
Name: Peter Granitski; ID: 27352611
COMP249
Assignment # 1
Due Date 9/26/2016 (extended from 9/23/2016 by Dr. Aiman Hanna)
 */

package game.battleShip;

import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a Map object that is used as a playing field for the game, along with all the other methods
 * used to coordinate the game procedures
 * @author Peter Granitski
 * @version 1.0
 */

public class Map{
	GamePiece[][] tileMap;
	private int playerShips, enemyShips;
	private boolean playerRepeating, enemyRepeating;
	public static final int STARTING_SHIPS = 6, STARTING_GRENADES = 4;
	
	Random randomGenerator = new Random();
	/**
	 * This constructs a 2 dimensional array of GamePiece objects, sets the amounts of playerShips and enemyShips
	 * to the starting number 6, and the playerRepeating and enemyRepeating fields to false by default
	 */
	public Map(){
		tileMap = new GamePiece[8][8];
		playerShips = STARTING_SHIPS;
		enemyShips = STARTING_SHIPS;
		playerRepeating = false;
		enemyRepeating = false;
	}
	
	/**
	 * This returns the length of the first horizontal array
	 * @return this tileMap's length
	 */
	public int getLength(){
		return tileMap.length;
	}
	
	/**
	 * This returns the length of the horizontal array at the index of the vertical array
	 * @param index index of the vertical array
	 * @return the length of this horizontal array
	 */
	public int getLength(int index){
		return tileMap[index].length;
	}
	
	/**
	 * This returns the current amount of enemyShips
	 * @return the amount of enemyShips
	 */
	public int getEnemyShips(){
		return enemyShips;
	}
	/**
	 * This returns the current amount of playerShips
	 * @return the amount of playerShips
	 */
	public int getPlayerShips(){
		return playerShips;
	}
	
	/**
	 * This returns if the player repeats their turn
	 * @return true if the opponent hit one of this player's grenades
	 */
	public boolean isPlayerRepeating(){
		return playerRepeating;
	}
	
	/**
	 * This sets the boolean value of playerRepeating
	 * @param repeating this is true if the player will repeat their turn
	 */
	public void setPlayerRepeating(boolean repeating){
		this.playerRepeating = repeating;
	}
	
	/**
	 * This returns if the enemy repeats their turn
	 * @return true if the opponent hit one of the enemies' grenades
	 */
	public boolean isEnemyRepeating(){
		return enemyRepeating;
	}
	
	/**
	 * This sets the boolean value of enemyRepeating
	 * @param repeating this is true if the enemy will repeat their turn
	 */
	public void setEnemyRepeating(boolean repeating){
		this.enemyRepeating = repeating;
	}
	
	/**
	 * This method places a GamePiece object into the tileMap
	 * @param row the row in which the GamePiece will be placed
	 * @param column the column in which the GamePiece will be placed
	 * @param gamePiece the GamePiece object that will be placed
	 */
	public void place(int row, int column, GamePiece gamePiece){
		if(row < 0){
			row = 0;
		}
		tileMap[row][column] = gamePiece;
	}
	
	/**
	 * This method places a GamePiece object into the tileMap, this method is mainly used in the Driver class
	 * @param row the row in which the GamePiece will be placed
	 * @param column the column in which the GamePiece will be placed
	 * @param obj the GamePiece object that will be placed
	 */
	public void place(int row, char column, GamePiece obj){
		if(row < 0){
			row = 0;
		}
		int columnIndex = ((int) column)-65;
		
		while(row < 1 || row > 8 || columnIndex < 0 || columnIndex > 7){
			System.out.println("Sorry, coordinates outside grid. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			column = input.charAt(0);
			row = Integer.parseInt(input.charAt(1) + "");
			
			columnIndex = ((int) column)-65;
		}
		while(getGamePiece(row-1, columnIndex) != null){
			System.out.println("Sorry, coordinates already used. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			column = input.charAt(0);
			row = Integer.parseInt(input.charAt(1) + "");
			
			columnIndex = ((int) column)-65;
		}
		
		place(row-1, columnIndex, obj);
	}
	/**
	 * This returns the GamePiece object at a given row and column within the tileMap
	 * @param row the row which we are looking at
	 * @param column the column which we are looking at
	 * @return GamePiece object
	 */
	public GamePiece getGamePiece(int row, int column){
		if(row < 0){
			row = 0;
		}
		return tileMap[row][column];
	}
	
	/**
	 * Convinience method used to check for if the coordinate input is valid
	 * @param row the row of this tileMap
	 * @param column the column of this tileMap
	 * @return true if coordinates are in valid range
	 */
	public boolean isInputValid(int row, int column){
		return row >= 1 && row <= 8 && column >= 0 && column <= 7;
	}
	
	/**
	 * This tells us if the GamePiece in question belongs to the player
	 * @param gamePiece GamePiece object in question
	 * @return true if the object is either a player ship or grenade
	 */
	public boolean isPlayerCoordinate(GamePiece gamePiece){
		if(gamePiece == null)
			return false;
		
		char displayValue = gamePiece.getDisplayValue();
		
		return displayValue == GamePiece.PLAYER_SHIP || displayValue == GamePiece.PLAYER_GRENADE;
	}
	
	/**
	 * This tells us if the GamePiece in question belongs to the enemy
	 * @param gamePiece GamePiece object in question
	 * @return true if the object is either an enemy ship or grenade
	 */
	public boolean isEnemyCoordinate(GamePiece gamePiece){
		if(gamePiece == null)
			return false;
		
		char displayValue = gamePiece.getDisplayValue();
		
		return displayValue == GamePiece.ENEMY_SHIP || displayValue == GamePiece.ENEMY_GRENADE;
	}
	
	/**
	 * This is used to launch the player's rockets and takes care of what happens when it hits other objects or misses
	 */
	public void launchPlayerRocket(){
		GamePiece gamePiece;
		int row, column;
		
		do{
			Scanner kb = new Scanner(System.in);
		
			String input = kb.next();
			
			column = Driver.columnCharToInt(input.toUpperCase().charAt(0));
			row = Integer.parseInt(input.charAt(1) + "");
			
			gamePiece = getGamePiece(row-1, column);
			
			if(!isInputValid(row, column))
				System.out.println("Sorry, coordinates outside grid. Try again.");
			else if(isPlayerCoordinate(gamePiece))
				System.out.println("This coordinate contains one of your game pieces. Try again.");
			else if((gamePiece != null && gamePiece.getCalled()))
				System.out.println("Sorry, coordinates already chosen. Try again.");
		
		}while(!isInputValid(row, column) || (gamePiece != null && gamePiece.getCalled()) || isPlayerCoordinate(gamePiece));
		
		if(tileMap[row-1][column] == null){
			place(row-1, column, new GamePiece(GamePiece.MISS_HIT));

			System.out.println("Nothing.");
		}else if(gamePiece.getDisplayValue() == GamePiece.ENEMY_SHIP){
			enemyShips--;
			
			System.out.println("Ship hit.");
		}
		else if(gamePiece.getDisplayValue() == GamePiece.ENEMY_GRENADE){
			enemyRepeating = true;
			
			System.out.println("Boom! Grenade hit.");
		}
		if(gamePiece != null)
			gamePiece.setCalled(true);
	}
	
	/**
	 * This is used to launch the enemy's rockets and takes care of what happens when it hits other objects or misses
	 */
	public void launchEnemyRocket(){
		GamePiece gamePiece;
		int randomRow, randomColumn;
		char randomChar;
		do{
			randomRow = randomGenerator.nextInt(8);
			randomColumn = randomGenerator.nextInt(8);
			
			 gamePiece = getGamePiece(randomRow, randomColumn);
			
		}while((gamePiece != null && gamePiece.getCalled()) || isEnemyCoordinate(gamePiece));
		
		randomChar = Driver.columnIntToChar(randomColumn);
		
		System.out.println("Position of my rocket: "+randomChar+(randomRow+1));
		
		if(tileMap[randomRow][randomColumn] == null){
			place(randomRow, randomColumn, new GamePiece(GamePiece.MISS_HIT));
			
			System.out.println("Nothing.");
		}else if(getGamePiece(randomRow, randomColumn).getDisplayValue() == GamePiece.PLAYER_SHIP){
			playerShips--;
			
			System.out.println("Ship hit.");
		}
		else if(getGamePiece(randomRow, randomColumn).getDisplayValue() == GamePiece.PLAYER_GRENADE){
			playerRepeating = true;
			
			System.out.println("Boom! Grenade hit.");
		}
		if(gamePiece != null)
			gamePiece.setCalled(true);
	}
	
	/**
	 * This can be used by developers to see all the objects in the tileMap, to make sure they are where they are supposed to be
	 */
	public void printMap(){
		for(int i = 0; i < getLength(); i++){
			for(int j = 0; j < getLength(i); j++){
				if(getGamePiece(i,j) == null){
					System.out.print('_'+"  ");
				}else{
				System.out.print(getGamePiece(i, j).getDisplayValue()+"  ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * this method is meant to only display coordinates that have been called by either player 
	 * once the game has begun, after initializing the map by placing all objects in the tileMap
	 */
	public void printGameMap(){
		for(int i = 0; i < getLength(); i++){
			for(int j = 0; j < getLength(i); j++){
				if(getGamePiece(i,j) == null){
					System.out.print('_'+"  ");
				}else if(getGamePiece(i,j).getCalled() == false){
					System.out.print('_'+"  ");
				}else{
					System.out.print(getGamePiece(i, j).getDisplayValue()+"  ");
				}
			}
			System.out.println();
		}		
	}
}
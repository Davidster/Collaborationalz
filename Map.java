package game.battleShip;

import java.util.Scanner;

public class Map {
	GamePiece[][] tileMap;
	char[][] printedTileMap;
	private int shipsAlive, enemyShips, grenadesAlive, enemyGrenades;
	private final int STARTING_SHIPS = 6, STARTING_GRENADES =4;
	
	public Map(){
		tileMap = new GamePiece[8][8];
		printedTileMap = new char[9][9];
		shipsAlive = STARTING_SHIPS;
		enemyShips = STARTING_SHIPS;
		grenadesAlive = STARTING_GRENADES;
		enemyGrenades = STARTING_GRENADES;
	}
	
	public int getLength(){
		return tileMap.length;
	}
	
	public int getLength(int index){
		return tileMap[index].length;
	}
	
	public void place(int row, int collumn, char character){
		if(row < 0){
			row = 0;
		}
		printedTileMap[row][collumn] = character;
	}
	
	public void place(int row, int collumn, GamePiece gamePiece){
		if(row < 0){
			row = 0;
		}
		tileMap[row][collumn] = gamePiece;
	}
	
	//used in main
	public void place(int row, char collumn, GamePiece obj){
		if(row < 0){
			row = 0;
		}
		int collumnIndex = ((int) collumn)-65;
		
		System.out.println("collumnIndex = "+collumnIndex);
		System.out.println("row = "+row);
		System.out.println();
		
		if(row < 1 || row > 8 || collumnIndex < 0 || collumnIndex > 7){
			System.out.println("Sorry, coordinates outside grid. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = input.charAt(1)-48;
			
			place(row-1, collumn, obj);
		}
		else if(getGamePiece(row-1, collumnIndex) != null){
			System.out.println("Sorry, coordinates already used. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = input.charAt(1)-48;
			
			place(row-1, collumn, obj);
		}
		
		collumnIndex = ((int) collumn)-65;
		
		place(row-1, collumnIndex, obj);
	}
	
	public GamePiece getGamePiece(int row, int collumn){
		return tileMap[row][collumn];
	}
	
	public boolean exists(int row, int collumn){
		if(tileMap[row][collumn] != null){
			return true;
		}
		return false;
	}
	
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
	
	/*
	 * init map does shit all
	 */
	public void initMap(){
		//decimal code for 'A' and '1'
		int asciiCollumn = 64, asciiRow = 49, ctr = 1;
		
		for(int i = 0; i < getLength(); i++){
			for(int j = 0; j < getLength(i); j++){
				if(i == 0){
					place(i, j, (char) asciiCollumn++);
				}
				else if(i == ctr && j == 0){
					place(i, j , (char) asciiRow++);
					ctr++;
				}
				else{
					place(i, j, null);
				}
			}
			
		}
		place(0, 0, 'X');
	}
}

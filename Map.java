package game.battleShip;

import java.util.Scanner;

public class Map {
	GamePiece[][] tileMap;
	private int shipsAlive, enemyShips, grenadesAlive, enemyGrenades;
	private final int STARTING_SHIPS = 6, STARTING_GRENADES =4;
	
	public Map(){
		tileMap = new GamePiece[9][9];
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
	
	public void place(int i, int j, GamePiece input){
		tileMap[i][j] = input;
	}
	
	public void place(int row, char collumn, GamePiece obj){
		int collumnIndex = ((int) collumn)-64;
		
		System.out.println("collumnIndex = "+collumnIndex);
		System.out.println("row = "+row);
		
		if(row < 1 || row > 8 || collumnIndex < 1 || collumnIndex > 8){
			System.out.println("Sorry, coordinates outside grid. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = input.charAt(1)-48;
			
//			System.out.println("place row = "+row);
//			System.out.println("place collumn = "+collumn);
			
			place(row, collumn, obj);
		}
		else if(getChar(row, collumnIndex) != '_'){
			System.out.println("Sorry, coordinates already used. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			collumn = input.charAt(0);
			row = input.charAt(1)-48;
			
			place(row, collumn, obj);
		}
		place(row, collumnIndex, obj);
	}
	
	public GamePiece getChar(int row, int collumn){
		return tileMap[row][collumn];
	}
	
	public void printMap(){
		for(int i = 0; i < getLength(); i++){
			for(int j = 0; j < getLength(i); j++){
				System.out.print(getChar(i, j)+"  ");
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
					place(i, j, '_');
				}
			}
			
		}
		place(0, 0, 'X');
	}
}

package game.battleShip;

import java.util.Scanner;

public class Map{
	GamePiece[][] tileMap;
	private int playerShips, enemyShips, grenadesAlive, enemyGrenades, myTurn, enemyTurn;
	private final int STARTING_SHIPS = 6, STARTING_GRENADES = 4;
	
	public Map(){
		tileMap = new GamePiece[8][8];
		playerShips = STARTING_SHIPS;
		enemyShips = STARTING_SHIPS;
		grenadesAlive = STARTING_GRENADES;
		enemyGrenades = STARTING_GRENADES;
		myTurn = 1;
		enemyTurn = 1;
	}
	
	public int getLength(){
		return tileMap.length;
	}
	
	public int getLength(int index){
		return tileMap[index].length;
	}
	
	public int getEnemyShips(){
		return enemyShips;
	}
	
	public int getPlayerShips(){
		return playerShips;
	}
	
	public int getTurn(){
		return myTurn;
	}
	
	public int getEnemyTurn(){
		return enemyTurn;
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
		
		while(row < 1 || row > 8 || collumnIndex < 0 || collumnIndex > 7){
			System.out.println("Sorry, coordinates outside grid. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = Integer.parseInt(input.charAt(1) + "");
			
			collumnIndex = ((int) collumn)-65;
		}
		while(getGamePiece(row-1, collumnIndex) != null){
			System.out.println("Sorry, coordinates already used. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = Integer.parseInt(input.charAt(1) + "");
			
			collumnIndex = ((int) collumn)-65;
		}
		
		place(row-1, collumnIndex, obj);
	}
	
	public GamePiece getGamePiece(int row, int collumn){
		if(row < 0){
			row = 0;
		}
		return tileMap[row][collumn];
	}
	
	public void launchRocket(int row, char collumn){
		int collumnIndex = ((int) collumn)-65;
		myTurn = 1;
		
//		System.out.println("row = "+row);
//		System.out.println("collumnIndex = "+collumnIndex);
		
		while(row < 1 || row > 8 || collumnIndex < 0 || collumnIndex > 7){
			System.out.println("Sorry, coordinates outside grid. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			
			collumn = input.charAt(0);
			row = Integer.parseInt(input.charAt(1) + "");
			
			collumnIndex = ((int) collumn)-65;
		}
		if(getGamePiece(row-1, collumnIndex) != null){
			while(getGamePiece(row-1, collumnIndex).getCalled() == true){
				System.out.println("Sorry, coordinates already used. Try again.");
			
				@SuppressWarnings("resource")
				Scanner kb = new Scanner(System.in);
			
				String input = kb.next();
			
				collumn = input.charAt(0);
				row = Integer.parseInt(input.charAt(1) + "");
			
				collumnIndex = ((int) collumn)-65;
			}
		}
		if(tileMap[row-1][collumnIndex] == null){
			place(row-1, collumnIndex, new GamePiece("void"));

			System.out.println("Nothing.");
		}else if(getGamePiece(row-1, collumnIndex).getDisplayValue() == 'S'){
			enemyShips--;
			
			System.out.println("Ship hit.");
		}else if(getGamePiece(row-1, collumnIndex).getDisplayValue() == 's'){
			playerShips--;
			
			System.out.println("Ship hit.");
		}
		else if(getGamePiece(row-1, collumnIndex).getDisplayValue() == 'G'){
			enemyTurn++;
			
			System.out.println("Boom! Grenade hit.");
		}else if(getGamePiece(row-1, collumnIndex).getDisplayValue() == 'g'){
			myTurn++;
			
			System.out.println("Boom! Grenade hit.");
		}
		getGamePiece(row-1, collumnIndex).setCalled(true);
	}
	
	//for myself to check what's there
	public void printMap(){
		//decimal code for 'A' and '1'
//		int asciiCollumn = 64, asciiRow = 49, ctr = 1;
//		
//		for(int i = 0; i < printedTileMap.length; i++){
//			System.out.print((char) asciiCollumn++ +"  ");
//		}
		
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
	
	//this method is meant to only display coordinates that have been called by either player 
	//once the game has started
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

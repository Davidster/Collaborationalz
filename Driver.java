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

public class Driver {
	public static void main(String[] args){
		Random randomGenerator = new Random();
				
		Map tileMap = new Map();
		
		String input;
		char x;
		int y;
		
		Scanner kb = new Scanner(System.in);
		
		System.out.println("Hi, let's play BattleShip!");

		for(int i = 0; i < Map.STARTING_SHIPS; i++){
			System.out.print("Enter the coordinates of your ship #"+(i+1)+": ");
			
			input = kb.next();
			x = input.toUpperCase().charAt(0);
			y = Integer.parseInt(input.charAt(1) + "");
			
			tileMap.place(y, x, new GamePiece(GamePiece.PLAYER_SHIP));
			
			System.out.println();
		}
		
		for(int i = 0; i < Map.STARTING_GRENADES; i++){
			System.out.print("Enter the coordinates of your grenade #"+(i+1)+": ");
			
			input = kb.next();
			
			x = input.toUpperCase().charAt(0);
			y = Integer.parseInt(input.charAt(1) + "");
			
			tileMap.place(y, x, new GamePiece(GamePiece.PLAYER_GRENADE));
			
			System.out.println();
		}
		
		//Now the computer will randomly set its rockets and grenades on the field
		int randomRow = 0, randomCollumn = 0;
		
		for(int i = 0; i < Map.STARTING_SHIPS; i++){
			
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
				
				while(tileMap.getGamePiece(randomRow, randomCollumn) !=  null){
					randomRow = randomGenerator.nextInt(8);
					randomCollumn = randomGenerator.nextInt(8);
				}
				
				tileMap.place(randomRow, randomCollumn, new GamePiece(GamePiece.ENEMY_SHIP));
			System.out.println();
		}
		
		for(int i = 0; i < Map.STARTING_GRENADES; i++){
			
			randomRow = randomGenerator.nextInt(8);
			randomCollumn = randomGenerator.nextInt(8);
			
			while(tileMap.getGamePiece(randomRow, randomCollumn) !=  null){
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
			}
			tileMap.place(randomRow, randomCollumn, new GamePiece(GamePiece.ENEMY_GRENADE));
			
			System.out.println();
		}
		
		System.out.println("OK, the computer placed its ships and grenades at random. Let's play!");
		
		while(tileMap.getEnemyShips() > 0 && tileMap.getPlayerShips() > 0){
				System.out.print("Position of your rocket: ");
				
				tileMap.launchPlayerRocket();
				
				tileMap.printGameMap();
				
				if(tileMap.isPlayerRepeating()){
					System.out.print("You get another turn! Position of your rocket: ");
					
					tileMap.launchPlayerRocket();
					tileMap.printGameMap();
					tileMap.setPlayerRepeating(false);
				}
				
				tileMap.launchEnemyRocket();
				tileMap.printGameMap();
				
				if(tileMap.isEnemyRepeating()){
					System.out.print("I get another turn! ");
					
					tileMap.launchEnemyRocket();
					tileMap.printGameMap();
					tileMap.setEnemyRepeating(false);
				}
		}
		
		if(tileMap.getEnemyShips() == 0){
			System.out.println("After a difficult but fair game...\nYou win!");
		}else{
			System.out.println("After a difficult but fair game...\nYou lose!");
		}
		
		kb.close();
	}
	
	/**
	 * This is used as a convenience method to convert the character value of this column to an integer
	 * @param column the character representation of the column of this tileMap
	 * @return the integer representation of this column
	 */
	public static int columnCharToInt(char column){
		return ((int) column)-65;
	}
	
	/**
	 * This is used as a convenience method to convert the integer value of this column to a character
	 * @param column the integer representation of the column of this tileMap
	 * @return the character representation of this column
	 */
	public static char columnIntToChar(int column){
		return (char) (column + 65);
	}
	
}
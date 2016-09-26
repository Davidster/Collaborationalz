package game.battleShip;

import java.util.Scanner;
import java.util.Random;

public class Driver extends Map{
	public static void main(String[] args){
		Random randomGenerator = new Random();
		
		GamePiece playerShip = new GamePiece("PlayerShip");
		GamePiece playerGrenade = new GamePiece("PlayerGrenade");
		GamePiece enemyShip = new GamePiece("EnemyShip");
		GamePiece enemyGrenade = new GamePiece("EnemyGrenade");
		
		Map tileMap = new Map();
		
		String input;
		char x;
		int y;
		
		Scanner kb = new Scanner(System.in);

		for(int i = 0; i < 6; i++){
			System.out.print("Enter the coordinates of your ship #"+(i+1)+":");
			
			input = kb.next();
			x = input.charAt(0);
			y = input.charAt(1)-48;
			
			tileMap.place(y, x, playerShip);
			
			System.out.println();
		}
		
		for(int i = 0; i < 4; i++){
			System.out.print("Enter the coordinates of your grenade #"+(i+1)+":");
			
			input = kb.next();
			
			x = input.charAt(0);
			y = input.charAt(1)-48;
			
			tileMap.place(y, x, playerGrenade);
			
			System.out.println();
		}
		
		tileMap.printMap();
		
		//Now the computer will randomly set its rockets and grenades on the field
		int randomRow = 0, randomCollumn = 0;
		
		for(int i = 0; i < 6; i++){
			
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
				
				while(tileMap.getGamePiece(randomRow, randomCollumn) !=  null){
					randomRow = randomGenerator.nextInt(8);
					randomCollumn = randomGenerator.nextInt(8);
				}
				
				tileMap.place(randomRow, randomCollumn, enemyShip);
			System.out.println();
		}
		
		for(int i = 0; i < 4; i++){
			
			randomRow = randomGenerator.nextInt(8);
			randomCollumn = randomGenerator.nextInt(8);
			
			while(tileMap.getGamePiece(randomRow, randomCollumn) !=  null){
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
			}
			tileMap.place(randomRow, randomCollumn, enemyGrenade);
			
			System.out.println();
		}
		
		tileMap.printMap();
		
		System.out.println("OK, the computer placed its ships and grenades at random. Let's play!");
		
		tileMap.printGameMap();
		
		char randomChar;
		
		while(tileMap.getEnemyShips() > 0 || tileMap.getPlayerShips() > 0){
			do{
				System.out.print("Position of your rocket: ");
				
				input = kb.next();
				x = input.charAt(0);
				y = input.charAt(1)-48;
				
				tileMap.launchRocket(y, x);
				
				tileMap.printGameMap();
			}while(tileMap.getTurn() == 2);
			
			do{
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = 65 + randomGenerator.nextInt(8);
				randomChar = (char) randomCollumn;
				
				System.out.println(randomChar);
				System.out.println(randomCollumn);
				System.out.println("Position of my rocket: "+randomChar+randomRow);
				tileMap.launchRocket(randomRow, randomChar);
				tileMap.printGameMap();
			}while(tileMap.getEnemyTurn() == 2);
		}
		
		kb.close();
	}
}

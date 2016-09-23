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
		tileMap.initMap();
		
		String input;
		char x;
		int y;
		
		Scanner kb = new Scanner(System.in);
		
		//initialize and print map
		tileMap.initMap();
		tileMap.printMap();
		
//		System.out.println((int)'A'-64);
		
		for(int i = 0; i < 6; i++){
			System.out.print("Enter the coordinates of your ship #"+(i+1)+":");
			
			input = kb.next();
			x = input.charAt(0);
			y = input.charAt(1)-48;
			
			System.out.println("x = "+x);
			System.out.println("y = "+y);
			
			tileMap.place(y, x, playerShip);
			
			System.out.println();
		}
		
		for(int i = 0; i < 4; i++){
			System.out.print("Enter the coordinates of your grenade #"+(i+1)+":");
			
			input = kb.next();
			
			x = input.charAt(0);
			y = input.charAt(1)-48;

//			System.out.println("x = "+x);
//			System.out.println("y = "+y);
			
			tileMap.place(y, x, playerGrenade);
			
			System.out.println();
		}
		
		tileMap.printMap();
		
		int randomRow = 0, randomCollumn = 0;
		
		for(int i = 0; i < 6; i++){
			do{
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
				
				tileMap.place(randomRow, randomCollumn, enemyShip);
			}while(tileMap.exists(randomRow, randomCollumn));
			
			System.out.println();
		}
		
		for(int i = 0; i < 4; i++){
			do{
				randomRow = randomGenerator.nextInt(8);
				randomCollumn = randomGenerator.nextInt(8);
				
				tileMap.place(randomRow, randomCollumn, enemyShip);
			}while(tileMap.exists(randomRow, randomCollumn));
			
			System.out.println();
		}
		
		tileMap.printMap();
		
		kb.close();
	}
}

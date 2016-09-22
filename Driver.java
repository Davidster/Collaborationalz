package game.battleShip;

import java.util.Scanner;
import java.util.Random;

public class Driver{
	private int shipsAlive = 6, enemyShips = 6, grenadesAlive = 4, enemyGrenades;
	
	public static void main(String[] args){
		Random randomGenerator = new Random();
		
		Map tileMap = new Map();
		tileMap.initMap(tileMap);
		
		String input;
		char x;
		int y;
		
		Scanner kb = new Scanner(System.in);
		
		//initialize and print map
		tileMap.initMap(tileMap);
		tileMap.printMap(tileMap);
		
//		System.out.println((int)'A'-64);
		
		for(int i = 0; i < 6; i++){
			System.out.print("Enter the coordinates of your ship #"+(i+1)+":");
			
			input = kb.next();
			x = input.charAt(0);
			y = input.charAt(1)-48;
			
//			System.out.println("x = "+x);
//			System.out.println("y = "+y);
			
			tileMap.place(y, x, tileMap, BattleShip.ship);
			System.out.println();
		}
		
		for(int i = 0; i < 4; i++){
			System.out.print("Enter the coordinates of your grenade #"+(i+1)+":");
			
			input = kb.next();
			
			x = input.charAt(0);
			y = input.charAt(1)-48;

//			System.out.println("x = "+x);
//			System.out.println("y = "+y);
			
			tileMap.place(y, x, tileMap, Grenade.grenade);
			System.out.println();
			
		}
		
		for(int i = 0; i < 6; i++){

			tileMap.place(randomGenerator.nextInt(9), randomGenerator.nextInt(9), tileMap, BattleShip.ship);
			System.out.println();
		}
		tileMap.printMap(tileMap);
		
		kb.close();
	}
}

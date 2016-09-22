package game.battleShip;

import java.util.Scanner;

public class Map {
	char[][] tileMap;
	public Map(){
		tileMap = new char[9][9];
	}
	
	public int getLength(){
		return tileMap.length;
	}
	
	public int getLength(int index){
		return tileMap[index].length;
	}
	
	public void place(int i, int j, Map map, char input){
		map.tileMap[i][j] = input;
	}
	
	public void place(int row, char collumn, Map map, char obj){
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
			
			place(row, collumn, map, obj);
		}
		else if(getChar(row, collumnIndex, map) != '_'){
			System.out.println("Sorry, coordinates already used. Try again.");
			
			@SuppressWarnings("resource")
			Scanner kb = new Scanner(System.in);
			
			String input = kb.next();
			collumn = input.charAt(0);
			row = input.charAt(1)-48;
			
			place(row, collumn, map, obj);
		}
		place(row, collumnIndex, map, obj);
	}
	
	public char getChar(int row, int collumn, Map map){
		return map.tileMap[row][collumn];
	}
	
	public void printMap(Map map){
		for(int i = 0; i < map.getLength(); i++){
			for(int j = 0; j < map.getLength(i); j++){
				System.out.print(getChar(i, j, map)+"  ");
			}
			System.out.println();
		}
	}
	
	public void initMap(Map map){
		//decimal code for 'A' and '1'
		int asciiCollumn = 64, asciiRow = 49, ctr = 1;
		
		for(int i = 0; i < map.getLength(); i++){
			for(int j = 0; j < map.getLength(i); j++){
				if(i == 0){
					place(i, j, map, (char) asciiCollumn++);
				}
				else if(i == ctr && j == 0){
					place(i, j , map, (char) asciiRow++);
					ctr++;
				}
				else{
					place(i, j, map, '_');
				}
			}
			
		}
		place(0, 0, map, 'X');
	}
}

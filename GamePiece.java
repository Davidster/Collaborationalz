/*
Name: Peter Granitski; ID: 27352611
COMP249
Assignment # 1
Due Date 9/26/2016 (extended from 9/23/2016 by Dr. Aiman Hanna)
 */

package game.battleShip;

/**
 * This class implements a GamePiece object that is used through out the game
 * @author Peter Granitski
 * @version 1.0
 */
public class GamePiece {
	private boolean called;
	private char displayValue;
	
	public static final char PLAYER_SHIP = 's', PLAYER_GRENADE = 'g', ENEMY_SHIP = 'S', ENEMY_GRENADE = 'G', MISS_HIT = '*';
	
	/**
	 * This constructs a GamePiece object with a specified pieceType
	 * @param pieceType this can be PLAYER_SHIP, PLAYER_GRENADE, ENEMY_SHIP, ENEMY_GRENADE or EMPTY_HIT
	 * Each pieceType is assigned a character to represent it, respectively
	 */
	public GamePiece(char pieceType){
		//only called by default if it's an empty hit
		called = pieceType == MISS_HIT;
		
		displayValue = pieceType;
	}
	
	/**
	 * This returns the displayValue (character type) associated with this GamePiece
	 * @return this GamePiece's pieceType
	 */
	
	public char getDisplayValue(){
		return this.displayValue;
	}
	
	/**
	 * This sets the displayValue of this GamePiece
	 * @param displayValue this pertains to the pieceTypes and can hold values 's', 'g' standing for the players ships and grenades;
	 * 'S', 'G' standing for the computers ships and grenades; and '*' standing for any rocket that strikes a coordinate with no target
	 */
	
	public void setDisplayValue(char displayValue){
		this.displayValue = displayValue;
	}
	
	/**
	 * This returns if this GamePiece was called already or not
	 * @return true if this GamePiece was called, otherwise returns false
	 */
	
	public boolean getCalled(){
		return this.called;
	}
	
	/**
	 * This sets the called value of a GamePiece
	 * @param input can be true or false
	 */
	
	public void setCalled(boolean input){
		this.called = input;
	}
}
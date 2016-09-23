package game.battleShip;

public class GamePiece {
//	private PieceType type;	
	private boolean alive;
	private char displayValue;
	
//	enum PieceType {
//		PlayerShip, EnemyShip, PlayerGrenade, EnemyGrenade	
//	}
	
	/*
	 * Construct a new game piece
	 * 
	 * @param type the type of piece thats being added
	 */
	public GamePiece(String gamePiece){
//		this.type = type;
		alive = true;
		
		//can be rewritten as switch case
		if(gamePiece.equals("PlayerShip")){
			displayValue = 'S';
		}else if(gamePiece.equals("PlayerGrenade")){
			displayValue = 'G';
		}else if(gamePiece.equals("EnemyShip")){
			displayValue = 's';
		}else if(gamePiece.equals("EnemyGrenade")){
			displayValue = 'g';
		}
	}
	
//	public PieceType getType(){
//		return type;
//	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public char getDisplayValue(){
		return this.displayValue;
	}

}

package game.battleShip;

public class GamePiece {
//	private PieceType type;	
	private boolean alive, called;
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
		called = false;
		
		//can be rewritten as switch case
		if(gamePiece.equals("PlayerShip")){
			displayValue = 's';
		}else if(gamePiece.equals("PlayerGrenade")){
			displayValue = 'g';
		}else if(gamePiece.equals("EnemyShip")){
			displayValue = 'S';
		}else if(gamePiece.equals("EnemyGrenade")){
			displayValue = 'G';
		}else if(gamePiece.equals("void")){
			displayValue = '*';
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
	
	public void setDisplayValue(char displayValue){
		this.displayValue = displayValue;
	}
	
	public boolean getCalled(){
		return this.called;
	}
	public void setCalled(boolean input){
		this.called = input;
	}
}

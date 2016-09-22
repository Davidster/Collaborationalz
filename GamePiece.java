package game.battleShip;

public class GamePiece {
	private PieceType type;	
	private boolean alive;	
	
	enum PieceType {
		PlayerShip, EnemyShip, PlayerGrenade, EnemyGrenade	
	}
	
	/*
	 * Construvct a new game piecew
	 * 
	 * @param type the type of piece thats being added
	 */
	public GamePiece(PieceType type){
		this.type = type;
		alive = true;
	}
	
	public PieceType getType(){
		return type;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
}

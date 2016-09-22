package game.battleShip;

public class GamePiece {
	protected char gridValue;
	protected boolean alive, PC;
	
	public GamePiece(boolean PC, char gridValue){
		if(PC == true){
			this.PC = true;
		}
		alive = true;
		this.gridValue = gridValue;
	}
	
	public boolean isPC(){
		return this.PC;
	}	
	
	public boolean isAlive(){
		return this.alive;
	}
}

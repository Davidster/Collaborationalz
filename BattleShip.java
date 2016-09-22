package game.battleShip;

public class BattleShip extends GamePiece{
	static char ship = 'S';
	private int shipsAlive, enemyShips;

	public BattleShip(boolean PC){
		super(PC, ship);
		
		if(PC == true){
			enemyShips++;
		}else{
			shipsAlive++;
		}
	}
	
	public int getShips(){
		return shipsAlive;
	}
	
	public int getEnemyShips(){
		return enemyShips;
	}
}

package game.battleShip;

public class Grenade extends GamePiece{
	static char grenade = 'g';
	private int grenadesAlive, enemyGrenades;
	
	public Grenade(boolean PC){
		super(PC, grenade);
		
		if(PC == true){
			enemyGrenades++;
		}else{
			grenadesAlive++;
		}
	}
	
	public int getGrenades(){
		return grenadesAlive;
	}
	
	public int getEnemyGrenades(){
		return enemyGrenades;
	}
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class Bunny_Sim extends Simtemplate {
	//id":129,"name":"Bunny","description":"If Bunny's Attack is 0, it will summon another Bunny in an adjacent tile instead of attacking.
	
	public  boolean doesAttack(Board b, Minion m)
    {
		if(m.getAttack() == 0)
        {
			return false;
        }
		
		return true;
    }
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		if(m.getAttack() != 0 || m!=self)
        {
			return;
        }
		
		ArrayList<UPosition> nbrs = m.position.getNeightbours(); 
		if(nbrs.size()==0) return;
		
		int rndm = b.getRandomNumber(0, nbrs.size()-1);
		Minion bunny = m.getMinionToken();
		b.summonUnitOnPosition(nbrs.get(rndm), bunny);
		
    	return;
    }

}

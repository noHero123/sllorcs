package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class TempestReaver_Sim extends Simtemplate {
	//"id":299,"name":"Tempest Reaver","description":"Tempest Reaver's Attack is increased by your [current] Energy resource."
	//ranged + armor1
	
	//attack-change is done in Minion->getAttack(board) 
	
    public int getArmor(Board b ,Minion triggerEffectMinion, Minion minion)
    {
    	return 1;
    }
	

}

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class LawMemorial_Sim extends Simtemplate {
	//"id":108,"name":"Law Memorial","description":"When Law Memorial's Countdown becomes 0, it's destroyed and Order is increased by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	
	public void onCountdownReachesZero(Board b , Minion m)
    {

		b.destroyMinion(m, m);
		b.changeMaxRessource(ResourceName.ORDER, m.position.color, 1);
    	return;
    }
	
	
	
}

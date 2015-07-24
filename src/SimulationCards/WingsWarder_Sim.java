package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class WingsWarder_Sim extends Simtemplate {
	//"id":298,"name":"Wings Warder","description":"Wings Warder does not attack. Other units you control on the same row have [Ward]."
	//ward effect is checked in Minion->hasWard()
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	
	
	
	
}

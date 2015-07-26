package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class AshRunner_Sim extends Simtemplate {
	//"id":283,"name":"Ash Runner","description":"", ranged + piercing
	
	public boolean hasPiercing(Board b ,Minion m)
    {
    	return true;
    }

}

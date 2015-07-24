package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class GravelockRaider_Sim extends Simtemplate {
	//"id":2,"name":"Gravelock Raider","description":"" (relentless)
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
}

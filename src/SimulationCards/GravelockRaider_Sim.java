package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class GravelockRaider_Sim extends Simtemplate {
	//"id":2,"name":"Gravelock Raider","description":"" (relentless)
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.isRelentless=true;
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
}

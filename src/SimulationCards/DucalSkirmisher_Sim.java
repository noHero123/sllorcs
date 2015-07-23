package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class DucalSkirmisher_Sim extends Simtemplate {
	//"id":122,"name":"Ducal Skirmisher","description":"" relentless
	
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

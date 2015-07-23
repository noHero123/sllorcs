package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class RoyalSkirmisher_Sim extends Simtemplate {
	//"id":127,"name":"Royal Skirmisher","description":"" relentless
	
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

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class InfectedGravelock_Sim extends Simtemplate {
	//"id":3,"name":"Infected Gravelock","description":"Infected Gravelock has [Move] 0." //poisonous

	 public boolean isPoisonous(Board b ,Minion m)
	    {
	    	return true;
	    }
	 
	 public  void getBattlecryEffect(Board b, Minion own, Minion target)
	    {
			own.moveChanges-=1;
	        return;
	    }
	

}

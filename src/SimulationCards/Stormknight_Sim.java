package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Stormknight_Sim extends Simtemplate {
	//"id":356,"name":"Stormknight","description":"Stormknight has [Move] 2."


	 public  void getBattlecryEffect(Board b, Minion own, Minion target)
	    {
			own.moveChanges+=1;
	        return;
	    }
	

}

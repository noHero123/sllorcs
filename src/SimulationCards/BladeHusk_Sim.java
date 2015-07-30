package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class BladeHusk_Sim extends Simtemplate {
	//"id":268,"name":"Blade Husk","description":""
	//dominion = relentless
	
	 public boolean isRelentless(Board b ,Minion m)
	 {

	    	return b.isDominionActive(m.position.color);
	 }
	 
	
	 
}

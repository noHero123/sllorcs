package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class WingsSpear_Sim extends Simtemplate {
	//"id":305,"name":"Wings Spear","description":"" spiky 2
	//dominion = spiky 4
	
	
	 public int getSpikyDamage(Board b ,Minion m, Minion defender)
	 {
		 for(Minion idol : b.getPlayerIdols(Board.getOpposingColor(m.position.color)))
		 {
				if(idol.Hp<=0)
				{
					return 4;
				}
		 }
	    	return 2;
	 }
	
	 
}

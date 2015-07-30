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
		 if(b.isDominionActive(m.position.color)) return 4;

		 return 2;
	 }
	
	 
}

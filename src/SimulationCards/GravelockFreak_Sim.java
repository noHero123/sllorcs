package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class GravelockFreak_Sim extends Simtemplate {
	//"id":364,"name":"Gravelock Freak","description":"When another Gravelock you control takes damage, Gravelock Freak's Countdown is decreased by 1."


	
	 
	 public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
	    {
		 
		 	if(damagedMinion.position.color == triggerEffectMinion.position.color && damagedMinion.getSubTypes().contains(SubType.Gravelock))
		 	{
		 		triggerEffectMinion.buffMinion(0, 0, -1, b);
		 	}
	        return;
	    }

	
}

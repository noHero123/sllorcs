package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class ArhartsDisciple_Sim extends Simtemplate {
	//"id":267,"name":"Arhart's Disciple","description":"When Arhart's Disciple deals damage to a unit, opponent's idol on the same row is dealt 1 damage."


	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
    }
	
	
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != attacker || damagedMinion.isIdol || dmgdone <=0 ) return;
		 
		 Minion idol = b.getPlayerIdol(triggerEffectMinion.position.color, triggerEffectMinion.position.row);
		 b.doDmg(idol, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
	     return;
	 }
	 
}

package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class HiredSmuggler_Sim extends Simtemplate {
	//"id":281,"name":"Hired Smuggler","description":"", ranged + 
	//_Pillage: Draw 1 structure scroll.:The pillage effect is triggered when this unit deals damage to an idol during combat.

	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 b.drawSpecialCard(triggerEffectMinion.position.color, Kind.STRUCTURE);

	     return;
	 }
	

}

package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class VengefulVaettr_Sim extends Simtemplate {
	//"id":292,"name":"Vengeful Vaettr","description":""
	// pillage = dmg + growth+1
	

	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion.owner != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 b.changeMaxRessource(ResourceName.GROWTH, triggerEffectMinion.position.color, 1);
		 b.doDmg(triggerEffectMinion, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
	     return;
	 }
	 
}

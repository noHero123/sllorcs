package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UColor;

public class IroncladReaver_Sim extends Simtemplate {
	//"id":286,"name":"Ironclad Reaver","description":""
	//Pillage: Immune to damage until the beginning of your next turn
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 triggerEffectMinion.imuneToDmg=true;

	     return;
	 }
	 
	 public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	    {
		 	if(triggerEffectMinion.position.color != turnStartColor) return;
		 	 triggerEffectMinion.imuneToDmg=false;
	        return;
	    }

}

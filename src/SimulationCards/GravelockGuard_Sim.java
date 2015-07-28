package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class GravelockGuard_Sim extends Simtemplate {
	//"id":365,"name":"Gravelock Guard","description":""
	// pillage = dmg to random opp unit 
	

	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 ArrayList<Minion> mins = b.getPlayerFieldList(Board.getOpposingColor(triggerEffectMinion.position.color));
		 if(mins.size()==0) return;
		 int random = b.getRandomNumber(0, mins.size()-1);
		 Minion targ = mins.get(random);
		 b.doDmg(targ, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.PHYSICAL);
	     return;
	 }
	 
}

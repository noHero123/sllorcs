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

public class DustRunner_Sim extends Simtemplate {
	//id":205,"name":"Dust Runner","description":"When Dust Runner deals damage to a creature with 3 or less Health, that creature is destroyed."
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion || dmgdone <=0 || damagedMinion.Hp + dmgdone >= 4) return;
		b.destroyMinion(damagedMinion, triggerEffectMinion);
		
        return;
    }
	
	
	
}

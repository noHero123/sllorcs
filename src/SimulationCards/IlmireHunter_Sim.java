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

public class IlmireHunter_Sim extends Simtemplate {
	//"id":196,"name":"Ilmire Hunter","description":"" *slayer
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion || dmgdone <=0) return;
		b.destroyMinion(damagedMinion, triggerEffectMinion);
		
        return;
    }
	
	
	
}

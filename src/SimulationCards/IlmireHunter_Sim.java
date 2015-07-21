package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class IlmireHunter_Sim extends Simtemplate {
	//"id":196,"name":"Ilmire Hunter","description":"" *slayer
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone)
    {
		if(attacker != triggerEffectMinion.owner || dmgdone <=0) return;
		b.destroyMinion(damagedMinion, triggerEffectMinion);
		
        return;
    }
	
	
	
}

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

public class Lifestealer_Sim extends Simtemplate {
	//"id":59,"name":"Lifestealer","description":"When Lifestealer deals damage, it is [healed] by 2."
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone)
    {
		if(attacker != triggerEffectMinion.owner || dmgdone <=0) return;
		attacker.healMinion(2, b);
        return;
    }
	
	
	
}

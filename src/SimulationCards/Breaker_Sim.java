package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Breaker_Sim extends Simtemplate
{
	//"id":212,"name":"Breaker","description":"When Breaker deals damage, it gets +1 Attack and +1 Health."
	
	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion || dmgdone <=0) return;
		triggerEffectMinion.buffMinion(1, 1, 0, b);
        return;
    }
	
	
	
}

package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class CatapultOfGoo_Sim extends Simtemplate {
	//"id":24,"name":"Catapult of Goo","description":"Units attacked by Catapult of Goo have their Countdown increased by 2."

	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		damagedMinion.buffMinion(0, 0, 2, b);
        return;
    }
	
}

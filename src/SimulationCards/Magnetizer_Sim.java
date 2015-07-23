package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Magnetizer_Sim extends Simtemplate {
	//"id":228,"name":"Magnetizer","description":"Any unit hit by Magnetizer gets its Countdown increased by 1."

	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker == triggerEffectMinion)
		{
			damagedMinion.buffMinion(0, 0, 1, b);//TODO with or without buff-message=?
		}
        return;
    }

}

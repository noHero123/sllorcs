package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class SiegeCracker_Sim extends Simtemplate {
	//id":241,"name":"Siege Cracker","description":""  idolstrike 5!
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion || !damagedMinion.isIdol || dmgtype != DamageType.COMBAT) return;
		if(dmgdone<=0) return;
		int dmg = 5;
		b.doDmg(damagedMinion, triggerEffectMinion, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
	
	
}

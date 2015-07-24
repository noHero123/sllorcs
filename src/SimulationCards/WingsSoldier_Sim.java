package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class WingsSoldier_Sim extends Simtemplate {
	//"id":237,"name":"Wings Soldier","description":""  creature-strike 2!
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion || damagedMinion.cardType != Kind.CREATURE || dmgtype != DamageType.COMBAT) return;
		if(dmgdone<=0) return;
		int dmg = 2;
		b.doDmg(damagedMinion, triggerEffectMinion, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
	
	
}

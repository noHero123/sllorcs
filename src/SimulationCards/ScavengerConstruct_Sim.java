package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class ScavengerConstruct_Sim extends Simtemplate {
	//"id":197,"name":"Scavenger Construct","description":"When an adjacent unit is destroyed, Scavenger Construct gets +2 Health."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.cardType == Kind.CREATURE && diedMinion.position.isNeightbour(triggerEffectMinion.position))
		{
			triggerEffectMinion.buffMinion(0, 2, 0, b);
		}
			
        return;
    }

	

}

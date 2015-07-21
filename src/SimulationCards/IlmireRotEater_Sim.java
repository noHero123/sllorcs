package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.subType;

public class IlmireRotEater_Sim extends Simtemplate {
	//"id":195,"name":"Ilmire Rot Eater","description":"When a creature adjacent to Ilmire Rot Eater is destroyed, Ilmire Rot Eater gets +1 Attack and +1 Health."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.cardType == Kind.CREATURE && diedMinion.position.isNeightbour(triggerEffectMinion.position))
		{
			triggerEffectMinion.buffMinion(1, 1, 0, b);
		}
			
        return;
    }

	

}

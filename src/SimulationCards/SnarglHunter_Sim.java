package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.UColor;

public class SnarglHunter_Sim extends Simtemplate {
	//"id":379,"name":"Snargl Hunter","description":"When Snargl Hunter destroys a non-Human creature, increase [current] Energy by 4 at the start of your next turn."

	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion == attacker && !diedMinion.getSubTypes().contains(SubType.Human))
		{
			triggerEffectMinion.turnCounter=1;
		}
        return;
    }
	
	//tested: it is not raised if hunter dies!
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
    {
		if(triggerEffectMinion.position.color == turnStartColor && triggerEffectMinion.turnCounter>=1)
		{
			triggerEffectMinion.turnCounter=0;
			b.changeCurrentRessource(ResourceName.ENERGY, turnStartColor, 4);
		}
        return;
    }

}

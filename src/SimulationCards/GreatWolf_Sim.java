package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.subType;

public class GreatWolf_Sim extends Simtemplate {
	//"id":38,"name":"Great Wolf","description":"Great Wolf gets +1 Attack for each other Wolf you control."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.isRelentless=true;
		int buffs = 0;
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.subtypes.contains(subType.Wolf))
			{
				buffs++;
			}
		}
		if(buffs>=1) own.buffMinion(buffs, 0, 0, b);
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.subtypes.contains(subType.Wolf))
		{
			triggerEffectMinion.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.subtypes.contains(subType.Wolf))
		{
			triggerEffectMinion.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
}

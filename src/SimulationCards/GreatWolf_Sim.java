package SimulationCards;

import BattleStuff.Board;
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
			if(m.card.subtypes.contains(subType.Wolf))
			{
				buffs++;
			}
		}
		if(buffs>=1) own.buffMinion(buffs, 0, 0, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion)
    {
		//unbuff wolf if a wolf dies
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.card.subtypes.contains(subType.Wolf))
		{
			triggerEffectMinion.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
}

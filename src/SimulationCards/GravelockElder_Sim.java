package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class GravelockElder_Sim extends Simtemplate {
	//"id":1
	//Other Gravelocks you control have +1 Health.
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.card.subtypes.contains(subType.Gravelock))
			{
				m.buffMinion(0, 1, 0, b);
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		
		if(summonedMinion.card.subtypes.contains(subType.Gravelock))
		{
			summonedMinion.buffMinion(0, 1, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.card.subtypes.contains(subType.Gravelock))
			{
				m.buffMinion(0, -1, 0, b);
			}
		}
        return;
    }
}

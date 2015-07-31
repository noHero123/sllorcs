package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class FjordsOfVigor_Sim extends Simtemplate {
	//"id":334,"name":"Fjords of Vigor","description":"Units you control costing 3 or less have +1 Attack."
	//linger 4

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 4;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
		
		if(!isNew) return;
		
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			int cost = m.card.costDecay + m.card.costEnergy + m.card.costGrowth + m.card.costOrder;
			if(m.getAc()>=0 && cost <=3)
			{
				m.buffMinion(1, 0, 0, b);
			}
		}
		
		
	    return;
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
		 	if(triggerEffectMinion.position.color == summonedMinion.position.color)
		 	{
		 		int cost = summonedMinion.card.costDecay + summonedMinion.card.costEnergy + summonedMinion.card.costGrowth + summonedMinion.card.costOrder;
				if(summonedMinion.getAc()>=0 && cost <=3)
				{
					summonedMinion.buffMinion(1, 0, 0, b);
				}
		 	}
	        return;
	    }
	 
	 public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
	    {
			for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
			{
				int cost = m.card.costDecay + m.card.costEnergy + m.card.costGrowth + m.card.costOrder;
				if(m.getAc()>=0 && cost <=3)
				{
					m.buffMinion(-1, 0, 0, b);
				}
			}
	        return;
	    }
	 

}

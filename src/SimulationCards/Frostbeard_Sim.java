package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class Frostbeard_Sim extends Simtemplate {
	//id":91,"name":"Frostbeard","description":"When Frostbeard is destroyed during your opponent's turn, units you control get +2 Attack until end of your next turn."

	
	public  void onDeathrattle(Board b, Minion m)
    {
		
		if(b.activePlayerColor == m.position.color) return; //opponent turn!
		
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			if(mnn.getAc()>=0)
			{
				mnn.buffMinionWithoutMessage(2, 0, 0, b);
				mnn.addnewEnchantments("BUFF", "Frostbeard", m.card.cardDescription, m.card, b, m.position.color);
				mnn.frostbeardCounter = 1;
			}
		}
		
        return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		if(turnEndColor != triggerEffectMinion.owner.position.color) return false;
		
		if(triggerEffectMinion.owner.frostbeardCounter == 0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
			return true;//buff is removed, so we return true
		}
		else
		{
			triggerEffectMinion.owner.frostbeardCounter-=1;
			return false;
		}
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
}

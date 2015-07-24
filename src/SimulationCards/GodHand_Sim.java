package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class GodHand_Sim extends Simtemplate {
	//"id":117,"name":"God Hand","description":"Units you control get +2 Attack until end of turn, and have their Countdown decreased by 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0)
			{
				m.buffMinionWithoutMessage(2, 0, -2, b);
				m.addnewEnchantments("BUFF", "God Hand", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
        return;
    }
	
	
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		//if()
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}
        return true;//buff is removed, so we return true
    }
}

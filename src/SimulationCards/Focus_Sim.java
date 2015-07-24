package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Focus_Sim extends Simtemplate {
	//"id":71,"name":"Focus","description":"Target creature gets +3 Attack until end of turn."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(3, 0, 0, b);
		target.addnewEnchantments("BUFF", "Focus", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
	
        return;
    }
	
	
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		//if()
		triggerEffectMinion.owner.buffMinionWithoutMessage(-3, 0, 0, b);
        return true;//buff is removed, so we return true
    }
}

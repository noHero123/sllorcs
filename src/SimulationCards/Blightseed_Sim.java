package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Blightseed_Sim extends Simtemplate {
	//"id":264,"name":"Blightseed","description":"Target creature you control has its Countdown increased by 2. If it is still on the board at the beginning of your next turn, draw 2 scrolls and increase [current] Decay by 2."


	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 0, 2, b);
		target.addnewEnchantments("BUFF", "Blightseed", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
    {
		if(turnStartColor != triggerEffectMinion.position.color) return;
		b.drawCards(triggerEffectMinion.position.color, 2);
		b.changeCurrentRessource(ResourceName.DECAY, triggerEffectMinion.position.color, 2);
		triggerEffectMinion.owner.removeEnchantment(triggerEffectMinion, true, b);
        return;
    }

}

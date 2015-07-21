package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class RoastedBeanPotion_Sim extends Simtemplate 
{

	//"id":193,"name":"Roasted Bean Potion","description":"Target creature's Countdown is decreased by 1. Its [Move] is increased by 1 until end of turn."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 0, -1, b);//status update is done in add card as enchantment
		target.moveChanges+=1;
		target.addnewEnchantments("BUFF", "Roasted Bean Potion", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		//if()
		triggerEffectMinion.owner.moveChanges-=1;
        return true;//buff is removed, so we return true
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class MachinationMindset_Sim extends Simtemplate 
{

	//""id":148,"name":"Machination Mindset","description":"Enchanted melee creature gets [Relentless] and +1 Attack."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_melees;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(1, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Machination Mindset", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
}

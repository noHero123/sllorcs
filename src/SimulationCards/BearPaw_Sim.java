package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class BearPaw_Sim extends Simtemplate 
{

	//"id":16,"name":"Bear Paw","description":"Enchanted unit gets +2 Attack and +2 Health. When Bear Paw comes into play, enchanted unit's Countdown is increased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(2, 2, 1, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Bear Paw", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}

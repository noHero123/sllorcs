package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class OakBlood_Sim extends Simtemplate 
{

	//"id":213,"name":"Oak Blood","description":"Enchanted creature gets +6 Health."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 6, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Oak Blood", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}

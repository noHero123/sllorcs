package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Fungify_Sim extends Simtemplate 
{

	//"id":248,"name":"Fungify","description":"Enchanted creature gets +5 Health, and its [base Countdown] is increased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(0, 5, 0, b);//status update is done in add card as enchantment
		target.maxAc +=1;
		target.addCardAsEnchantment("ENCHANTMENT", "Fungify", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}
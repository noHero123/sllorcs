package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Fleetness_Sim extends Simtemplate 
{

	//"id":234,"name":"Fleetness","description":"Enchanted unit's [base Countdown] is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		if(target.maxAc>=1)
			{
			target.maxAc--;
			}
			
		target.addCardAsEnchantment("ENCHANTMENT", "Fleetness", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}

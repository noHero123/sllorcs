package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Untainted_Sim extends Simtemplate 
{

	//"id":257,"name":"Untainted","description":"Enchanted unit has [Ward]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Untainted", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	 public boolean hasWard(Board b ,Minion m)
	    {
		 	if(m.owner==null) return false;
	    	return true;
	    }
	
}

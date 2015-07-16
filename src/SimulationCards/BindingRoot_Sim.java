package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class BindingRoot_Sim extends Simtemplate
{
	
	//binding root selects all units as possible targets
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.moveChanges-=2;
		target.addCardAsEnchantment("ENCHANTMENT", "Binding Root", playedCard.card.cardDescription, playedCard, b);
        
        return;
    }
	
}

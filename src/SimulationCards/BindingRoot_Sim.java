package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class BindingRoot_Sim extends Simtemplate
{
	
	//binding root selects all units as possible targets
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.moveChanges-=2;
		target.addCardAsEnchantment("ENCHANTMENT", "Binding Root", playedCard.card.cardDescription, playedCard, b);
        
        return;
    }
	
}

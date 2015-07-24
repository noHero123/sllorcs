package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class RangersBane_Sim extends Simtemplate
{
	//"id":100,"name":"Ranger's Bane","description":"Enchanted creature becomes [poisoned]."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Poison", "Unit takes 1 damage at the beginning of owner's turn.", playedCard, b);
        return;
    }
	
	
}

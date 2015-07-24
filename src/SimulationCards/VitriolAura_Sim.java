package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class VitriolAura_Sim extends Simtemplate
{
	
	//"id":88,"name":"Vitriol Aura","description":"",
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public boolean isPoisonous(Board b ,Minion m)
    {
    	return true;
    }
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Vitriol Aura", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
}

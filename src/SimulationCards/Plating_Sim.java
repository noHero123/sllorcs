package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Plating_Sim extends Simtemplate 
{

	//"id":141,"name":"Plating","description":"Enchanted unit disregards next attack or damage taken. Plating is removed afterwards."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.imuneToNextDmg=true;
		target.addCardAsEnchantment("ENCHANTMENT", "Plating", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
    {
		if(!damagedMinion.imuneToNextDmg)
		{
			//remove effect
			damagedMinion.removeEnchantment(triggerEffectMinion, true, b);
		}
        return;
    }
	
}

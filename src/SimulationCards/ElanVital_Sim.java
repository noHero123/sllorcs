package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ElanVital_Sim extends Simtemplate
{
	//"id":13,"name":"Elan Vital","description":""

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Elan Vital", "", playedCard, b);
        return;
    }
	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	{
		if(turnStartColor == triggerEffectMinion.position.color)
		{
			triggerEffectMinion.owner.healMinion(2,b);
		}
	}
	
}

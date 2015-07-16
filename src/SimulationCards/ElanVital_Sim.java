package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class ElanVital_Sim extends Simtemplate
{
	//"id":13,"name":"Elan Vital","description":""

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Elan Vital", "", playedCard, b);
        return;
    }
	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
	{
		if(turnStartColor == triggerEffectMinion.position.color)
		{
			triggerEffectMinion.owner.healMinion(2,b);
		}
	}
	
}

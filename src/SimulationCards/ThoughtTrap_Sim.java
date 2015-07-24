package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ThoughtTrap_Sim extends Simtemplate 
{

	//"id":20,"name":"Thought Trap","description":"Target unit's Countdown is doubled."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int ac = target.getAc();
		target.buffMinion(0, 0, ac, b);//status update is done in add card as enchantment
        return;
    }
	
}

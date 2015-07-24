package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class BlessingOfHaste_Sim extends Simtemplate 
{

	//"id":64,"name":"Blessing of Haste","description":"Target unit's Countdown is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinion(0, 0, -1, b);//status update is done in add card as enchantment
        return;
    }
	
}

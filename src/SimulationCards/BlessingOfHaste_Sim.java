package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class BlessingOfHaste_Sim extends Simtemplate 
{

	//"id":64,"name":"Blessing of Haste","description":"Target unit's Countdown is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinion(0, 0, -1, b);//status update is done in add card as enchantment
        return;
    }
	
}

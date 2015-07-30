package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class Callback_Sim extends Simtemplate 
{

	//"id":23,"name":"Callback","description":"Target unit you control is returned to your hand, and your [current] Order is increased by the unit's cost."
	//TODO cant target TOKENS!
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		//replenish
		Minion target = b.getMinionOnPosition(targets.get(0));
		int x = target.card.costGrowth + target.card.costOrder + target.card.costEnergy + target.card.costDecay;
		b.changeCurrentRessource(ResourceName.ORDER, playedCard.position.color, x);
		
		b.removeMinionToHand(target);
        return;
    }
	
}

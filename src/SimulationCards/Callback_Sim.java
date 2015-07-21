package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Callback_Sim extends Simtemplate 
{

	//"id":23,"name":"Callback","description":"Target unit you control is returned to your hand, and your [current] Order is increased by the unit's cost."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		//replenish
		int[] cure = b.whitecurrentRessources;
		if(playedCard.position.color == Color.black) cure = b.blackcurrentRessources;
		Minion target = b.getMinionOnPosition(targets.get(0));
		int x = target.card.costGrowth + target.card.costOrder + target.card.costEnergy + target.card.costDecay;
		
		cure[1]+=x;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		
		b.removeMinionToHand(target);
        return;
    }
	
}

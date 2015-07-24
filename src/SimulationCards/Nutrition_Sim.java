package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Nutrition_Sim extends Simtemplate {
	//"id":94,"name":"Nutrition","description":"Sacrifice target unit you control and add that unit's cost to your [current] resources."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		
		if(target.card.costGrowth >=0) b.changeCurrentRessource(ResourceName.GROWTH, playedCard.position.color, target.card.costGrowth);
		if(target.card.costOrder >=0) b.changeCurrentRessource(ResourceName.ORDER, playedCard.position.color, target.card.costOrder);
		if(target.card.costEnergy >=0) b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, target.card.costEnergy);
		if(target.card.costDecay >=0) b.changeCurrentRessource(ResourceName.DECAY, playedCard.position.color, target.card.costDecay);
		
        return;
    }
	
	
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class Nutrition_Sim extends Simtemplate {
	//"id":94,"name":"Nutrition","description":"Sacrifice target unit you control and add that unit's cost to your [current] resources."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		
		int[] curress = b.whitecurrentRessources;
		if(playedCard.position.color == Color.black)
		{
			curress= b.blackcurrentRessources;
		}
		curress[0]+=target.card.costGrowth;
		curress[1]+=target.card.costOrder;
		curress[2]+=target.card.costEnergy;
		curress[3]+=target.card.costDecay;
		//send ressourceupdates message
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		
        return;
    }
	
	
	
}

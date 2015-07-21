package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Metempsychosis_Sim extends Simtemplate
{
	//"id":19,"name":"Metempsychosis","description":"Target unit you control is resummoned on the same tile. Its Countdown is set to its previous value."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		//delete unit
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		ArrayList<Minion> grave = b.getPlayerGrave(target.position.color);
		for(Minion m : target.attachedCards)
		{
			m.card.cardSim.onDeathrattle(b, m);
			if(m.cardID>=0)grave.add(m);
		}
		target.attachedCards.clear();
		b.getPlayerField(target.position.color)[target.position.row][target.position.column] = null;
		
		//resummon
		b.summonUnitOnPosition(target.position, target, false);
		
        return;
    }
	
	
	
}

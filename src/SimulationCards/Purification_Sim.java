package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Purification_Sim extends Simtemplate
{
	//"id":99,"name":"Purification","description":"Remove all enchantments and [effects] from target unit."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {

		Minion target = b.getMinionOnPosition(targets.get(0));
		ArrayList<Minion> grave = b.getPlayerGrave(target.position.color);
		for(Minion m : target.attachedCards)
		{
			m.card.cardSim.onDeathrattle(b, m);
			if(m.cardID>=0)grave.add(m);
		}
		target.attachedCards.clear();

        return;
    }
	
	
	
}

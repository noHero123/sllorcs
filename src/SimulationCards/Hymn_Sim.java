package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Hymn_Sim extends Simtemplate
{
	

	//"id":18,"name":"Hymn","description":"Units on target tile and adjacent tiles are healed by 3."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		if(targets.size() == 0) return;
		
		ArrayList<Position> t = new ArrayList<Position>(targets);
		Position p = targets.get(0);
		t.addAll(p.getNeightbours());
		
		ArrayList<Minion> ms = b.getMinionsFromPositions(t);
		
		for(Minion m : ms)
		{
			m.healMinion(1, b);
		}
		
		
        return;
    }
	
	
	
}

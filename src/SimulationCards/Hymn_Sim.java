package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Hymn_Sim extends Simtemplate
{
	

	//"id":18,"name":"Hymn","description":"Units on target tile and adjacent tiles are healed by 3."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		if(targets.size() == 0) return;
		
		ArrayList<UPosition> t = new ArrayList<UPosition>(targets);
		UPosition p = targets.get(0);
		t.addAll(p.getNeightbours());
		
		ArrayList<Minion> ms = b.getMinionsFromPositions(t);
		
		for(Minion m : ms)
		{
			m.healMinion(1, b);
		}
		
		
        return;
    }
	
	
	
}

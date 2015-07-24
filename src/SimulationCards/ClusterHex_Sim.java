package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class ClusterHex_Sim extends Simtemplate {
	//"id":199,"name":"Cluster Hex","description":"All units on target tile and adjacent tiles get [Curse] 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all; 
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//add neighbours and position itself
		ArrayList<UPosition> posses = targets.get(0).getNeightbours();
		posses.add(targets.get(0));
		
		ArrayList<Minion> addjacent = new ArrayList<Minion>(b.getMinionsFromPositions(posses));
		
		for(Minion target : addjacent)
		{
			target.addnewCurse(b, playedCard.position.color, 2);
		}
		
		
		
        return;
    }
	
}

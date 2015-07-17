package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class Bombard_Sim extends Simtemplate {
	//"id":152,"name":"Bombard","description":"Ranged or Lobber units on target tile and adjacent tiles have their Countdown decreased by 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all; 
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//add neighbours and position itself
		ArrayList<Position> posses = targets.get(0).getNeightbours();
		posses.add(targets.get(0));
		
		ArrayList<Minion> addjacent = new ArrayList<Minion>(b.getMinionsFromPositions(posses));
		
		for(Minion target : addjacent)
		{
			if(target.attackType == AttackType.RANGED || target.attackType == AttackType.BALLISTIC)
			{
				target.buffMinion(0, 0, -2, b);
			}
		}
		
		
		
        return;
    }
	
}

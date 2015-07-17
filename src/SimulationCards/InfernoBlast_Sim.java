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

public class InfernoBlast_Sim extends Simtemplate {
	//"id":35,"name":"Inferno Blast","description":"Deal 1 [magic damage] to target tile and adjacent tiles."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all;
	}
	
	//TODO targettiles!
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Position> posses = targets.get(0).getNeightbours();
		posses.add(targets.get(0));
		
		ArrayList<Minion> addjacent = new ArrayList<Minion>(b.getMinionsFromPositions(posses));
		
		if(addjacent.size()>=1)
		{
			b.doDmg(addjacent, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);//dmg=-100 => aoedmg :D
		}
		
        return;
    }
	
}

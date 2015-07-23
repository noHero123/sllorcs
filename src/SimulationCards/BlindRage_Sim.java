package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class BlindRage_Sim extends Simtemplate {
	//"id":147,"name":"Blind Rage","description":"Target creature deals [magic damage] equal to its Countdown to adjacent units. Its Countdown is then increased by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		ArrayList<Minion> addjacent = new ArrayList<Minion>(b.getMinionsFromPositions(target.position.getNeightbours()));
		
		b.doDmg(addjacent, target, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
		target.buffMinion(0, 0, 1, b);
		
        return;
    }
	
}

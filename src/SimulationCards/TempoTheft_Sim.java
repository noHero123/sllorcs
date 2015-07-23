package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class TempoTheft_Sim extends Simtemplate
{
	
	//"id":178,"name":"Tempo Theft","description":"Two target units switch Countdown values."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.all_units_with_ac;
	}

	//TODO can play tempotheft on same unit?
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		Minion target2 = b.getMinionOnPosition(targets.get(1));
        
		if(target==target2) return;
		
		int t1ac = target.getAc();
		int t2ac = target2.getAc();
		
		if(t1ac == t2ac) return;
		
		target.buffMinion(0, 0, t2ac-t1ac, b);
		target2.buffMinion(0, 0, t1ac-t2ac, b);
		
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Flip_Sim extends Simtemplate
{
	
	//"id":36,"name":"Flip","description":"Move target opponent unit to another tile on the same side."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.opp_units;
	}
	
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.opp_free;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.unitChangesPlace(target.position, targets.get(1));
        
        return;
    }
	
}

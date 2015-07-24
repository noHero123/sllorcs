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

public class CursedPresence_Sim extends Simtemplate {
	//"id":276,"name":"Cursed Presence","description":"Target unit gets [Curse] 3."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units; 
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addnewCurse(b, playedCard.position.color, 3);
		
		
		
        return;
    }
	
}

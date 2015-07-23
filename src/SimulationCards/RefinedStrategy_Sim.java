package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class RefinedStrategy_Sim extends Simtemplate
{
	
	//"id":240,"name":"Refined Strategy","description":"Move target structure you control to another tile on that side. Its Countdown is decreased by 1."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_structures;
	}
	
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.own_free;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		String s = "{\"TeleportUnits\":{\"units\":[{\"from\":"+targets.get(0).posToString()+",\"to\":"+targets.get(1).posToString()+"}]}}";
		b.addMessageToBothPlayers(s);
		b.unitChangesPlace(target.position, targets.get(1), true, false);
		target.buffMinion(0, 0, -1, b);
		
        
        return;
    }
	
}

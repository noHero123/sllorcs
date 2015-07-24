package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Infiltrate_Sim extends Simtemplate
{
	
	//"id":312,"name":"Infiltrate","description":"Target unit's Countdown is increased by 2. Draw 1 scroll."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		target.buffMinion(0, 0, 2, b);
		b.drawCards(playedCard.position.color, 1);
        
        return;
    }
	
}

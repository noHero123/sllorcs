package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class TickBomb_Sim extends Simtemplate
{
	
	//"id":87,"name":"Tick Bomb","description":"Destroy target structure."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_structures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.destroyMinion(target, playedCard);
        
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class RekindledSpirit_Sim extends Simtemplate {
	//"id":348,"name":"Rekindled Spirit","description":"Sacrifice target creature you control. Destroy all [Lingering] spells. Draw 2 creature scrolls."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		
		
		ArrayList<Minion> rules = b.getAllRules();
		for(Minion rule : rules)
		{
			b.ruleCountDown(rule, 1000);
		}
		
		b.drawSpecialCard(playedCard.position.color, Kind.CREATURE);
		b.drawSpecialCard(playedCard.position.color, Kind.CREATURE);
		
        return;
    }
	
	
	
}

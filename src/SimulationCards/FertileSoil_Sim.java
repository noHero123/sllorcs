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

public class FertileSoil_Sim extends Simtemplate {
	//"id":29,"name":"Fertile Soil","description":"Sacrifice target creature you control and draw 3 scrolls."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		b.drawCards(playedCard.position.color, 3);
		
        return;
    }
	
}

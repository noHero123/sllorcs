package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Kabonk_Sim extends Simtemplate
{
	
	//"id":50,"name":"Kabonk","description":"Deal 1 [magic damage] to target unit. Draw 1 scroll."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		b.drawCards(playedCard.position.color, 1);
        return;
    }
	
}

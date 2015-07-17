package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Burn_Sim extends Simtemplate
{
	
	//"id":27,"name":"Burn","description":"Deal 3 [magic damage] to target unit. If that unit is destroyed, draw 1 scroll."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, playedCard, 3, AttackType.UNDEFINED, DamageType.MAGICAL);
		if(target.Hp<=0)
		{
			b.drawCards(playedCard.position.color, 1);
		}
        
        return;
    }
	
}

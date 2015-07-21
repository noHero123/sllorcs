package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class FaithBlessing_Sim extends Simtemplate
{
	
	//"id":93,"name":"Faith Blessing","description":"Target unit you control takes 2 [magic damage]. Your opponent's idol on that row is dealt 2 damage, and your idol is healed by 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Color oppcol = Board.getOpposingColor(playedCard.position.color);
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, playedCard, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		b.doDmg(b.getPlayerIdols(oppcol).get(target.position.row), playedCard, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		b.getPlayerIdols(playedCard.position.color).get(target.position.row).healMinion(2, b);
        return;
    }
	
}

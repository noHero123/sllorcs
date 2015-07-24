package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ViolentDispersal_Sim extends Simtemplate
{
	
	//"id":70,"name":"Violent Dispersal","description":"Deal 8 [magic damage] to target unit."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.doDmg(target, playedCard, 8, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
}

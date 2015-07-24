package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class Darkstrike_Sim extends Simtemplate
{
	
	//"id":133,"name":"Darkstrike","description":"Deal 2 [magic damage] to target unit. If that unit is destroyed, increase Decay by 1."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, playedCard, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		//TODO test with onUnitDied trigger? (and burn too)
		if(target.Hp<=0)
		{
			b.changeMaxRessource(ResourceName.DECAY, playedCard.position.color, 1);
		}
        
        return;
    }
	
}

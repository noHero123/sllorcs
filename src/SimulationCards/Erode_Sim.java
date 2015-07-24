package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Erode_Sim extends Simtemplate {
	//"id":135,"name":"Erode","description":"Target structure is dealt 3 [magic damage]. Increase Decay by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_structures;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.doDmg(target, playedCard, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		b.changeMaxRessource(ResourceName.DECAY, playedCard.position.color, 1);
        return;
    }
	
	
	
}

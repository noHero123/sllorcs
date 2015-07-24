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

public class VigorExtraction_Sim extends Simtemplate
{
	
	//"id":238,"name":"Vigor Extraction","description":"Target unit's Countdown is increased by 2. If Countdown is then 4 or more, increase Decay by 1."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		target.buffMinion(0, 0, 2, b);
		if(target.getAc()>=4)
		{
			b.changeMaxRessource(ResourceName.DECAY, playedCard.position.color, 1);
		}
        
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class PowerTrip_Sim extends Simtemplate
{
	
	//"id":282,"name":"Power Trip","description":"Increase [current] Energy by 6. If it is then 11 or more, draw 1 scroll."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, 6);
		if(b.getCurrentRessource(ResourceName.ENERGY, playedCard.position.color) >= 11)
		{
			b.drawCards(playedCard.position.color, 1);
		}
        
        return;
    }
	
}

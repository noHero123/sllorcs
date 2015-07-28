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

public class GraveGruel_Sim extends Simtemplate
{
	
	//"id":368,"name":"Grave Gruel","description":""
	//"Surge: rause current wild by x"
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	//TODO is sudden eruption really that random?
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		int surge = b.getCurrentRessource(ResourceName.ENERGY, playedCard.position.color);
		b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, -surge);
		
		b.changeCurrentRessource(ResourceName.WILD, playedCard.position.color, surge);
		
        
        return;
    }
	
}

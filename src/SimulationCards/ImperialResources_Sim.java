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

public class ImperialResources_Sim extends Simtemplate {
	//"id":157,"name":"Imperial Resources","description":"Draw 2 scrolls. Your idols are [healed] by 1. Increase Order by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None; 
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//add neighbours and position itself
		
		b.drawCards(playedCard.position.color, 2);
		
		
		for(Minion m: b.getPlayerIdols(playedCard.position.color))
		{
			m.healMinion(1, b);
		}
		
		b.changeMaxRessource(ResourceName.ORDER, playedCard.position.color, 1);
		
		
        return;
    }
	
}

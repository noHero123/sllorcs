package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class ImperialResources_Sim extends Simtemplate {
	//"id":157,"name":"Imperial Resources","description":"Draw 2 scrolls. Your idols are [healed] by 1. Increase Order by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None; 
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//add neighbours and position itself
		
		b.drawCards(playedCard.position.color, 2);
		
		
		for(Minion m: b.getPlayerIdols(playedCard.position.color))
		{
			m.healMinion(1, b);
		}
		
		int[] maxE = b.whiteRessources;
		
		if(playedCard.position.color == Color.black) 
		{
			maxE = b.blackRessources;
		}
		
		//curE[1] +=1;//raises only maxRessources...
		maxE[1] +=1;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		
		
        return;
    }
	
}

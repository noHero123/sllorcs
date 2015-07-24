package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Eclipse_Sim extends Simtemplate {
	//"id":260,"name":"Eclipse","description":"All your [current] resources are converted into Decay."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		//we are doing it the ugly way :D
		int[] curr = b.whitecurrentRessources;
		if(playedCard.position.color==UColor.black)
		{
			curr = b.blackcurrentRessources;
		}
		
		int sum = curr[0] + curr[1] + curr[2] +curr[3] +curr[4];
		curr[0] = 0;
		curr[1] = 0;
		curr[2] = 0;
		curr[3] = sum;
		curr[4] = 0;
		
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		
        return;
    }
	
	
}

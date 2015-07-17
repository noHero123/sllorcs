package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class StonePactMemorial_Sim extends Simtemplate {
	//"id":113,"name":"Stone Pact Memorial","description":"When Stone Pact Memorial comes into play, increase Order by 1."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int[] curE = b.whitecurrentRessources;
		int[] maxE = b.whiteRessources;
		if(own.position.color == Color.black) 
			{
			curE = b.blackcurrentRessources;
			maxE = b.blackRessources;
			}
		
		//curE[1] +=1;//raises only maxRessources...
		maxE[1] +=1;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
        return;
    }
	
	
}

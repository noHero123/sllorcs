package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class SandPactMemorial_Sim extends Simtemplate {
	//"id":112,"name":"Sand Pact Memorial","description":"When Sand Pact Memorial comes into play, increase [current] Energy by 5."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int[] curE = b.whitecurrentRessources;
		if(own.position.color == Color.black) curE = b.blackcurrentRessources;
		
		curE[2] +=5;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
        return;
    }
	
	
}

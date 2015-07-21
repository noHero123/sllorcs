package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.subType;

public class DesertMemorial_Sim extends Simtemplate {
	//"id":109,"name":"Desert Memorial","description":"When Desert Memorial is destroyed, increase Energy by 1.",
	public  void onDeathrattle(Board b, Minion m)
    {
		int[] curE = b.whiteRessources;
		if(m.position.color == Color.black) curE = b.blackRessources;
		
		curE[2] +=1;
		b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
        return;
    }
	
	
}

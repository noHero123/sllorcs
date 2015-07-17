package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.subType;

public class LawMemorial_Sim extends Simtemplate {
	//"id":108,"name":"Law Memorial","description":"When Law Memorial's Countdown becomes 0, it's destroyed and Order is increased by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	
	public void onCountdownReachesZero(Board b , Minion m)
    {

		b.destroyMinion(m, m);
		int[] curE = b.whitecurrentRessources;
		int[] maxE = b.whiteRessources;
		if(m.position.color == Color.black) 
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

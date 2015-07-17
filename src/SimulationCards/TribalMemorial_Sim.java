package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class TribalMemorial_Sim extends Simtemplate {
	//"id":110,"name":"Tribal Memorial","description":"Pay 2 Energy to destroy Tribal Memorial and increase Growth by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
		int[] curE = b.whitecurrentRessources;
		int[] maxE = b.whiteRessources;
		if(triggerEffectMinion.position.color == Color.black) 
		{
			curE = b.blackcurrentRessources;
			maxE = b.blackRessources;
		}
		
		//curE[0] +=1;//raises only maxRessources...
		maxE[0] +=1;
		
		//dont need a ressource update message, because this costs 2 energy-> is send in activeability.payenergy
		
    	return;
		
    }
	
	
	
	
	
}

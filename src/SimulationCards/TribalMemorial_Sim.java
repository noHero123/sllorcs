package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class TribalMemorial_Sim extends Simtemplate {
	//"id":110,"name":"Tribal Memorial","description":"Pay 2 Energy to destroy Tribal Memorial and increase Growth by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
		b.changeMaxRessource(ResourceName.GROWTH, triggerEffectMinion.position.color, 1);
		
		//dont need a ressource update message, because this costs 2 energy-> is send in activeability.payenergy
		
    	return;
		
    }
	
	
	
	
	
}

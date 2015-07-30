package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Arbalestier_Sim extends Simtemplate {
	//"id":357,"name":"Arbalestier","description":""
	//resonance: countdown decreased
	//ranged + piercing
	
	 public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
	    {
		 	if(triggerEffectMinion.position.color == spell.position.color)
		 	{
		 		triggerEffectMinion.buffMinion(0, 0, -1, b);
		 	}
	    	return;
	    }

	    public boolean hasPiercing(Board b ,Minion m)
	    {
	    	return true;
	    }

}

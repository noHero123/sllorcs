package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Snargl_Sim extends Simtemplate {
	//"id":367,"name":"Snargl","description":""
	//resonance: countdown increased! (+1) 
	
	 public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
	    {
		 	if(triggerEffectMinion.position.color == spell.position.color)
		 	{
		 		triggerEffectMinion.buffMinion(0, 0, 1, b);
		 	}
	    	return;
	    }

	

}

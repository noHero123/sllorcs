package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Replicaton_Sim extends Simtemplate {
	//"id":287,"name":"Replicaton","description":"When Replicaton is destroyed, return to owner's hand if your [current] Energy is 2 or more."

	
	 public  void getBattlecryEffect(Board b, Minion own, Minion target)
	    {
		 	//own.addToHandAfterDead=true;//we have to check the energy...(done in doDeathrattles2
	        return;
	    }
	

}

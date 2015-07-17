package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class CopperAutomaton_Sim extends Simtemplate {
	//"id":66,"name":"Copper Automaton","description":"Copper Automaton is destroyed after attacking."
	
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		if(m.Ap != 0 || m!=self)
        {
			return;
        }
		
		b.destroyMinion(m, m);
		
    	return;
    }

}

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
		if(m.getAc() != 0 || m!=self)
        {
			//System.out.println("ap " + m.getAc() + " " );
			return;
        }
		
		b.destroyMinion(m, m);
		
    	return;
    }

}

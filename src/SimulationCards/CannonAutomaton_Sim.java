package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class CannonAutomaton_Sim extends Simtemplate {
	//"id":68,"name":"Cannon Automaton","description":"" armor + ranged
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
        return;
    }
	
	public int getArmor(Board b ,Minion m)
    {
    	return 1;
    }
	
}

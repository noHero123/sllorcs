package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class CannonAutomaton_Sim extends Simtemplate {
	//"id":68,"name":"Cannon Automaton","description":"" armor + ranged
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.armor+=1;
        return;
    }
	
}

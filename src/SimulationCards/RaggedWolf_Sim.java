package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class RaggedWolf_Sim extends Simtemplate {
	//id":49,"name":"Ragged Wolf","description":""
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.buffMinion(0, 0, -1000, b);//haste
        return;
    }
	
}

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class AgingKnight_Sim extends Simtemplate {
	//"id":232,"name":"Aging Knight","description":"Each time Aging Knight attacks, its [base Countdown] is increased by 1."

	public void onAttackDone(Board b , Minion m, Minion self)
    {
    	if(m==self)
    	{
    		m.maxAc++;
    	}
    	return;
    }

}

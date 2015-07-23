package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class ClockLibrary_Sim extends Simtemplate {
	//"id":153,"name":"Clock Library","description":"When Clock Library's Countdown becomes 0, it's destroyed and you draw 3 scrolls."
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		b.destroyMinion(m, m);
		b.drawCards(m.position.color, 3);
    	return;
    }
	
	
	
}

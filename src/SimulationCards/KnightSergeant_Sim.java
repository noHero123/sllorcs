package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class KnightSergeant_Sim extends Simtemplate {
	//"id":355,"name":"Knight Sergeant","description":"When Knight Sergeant's Countdown becomes 0, adjacent Knights have their Countdown decreased by 1."
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {

    	return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		for(Minion mnn : b.getMinionsFromPositions(m.position.getNeightbours()))
		{
			if(mnn.getSubTypes().contains(SubType.Knight)) mnn.buffMinion(0, 0, -1, b);
		}
    	return;
    }
	
	
	
}

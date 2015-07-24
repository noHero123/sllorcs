package SimulationCards;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class HonorableGeneral_Sim extends Simtemplate {
	//"id":74,"name":"Honorable General","description":"When Honorable General's Countdown becomes 0, adjacent units have their Countdown decreased by 2."
	
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
    	return;
    }
	

	public void onCountdownReachesZero(Board b , Minion m)
    {
		for( Minion mnn : b.getMinionsFromPositions(m.position.getNeightbours()) )
		{
			if(mnn.getAc()>=0)
			{
				mnn.buffMinionWithoutMessage(0, 0, -2, b);
			}
		}
    	return;
    }

	
}

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class FleshAnimator_Sim extends Simtemplate {
	//"id":352,"name":"Flesh Animator","description":"When Flesh Animator's Countdown becomes 0, Undead creatures you control have their Countdown decreased by 1."
	

	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			if(mnn.cardType == Kind.CREATURE && mnn.getSubTypes().contains(SubType.Undead))
			{
				mnn.buffMinion(0, 0, -1, b);
			}
		}
    	return;
    }
	
	
	
}

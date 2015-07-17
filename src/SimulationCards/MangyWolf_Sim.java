package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class MangyWolf_Sim extends Simtemplate {
	//"id":44,"name":"Mangy Wolf","description":"When Mangy Wolf comes into play, other Wolf creatures you control have their Countdown decreased by 1."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.subtypes.contains(subType.Wolf))
			{
				m.buffMinion(0, 0, -1, b);
			}
		}
        return;
    }
	
	
}

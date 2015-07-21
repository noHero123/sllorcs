package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class Mangonel_Sim extends Simtemplate {
	//"id":155,"name":"Mangonel","description":"When Mangonel comes into play, your Mangonels' Countdowns are\\nset to the lowest of any you control."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		int min = 3;
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId==155)
			{
				int minm = m.getAc();
				if(minm<min)
				{
					min=minm;
				}
			}
		}
		
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId==155)
			{
				int curac = m.getAc();
				if(curac > min)
				{
					m.buffMinion(0, 0, min - curac, b);
				}
			}
		}
		
        return;
    }
	
	
}

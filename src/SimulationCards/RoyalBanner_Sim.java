package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class RoyalBanner_Sim extends Simtemplate {
	//"id":233,"name":"Royal Banner","description":"All non-Human units get Countdown increased by 2 after attacking."

	public void onAttackDone(Board b , Minion m, Minion self)
    {
    	if(m!=self)
    	{
    		if(!m.getSubTypes().contains(SubType.Human))
    		{
    			m.buffMinion(0, 0, 2, b);
    		}
    	}
    	return;
    }

}

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class IronOgre_Sim extends Simtemplate {
	//"id":39,"name":"Iron Ogre","description":"" + relentless + armor1
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
	public int getArmor(Board b ,Minion m)
    {
    	return 1;
    }
}

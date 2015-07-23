package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class VitalityWell_Sim extends Simtemplate {
	//"id":63,"name":"Vitality Well","description":"When Vitality Well's Countdown becomes 0, units you control are [healed] by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		//has haste
		own.buffMinion(0, 0, -1000, b);
		/*for(Minion mnn : b.getPlayerFieldList(own.position.color))
		{
			mnn.healMinion(1, b);
		}*/
		
    	return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			mnn.healMinion(1, b);
		}
    	return;
    }
	
	
	
}

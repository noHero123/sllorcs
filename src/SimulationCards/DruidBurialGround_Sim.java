package SimulationCards;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class DruidBurialGround_Sim extends Simtemplate {
	//"id":33,"name":"Druid Burial Ground","description":"At the beginning of your turn, adjacent units are [healed] by 1."
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			mnn.healMinion(1, b);
		}
    	return;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	{
		//TODO aoeHeal?
		for(Minion mnn : b.getMinionsFromPositions(triggerEffectMinion.position.getNeightbours()))
		{
			mnn.healMinion(1, b);
		}
    	return;
	}
	
}

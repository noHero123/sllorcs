package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class BannerOfOrdinance_Sim extends Simtemplate {
	//"id":346,"name":"Banner of Ordinance","description":"When Countdown is 0, you may sacrifice Banner of Ordinance to destroy all [Lingering] spells and [heal] adjacent units by 1."
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		for(Minion rule : b.getAllRules())
		{
			b.ruleCountDown(rule, rule.lingerDuration+1);
		}
		
		for(Minion mnn : b.getMinionsFromPositions(triggerEffectMinion.position.getNeightbours()))
		{
			mnn.healMinion(1, b);
		}
		
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
        return;
    }
	

}

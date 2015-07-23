package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class StateMachine_Sim extends Simtemplate {
	//"id":224,"name":"State Machine","description":"When a creature comes into play on opponent's side, State Machine deals 1 [magic damage] to that creature. When State Machine's Countdown becomes 0, it's destroyed."

	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(triggerEffectMinion.position.color == summonedMinion.position.color) return;
		
		b.doDmg(summonedMinion, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		b.destroyMinion(m, m);
    	return;
    }

}

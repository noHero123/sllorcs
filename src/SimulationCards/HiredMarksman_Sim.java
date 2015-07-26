package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UPosition;

public class HiredMarksman_Sim extends Simtemplate {
	//"id":300,"name":"Hired Marksman","description":"When Hired Marksman's Countdown is 0, you may reset its Countdown to deal 3 [physical damage] to target creature."
	//ranged

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, triggerEffectMinion, 3, AttackType.UNDEFINED, DamageType.PHYSICAL);
		triggerEffectMinion.resetAcWithMessage(b);
		
        return;
    }

}

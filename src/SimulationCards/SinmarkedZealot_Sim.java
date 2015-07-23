package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class SinmarkedZealot_Sim extends Simtemplate {
	//"id":52,"name":"Sinmarked Zealot","description":"When Countdown is 0, you may sacrifice Sinmarked Zealot to deal 2 [magic damage] to target unit."

	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.doDmg(target, triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
        return;
    }
	

}

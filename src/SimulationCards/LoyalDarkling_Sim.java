package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class LoyalDarkling_Sim extends Simtemplate {
	//"id":162,"name":"Loyal Darkling","description":"When Countdown is 0, you may sacrifice Loyal Darkling."
	
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);

    	return;
		
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		
		Minion target = b.getPlayerIdol(Board.getOpposingColor(m.position.color), m.position.row);
		b.doDmg(target, m, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
        return;
    }
	
	
}

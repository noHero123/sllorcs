package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.ResourceName;
import BattleStuff.subType;

public class LoyalDarkling_Sim extends Simtemplate {
	//"id":162,"name":"Loyal Darkling","description":"When Countdown is 0, you may sacrifice Loyal Darkling."
	
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		b.destroyMinion(triggerEffectMinion, triggerEffectMinion);
		
		b.changeMaxRessource(ResourceName.GROWTH, triggerEffectMinion.position.color, 1);
		
		//dont need a ressource update message, because this costs 2 energy-> is send in activeability.payenergy
		
    	return;
		
    }
	
	public  void onDeathrattle(Board b, Minion m)
    {
		
		Minion target = b.getPlayerIdol(Board.getOpposingColor(m.position.color), m.position.row);
		b.doDmg(target, m, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
        return;
    }
	
	
}

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

public class Owl_Sim extends Simtemplate {
	//"id":221,"name":"Owl","description":"" *flying
	
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		UPosition targ = targets.get(0);
		
		triggerEffectMinion.buffMinion(0, 0, 1, b);
		triggerEffectMinion.movesThisTurn++;
		b.unitChangesPlace(triggerEffectMinion.position, targ);

    	return;
		
    }
	
}

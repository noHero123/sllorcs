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
import BattleStuff.SubType;

public class Owl_Sim extends Simtemplate {
	//"id":221,"name":"Owl","description":"" *flying
	
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		Position targ = targets.get(0);
		
		triggerEffectMinion.movesThisTurn++;
		b.unitChangesPlace(triggerEffectMinion.position, targ);

    	return;
		
    }
	
}

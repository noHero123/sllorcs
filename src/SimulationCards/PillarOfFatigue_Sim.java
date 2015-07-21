package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class PillarOfFatigue_Sim extends Simtemplate 
{

	//"id":186,"name":"Pillar of Fatigue","description":"When a creature moves, its Countdown is increased by 1."
	
	
	 public  void onMinionMoved(Board b, Minion triggerEffectMinion, Minion movedMinion)
	 {
		 if(movedMinion.cardType != Kind.CREATURE) return;
		 movedMinion.buffMinion(0, 0, 1, b);
		 return;
	 }
}

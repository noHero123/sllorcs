package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Rigged_Sim extends Simtemplate {
	//"id":139,"name":"Rigged","description":"When enchanted structure is destroyed in combat, opponent units on the same row are dealt 2 [physical damage]." 
	//+ unstable 1

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_structures;
	}
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	    {
		 	if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		 	{
		 		UColor oppcol = Board.getOpposingColor(diedMinion.position.color);
		 		ArrayList<Minion> all = b.getPlayerFieldList(oppcol);
		 		ArrayList<Minion> targets = new ArrayList<Minion>();
		 		for(Minion m : all)
		 		{
		 			if(m.position.row == diedMinion.position.row)
		 			{
		 				m.aoeDmgToDo=2;
		 				targets.add(m);
		 			}
		 		}
		 		Minion idol =b.getPlayerIdol(oppcol, diedMinion.position.row);
		 		idol.aoeDmgToDo=1;
		 		targets.add(idol);
		 		b.doDmg(all, triggerEffectMinion, -100, AttackType.UNDEFINED, DamageType.MAGICAL);//dmg=-100 => aoedmg :D
		 	}	
			
	        return;
	    }

}

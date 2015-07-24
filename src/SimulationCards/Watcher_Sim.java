package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Watcher_Sim extends Simtemplate {
	//"id":253,"name":"Watcher","description":"When a unit adjacent to Watcher is destroyed by non-combat damage or death effects, Watcher deals 2 damage to a random opponent idol."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.position.isNeightbour(triggerEffectMinion.position) && dmgtype != DamageType.COMBAT)
		{
			//do dmg to random idol
			ArrayList<Minion> idols = new ArrayList<Minion>();
			UColor oppcol = Board.getOpposingColor(triggerEffectMinion.position.color);
			for(Minion idol : b.getPlayerIdols(oppcol))
			{
				if(idol.Hp>=1) idols.add(idol);
			}
			if( idols.size()==0) return;
		
			int randomint = b.getRandomNumber(0, idols.size()-1);
					
			b.doDmg(b.getPlayerIdol(oppcol, randomint), triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
		}
			
        return;
    }

	

}

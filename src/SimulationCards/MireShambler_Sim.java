package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class MireShambler_Sim extends Simtemplate {
	//"id":164,"name":"Mire Shambler","description":"Before attacking, Mire Shambler moves to a random adjacent tile."

	
	public  void onUnitIsGoingToAttack(Board b, Minion triggerEffectMinion, Minion attacker )
    {
		if(triggerEffectMinion!=attacker) return;
		
		Minion m = attacker;
		if(m != null)
		{
			UColor opp = m.position.color;
			Minion[][] enemyb = b.getPlayerField(opp);
			//move unit to random tile
			ArrayList<UPosition> nbrs  = m.position.getNeightbours();
			ArrayList<UPosition> freetiles  = new ArrayList<UPosition>();
			for(UPosition ps : nbrs)
			{
				if(enemyb[ps.row][ps.column] == null)
				{
					freetiles.add(ps);
				}
			}
			if(freetiles.size() >= 1)
			{
			
				int randomint = b.getRandomNumber(0, freetiles.size() - 1);
				b.unitChangesPlace(m.position, freetiles.get(randomint), true, true);	
			}
			
		}
		
        return;
    }

	
}

package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class Wildling_Sim extends Simtemplate {
	//"id":154,"name":"Wildling","description":"When a creature comes into play adjacent to Wildling, Wildling is dealt 1 [pure damage] and gets +1 Attack."
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color == triggerEffectMinion.position.color)
		{
			boolean jes=false;
			for(Position p : triggerEffectMinion.position.getNeightbours())
			{
				if(p.isEqual(summonedMinion.position))
				{
					jes=true;
				}
			}
			if(jes)
			{
				triggerEffectMinion.buffMinion(1, 0, 0, b);
				b.doDmg(triggerEffectMinion, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
			}
		}
        return;
    }
	
}

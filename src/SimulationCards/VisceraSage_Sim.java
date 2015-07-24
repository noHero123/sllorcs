package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class VisceraSage_Sim extends Simtemplate
{
	//"id":161,"name":"Viscera Sage","description":"When Viscera Sage destroys another unit, draw 1 scroll."

	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(attacker == triggerEffectMinion)
		{
			
			b.drawCards(attacker.position.color, 1);
			
		}
			
        return;
    }
	
	
}

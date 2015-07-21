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

public class TotemOfSuffering_Sim extends Simtemplate 
{

	//"id":173,"name":"Totem of Suffering","description":"When a creature you control is destroyed, the opposing idol on that row is dealt 1 damage."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(diedMinion.cardType != Kind.CREATURE || diedMinion.position.color != triggerEffectMinion.position.color) return;
		
		Color oppcol = Board.getOpposingColor(diedMinion.position.color);
		
		b.doDmg(b.getPlayerIdol(oppcol, diedMinion.position.row), triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		
        return;
    }
	
}

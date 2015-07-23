package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class PestDissimulator_Sim extends Simtemplate {
	//"id":245,"name":"Pest Dissimulator","description":"When Pest Dissimulator deals damage to a creature, that creature and adjacent creatures become [poisoned]."
	
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(attacker != triggerEffectMinion.owner || dmgdone <=0) return;
		if(damagedMinion.cardType != Kind.CREATURE) return;
		
		ArrayList<Minion> poisontargets = new ArrayList<Minion>();
		poisontargets.add(damagedMinion);
		for(Minion m : b.getMinionsFromPositions(damagedMinion.position.getNeightbours()) )
		{
			if(m.cardType == Kind.CREATURE) poisontargets.add(m);
		}
		
		for(Minion m : poisontargets)
		{
			m.addnewPoison(b, attacker.position.color);
		}
		
		
		
        return;
    }
	
	
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class BlastStrike_Sim extends Simtemplate {
	//"id":138,"name":"Blast Strike","description":"When enchanted creature deals damage, units adjacent to its target are dealt 1 [magic damage]."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Blast Strike", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		
		ArrayList<Minion> targs = b.getMinionsFromPositions(damagedMinion.position.getNeightbours());
		
		if(targs.size() == 0) return;
		
		for(Minion m : targs)
		{
			m.aoeDmgToDo=1;
		}
		b.doDmg(targs, triggerEffectMinion, -100, AttackType.UNDEFINED, DamageType.MAGICAL); 
		
        return;
    }
	
	
	
}

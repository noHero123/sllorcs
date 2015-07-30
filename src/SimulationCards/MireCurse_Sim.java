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

public class MireCurse_Sim extends Simtemplate
{
	//"id":102,"name":"Mire Curse","description":"Mire Curse deals 1 [poison damage] to adjacent creatures at the beginning of each of its turns."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Mire Curse", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	{
		if(triggerEffectMinion.owner != null && turnStartColor == triggerEffectMinion.owner.position.color)
		{
			ArrayList<Minion> creatures = new ArrayList<Minion>();
			for(Minion m : b.getMinionsFromPositions(triggerEffectMinion.owner.position.getNeightbours()))
			{
				if(m.cardType == Kind.CREATURE)
					{
						m.aoeDmgToDo=1;
						creatures.add(m);
					}
			}
			b.doDmg(creatures, triggerEffectMinion, -100, AttackType.UNDEFINED, DamageType.POISON);
			
		}
	}
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
}

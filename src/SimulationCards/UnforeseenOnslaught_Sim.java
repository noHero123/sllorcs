package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class UnforeseenOnslaught_Sim extends Simtemplate {
	//"id":243,"name":"Unforeseen Onslaught","description":"Creatures you control with Attack 1 or less get [Slayer] until their next attack."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAttack()<=1 && m.cardType == Kind.CREATURE)
			{
				m.addnewEnchantments("BUFF", "Unforeseen Onslaught", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion.owner == attacker)
		{
			if(dmgdone>=1)
			{
				b.destroyMinion(damagedMinion, triggerEffectMinion);
			}
			attacker.removeEnchantment(triggerEffectMinion, true, b);
		}
        return;
    }
}

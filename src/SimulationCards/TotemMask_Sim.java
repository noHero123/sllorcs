package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class TotemMask_Sim extends Simtemplate 
{

	//"id":542,"name":"Totem Mask","description":"When a structure comes into play, enchanted unit gets +2 Attack and +1 Health."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Totem Mask", playedCard.card.cardDescription, playedCard, b);
		playedCard.turnCounter=0;
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.cardType == Kind.STRUCTURE)
		{
			triggerEffectMinion.owner.buffMinion(2, 1, 0, b);
			triggerEffectMinion.turnCounter++;
		}
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	if(m.turnCounter>=1) m.owner.buffMinionWithoutMessage(-2*m.turnCounter, -m.turnCounter, 0, b);
	 	m.turnCounter=0;
        return;
    }
	
	
}

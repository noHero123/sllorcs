package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class SearingShackles_Sim extends Simtemplate 
{

	//"id":184,"name":"Searing Shackles","description":"Enchanted unit deals 1 damage to the idol behind it at the end of each of its turns. When enchanted unit moves, it is dealt 2 [magic damage]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Searing Shackles", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	    {
		 	if(turnEndColor != triggerEffectMinion.owner.position.color) return false;
		 	Minion idol = b.getPlayerIdol(turnEndColor, triggerEffectMinion.owner.position.row);
		 	b.doDmg(idol, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		 	
	        return false;
	    }
	
	 public  void onMinionMoved(Board b, Minion triggerEffectMinion, Minion movedMinion)
	 {
		 if(triggerEffectMinion.owner != movedMinion) return;
		 b.doDmg(movedMinion, triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		 return;
	 }
}

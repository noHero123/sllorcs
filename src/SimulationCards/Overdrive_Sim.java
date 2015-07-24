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

public class Overdrive_Sim extends Simtemplate {
	//"id":140,"name":"Overdrive","description":"When Overdrive comes into play, and at the beginning of its turns, enchanted structure's Countdown is decreased by 2 and it is dealt 1 [magic damage]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_structures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.doDmg(target, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		target.buffMinionWithoutMessage(0, 0, -2, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Bear Paw", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	{
		if(turnStartColor == triggerEffectMinion.owner.position.color)
		{
			b.doDmg(triggerEffectMinion.owner, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
			triggerEffectMinion.owner.buffMinion(0, 0, -2, b);
		}
	}
	
	
	
}

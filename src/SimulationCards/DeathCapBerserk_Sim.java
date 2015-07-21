package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class DeathCapBerserk_Sim extends Simtemplate 
{

	//"id":30,"name":"Death Cap Berserk","description":"When Death Cap Berserk comes into play, and at the beginning of its turns, enchanted creature's Countdown is decreased by 2 and it is dealt 1 [magic damage]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.doDmg(target, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		target.buffMinionWithoutMessage(0, 0, -2, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Death Cap Berserk", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
    {
		Minion target = triggerEffectMinion.owner;
		b.doDmg(target, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		target.buffMinion(0, 0, -2, b);//status update is done in add card as enchantment
        return;
    }
	
}

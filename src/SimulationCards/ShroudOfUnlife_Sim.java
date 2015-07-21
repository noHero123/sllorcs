package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class ShroudOfUnlife_Sim extends Simtemplate {
	//"id":179,"name":"Shroud of Unlife","description":"When Shroud of Unlife comes into play, enchanted creature becomes Undead. When enchanted creature is destroyed, a <Husk> is summoned in its place."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		boolean triggerOtherMetalHeart = false;
		if(!target.subtypes.contains(subType.Undead))
		{
			triggerOtherMetalHeart=true;
		}
		target.subtypes.add(subType.Undead);
		target.addCardAsEnchantment("ENCHANTMENT", "Shroud of Unlife", "", playedCard, b);
		
		//TODO trigger sanctuary of the lost! (if triggerOtherMetalHeart)
		
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion == triggerEffectMinion.owner)
		{
			Card c = CardDB.getInstance().cardId2Card.get(163);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			b.summonList.add(b.new SummonItem(ill, diedMinion.position));
		}
			
        return;
    }
	
	
}

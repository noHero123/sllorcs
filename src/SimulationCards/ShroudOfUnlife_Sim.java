package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class ShroudOfUnlife_Sim extends Simtemplate {
	//"id":179,"name":"Shroud of Unlife","description":"When Shroud of Unlife comes into play, enchanted creature becomes Undead. When enchanted creature is destroyed, a <Husk> is summoned in its place."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	//{"name":"Undead","description":"This unit has the Undead subtype.","type":"STARTBUFF"}
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
	
		target.addSubtype(SubType.Undead, b);
		target.addCardAsEnchantment("ENCHANTMENT", "Shroud of Unlife", "", playedCard, b);
		target.addnewEnchantments("STARTBUFF", "Undead", "This unit has the Undead subtype.", new Card(), b, playedCard.position.color);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		diedMinion.removeSubtype(SubType.Undead, b);
		
		if(diedMinion == triggerEffectMinion.owner)
		{
			Card c = CardDB.getInstance().cardId2Card.get(163);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			b.addItemToSummonList(b.new SummonItem(ill, diedMinion.position, 0));
		}
			
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.removeSubtype(SubType.Undead, b);
        return;
    }
	
	
}

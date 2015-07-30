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

public class IllthornSeed_Sim extends Simtemplate {
	//"id":114,"name":"Illthorn Seed","description":"When enchanted unit you control is destroyed, summon an <Illthorn> in its place."
	//spiky 1
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public int getSpikyDamage(Board b ,Minion m, Minion defender)
    {
    	return 1;
    }
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Illthorn Seed", "", playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		{
			//summon illthorn
			Card c = CardDB.getInstance().cardId2Card.get(115);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			b.addItemToSummonList(b.new SummonItem(ill, diedMinion.position));
			//b.summonUnitOnPosition(new Position(diedMinion.position), ill);
		}
			
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
	
}

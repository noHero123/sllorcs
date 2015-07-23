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
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class SeedOfInsurgency_Sim extends Simtemplate {
	//"id":304,"name":"Seed of Insurgency","description":"When enchanted creature is destroyed, it's resummoned on the same tile. Its Countdown is set to 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Seed of Insurgency", "", playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		{
			//summon illthorn
			Minion ill = new Minion(diedMinion.card, -1, diedMinion.position.color);
			b.summonList.add(b.new SummonItem(ill, diedMinion.position, 1));
			//b.summonUnitOnPosition(new Position(diedMinion.position), ill);
		}
			
        return;
    }
	
	
}

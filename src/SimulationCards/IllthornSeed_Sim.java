package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class IllthornSeed_Sim extends Simtemplate {
	//"id":114,"name":"Illthorn Seed","description":"When enchanted unit you control is destroyed, summon an <Illthorn> in its place."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public int getSpikyDamage(Board b ,Minion m)
    {
    	return 1;
    }
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Illthorn Seed", "", playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		{
			//summon illthorn
			Card c = CardDB.getInstance().cardId2Card.get(115);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			b.summonUnitOnPosition(new Position(diedMinion.position), ill);
		}
			
        return;
    }
	
	
}

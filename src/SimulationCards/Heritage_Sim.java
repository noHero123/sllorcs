package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Heritage_Sim extends Simtemplate 
{

	//"id":92,"name":"Heritage","description":"When enchanted unit you control is destroyed, draw 2 scrolls."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;//tested
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Heritage", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(diedMinion.position.color == triggerEffectMinion.owner.position.color)
		{
			b.drawCards(diedMinion.position.color, 2);
		}
        return;
    }
	
}

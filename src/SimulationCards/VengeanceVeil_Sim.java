package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.ResourceName;
import BattleStuff.tileSelector;

public class VengeanceVeil_Sim extends Simtemplate 
{

	//id":97,"name":"Vengeance Veil","description":"When enchanted unit takes damage, its Countdown is decreased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		//replenish
		int x = playedCard.card.costGrowth + playedCard.card.costOrder + playedCard.card.costEnergy + playedCard.card.costDecay;
		b.changeCurrentRessource(ResourceName.WILD, playedCard.position.color, x);
		
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Vengeance Veil", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionGotDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, int dmg, Minion attacker)
    {
		if(dmg<=0 || triggerEffectMinion.owner != damagedMinion)
		{
			return;
		}
		
		damagedMinion.buffMinion(0, 0, -1, b);
		
        return;
    }
	
}

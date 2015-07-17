package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class LeechingRing_Sim extends Simtemplate {
	//"id":43,"name":"Leeching Ring","description":"When enchanted creature deals damage, it is [healed] by its Attack value."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Leeching Ring", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion)
    {
		triggerEffectMinion.healMinion(triggerEffectMinion.Ap, b);
        return;
    }
	
	
	
}

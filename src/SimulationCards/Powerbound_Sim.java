package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Powerbound_Sim extends Simtemplate 
{

	//"id":95,"name":"Powerbound","description":"When enchanted unit you control is destroyed, increase Order by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;//tested
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Powerbound", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker)
    {
		if(diedMinion.position.color == triggerEffectMinion.owner.position.color)
		{
			int[] curE = b.whiteRessources;
			if(diedMinion.position.color == Color.black) curE = b.blackRessources;
			
			curE[1] +=1;
			b.addMessageToBothPlayers(b.getResourcesUpdateMessage());
		}
        return;
    }
	
}

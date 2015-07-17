package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class PotionOfResistance_Sim extends Simtemplate 
{

	//"id":47,"name":"Potion of Resistance","description":"For 3 rounds, damage dealt to enchanted unit is reduced to 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public boolean reduceDmgToOne(Board b ,Minion m)
    {
    	return true;
    }
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.imuneToNextDmg=true;
		target.addCardAsEnchantment("ENCHANTMENT", "Plating", playedCard.card.cardDescription, playedCard, b);
		playedCard.lingerDuration = 3;
        return;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
    {
		triggerEffectMinion.lingerDuration--;
		if(triggerEffectMinion.lingerDuration ==0)
		{
			//remove!
			triggerEffectMinion.owner.attachedCards.remove(triggerEffectMinion);
		}
		else
		{
			String txt = "For " + triggerEffectMinion.lingerDuration + " rounds, damage dealt to enchanted unit is reduced to 1.";
			triggerEffectMinion.buffDescription = txt;
		}
		
		//update minion in both cases //is done automatically in onTurnStart!
		
        return;
    }
	
}

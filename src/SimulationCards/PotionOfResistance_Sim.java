package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
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
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.imuneToNextDmg=true;
		target.addCardAsEnchantment("ENCHANTMENT", "Plating", playedCard.card.cardDescription, playedCard, b);
		playedCard.lingerDuration = 3;
        return;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
    {
		triggerEffectMinion.lingerDuration--;
		if(triggerEffectMinion.lingerDuration ==0)
		{
			//remove!
			triggerEffectMinion.owner.removeEnchantment(triggerEffectMinion, false, b);
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

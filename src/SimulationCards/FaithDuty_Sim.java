package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class FaithDuty_Sim extends Simtemplate 
{

	//"id":209,"name":"Faith Duty","description":"Enchant unit you control. At the end of each of its turns, enchanted unit is dealt 1 [pure damage], and opponent units on that row get their Countdown increased by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Faith Duty", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		b.doDmg(triggerEffectMinion.owner, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		
		int row = triggerEffectMinion.owner.position.row;
		
		for(Minion m :  b.getPlayerFieldList(Board.getOpposingColor(triggerEffectMinion.owner.position.color)))
		{
			if(m.position.row == row)
			{
				m.buffMinion(0, 0, 1, b);
			}
		}
		
        return false;
    }
	
}

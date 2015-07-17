package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class IronWhip_Sim extends Simtemplate
{
	
	//"id":142,"name":"Iron Whip","description":"Deal 1 [magic damage] to target unit. It gets +1 Attack until end of turn, and its Countdown is decreased by 1. "
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		b.doDmg(target, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		if(target.Hp>=1)
		{
			target.buffMinionWithoutMessage(1, 0, -1, b);
			target.addnewEnchantments("BUFF", "Iron Whip", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		}
        
        return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		//if()
		triggerEffectMinion.owner.buffMinionWithoutMessage(-1, 0, 0, b);
        return true;//buff is removed, so we return true
    }
	
}

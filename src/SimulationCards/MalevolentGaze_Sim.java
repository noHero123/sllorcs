package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class MalevolentGaze_Sim extends Simtemplate {
	//"id":279,"name":"Malevolent Gaze","description":"Target creature's [Move] is decreased by 2 until end of its turn. It gets [Curse] 2."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures; 
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.moveChanges-=2;
		target.addnewEnchantments("BUFF", "Malevolent Gaze", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		target.addnewCurse(b, playedCard.position.color, 2);
		
		
		
        return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		triggerEffectMinion.owner.moveChanges+=2;
        return true;//buff is removed, so we return true
    }
}

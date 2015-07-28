package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class FeedbackJolt_Sim extends Simtemplate
{
	
	//id":385,"name":"Feedback Jolt","description":"Each opponent unit is dealt 2 [magic damage] per attached enchantment."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		for(Minion target : b.getPlayerFieldList(playedCard.position.color))
		{
			int dmg = 0;
			
			for(Minion ench : target.getAttachedCards())
			{
				if(ench.cardID != -1)
				{
					dmg+=2;
				}
			}
			
			if(dmg>=2) b.doDmg(target, playedCard, dmg, AttackType.UNDEFINED, DamageType.MAGICAL);
		}
		
		
		
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class Atrophy_Sim extends Simtemplate {
	//"id":198,"name":"Atrophy","description":"Target unit's Attack is set to 0 until its next attack. It cannot be increased during this time. "
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addnewEnchantments("BUFF", "Atrophy", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone)
    {
		if(triggerEffectMinion.owner == attacker)
		{
			attacker.removeEnchantment(triggerEffectMinion, true, b);
		}
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class RestlessBones_Sim extends Simtemplate {
	//"id":177,"name":"Restless Bones","description":"Undead units you control get +2 Attack until end of turn, and their Countdown is decreased by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.subtypes.contains(subType.Undead))
			{
				m.buffMinionWithoutMessage(2, 0, -1, b);
				m.addnewEnchantments("BUFF", "Restless Bones", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
        return;
    }
	
	
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		if(triggerEffectMinion.owner == null) return false;
		triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return true;//buff is removed, so we return true
    }
	
}

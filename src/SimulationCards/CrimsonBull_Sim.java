package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class CrimsonBull_Sim extends Simtemplate {
	//"id":78,"name":"Crimson Bull","description":"Units you control get +2 Attack until end of turn."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0)
			{
				m.buffMinionWithoutMessage(2, 0, 0, b);
				m.addnewEnchantments("BUFF", "Crimson Bull", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
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
		//if()
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}
        return true;//buff is removed, so we return true
    }
}

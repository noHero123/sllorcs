package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class HornOfAges_Sim extends Simtemplate {
	//"id":101,"name":"Horn of Ages","description":"Opponent units have their [Move] decreased by 1 until end of their turn."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Color oppcol = Board.getOpposingColor(playedCard.position.color);
		for(Minion m : b.getPlayerFieldList(oppcol))
		{
			if(m.getAc()>=0)
			{
				m.moveChanges-=1;
				m.addnewEnchantments("BUFF", "Horn of Ages", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
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
		if(triggerEffectMinion.owner.position.color == turnEndColor)
		{
			triggerEffectMinion.owner.moveChanges+=1;
		}
        return true;//buff is removed, so we return true
    }
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class FierceTactics_Sim extends Simtemplate {
	//"id":214,"name":"Fierce Tactics","description":"Your melee creatures become [Relentless] until end of turn. Opponent ranged units get [Curse] 1 until end of turn."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.attackType == AttackType.MELEE)
			{
				m.addnewEnchantments("BUFF", "Fierce Tactics", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
		
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(playedCard.position.color)))
		{
			if(m.getAc()>=0 && m.attackType == AttackType.RANGED)
			{
				m.curse+=1;
				m.addnewEnchantments("BUFF", "Fierce Tactics",  playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
		if(m.owner!=null) return false; // not an enchantment :D
		
		if(m.position.color != m.owner.position.color) return false;
    	return true;
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
			if(triggerEffectMinion.position.color != triggerEffectMinion.owner.position.color) 
			{
				//remove curse
				triggerEffectMinion.owner.curse-=1;
			}
			else
			{
				//remove relentless (not needed) 
			}
		}
        return true;//buff is removed, so we return true
    }
}

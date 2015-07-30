package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class IreAndBile_Sim extends Simtemplate {
	//"id":249,"name":"Ire And Bile","description":"All units get [Curse] 1 and +1 Attack until end of opponent's turn.",
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			m.buffMinionWithoutMessage(1, 0, 0, b);
			m.curse+=1;
			m.addnewEnchantments("BUFF", "Ire and Bile", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		}
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		//let it end at OPPONENT turn
		if(turnEndColor== triggerEffectMinion.position.color) return false;
		
        return true;//buff is removed, so we return true
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-1, 0, 0, b);
	 	m.owner.curse-=1;
        return;
    }
}

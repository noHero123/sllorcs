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

public class NewOrders_Sim extends Simtemplate {
	//"id":73,"name":"New Orders","description":"Your units have their [Move] increased by 1 until end of turn."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0)
			{
				m.moveChanges+=1;
				m.addnewEnchantments("BUFF", "New Orders", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
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
		//if()
        return true;//buff is removed, so we return true
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.moveChanges-=1;
        return;
    }
	
	
}

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

public class Desperation_Sim extends Simtemplate {
	//"id":146,"name":"Desperation","description":"Target creature gets +2 Attack until end of turn for each opponent unit on the same row. It is then dealt 1 [magic damage]."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff=0;
		target.buffMinionWithoutMessage(2*buff, 0, 0, b);//status update is done in add card as enchantment
		Minion ench = target.addnewEnchantments("BUFF", "Desperation", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		ench.turnCounter += buff;
		b.doDmg(target, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		
        return true;//buff is removed, so we return true
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-2*m.turnCounter, 0, 0, b);
	 	m.turnCounter=0;
        return;
    }
}

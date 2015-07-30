package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ViciousStrike_Sim extends Simtemplate
{
	
	//"id":301,"name":"Vicious Strike","description":""
	//"Surge: Target unit gets +X Attack until end of turn."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		int surge = b.getCurrentRessource(ResourceName.ENERGY, playedCard.position.color);
		b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, -surge);
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(surge, 0, 0, b);//status update is done in add card as enchantment
		target.addnewEnchantments("BUFF", "Vicious Strike", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color, surge);
		
        
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
	 	m.owner.buffMinionWithoutMessage(-m.turnCounter, 0, 0, b);
		m.turnCounter=0;
        return;
    }
	
}

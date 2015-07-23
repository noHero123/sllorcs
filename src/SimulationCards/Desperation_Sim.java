package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Desperation_Sim extends Simtemplate {
	//"id":146,"name":"Desperation","description":"Target creature gets +2 Attack until end of turn for each opponent unit on the same row. It is then dealt 1 [magic damage]."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buff=0;
		target.buffMinionWithoutMessage(2*buff, 0, 0, b);//status update is done in add card as enchantment
		target.addnewEnchantments("BUFF", "Desperation", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		target.desperationBuffs += buff;
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
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2*triggerEffectMinion.owner.desperationBuffs, 0, 0, b);
			triggerEffectMinion.owner.desperationBuffs=0;
			//so its is only debuffed once (with all desperation buffs on it), but deal dmg to amount of desperation Buffs!
			b.doDmg(triggerEffectMinion.owner, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		}
        return true;//buff is removed, so we return true
    }
}

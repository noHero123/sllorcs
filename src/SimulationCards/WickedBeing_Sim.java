package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class WickedBeing_Sim extends Simtemplate {
	//"id":239,"name":"Wicked Being","description":"" unstable 2
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		target.addCardAsEnchantment("ENCHANTMENT", "Wicked Being", "", playedCard, b);
		
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion.owner == null || triggerEffectMinion.owner!= diedMinion) return;
		
		Minion target = b.getPlayerIdol(Board.getOpposingColor(triggerEffectMinion.owner.position.color), triggerEffectMinion.owner.position.row);
		b.doDmg(target, triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);	
        return;
    }
	
	
}

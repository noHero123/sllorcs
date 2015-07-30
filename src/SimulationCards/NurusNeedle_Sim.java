package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class NurusNeedle_Sim extends Simtemplate 
{

	//"id":309,"name":"Nuru's Needle","description":"Enchanted unit's Attack, Health and [Move] are decreased by 1. It gets [Curse] 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.moveChanges-=1;
		target.buffMinionWithoutMessage(-1, -1, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Nuru's Needle", playedCard.card.cardDescription, playedCard, b);
		target.addnewCurse(b, playedCard.position.color, 1);
        return;
    }
	
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.moveChanges+=1;
	 	m.owner.buffMinionWithoutMessage(1, 1, 0, b);
        return;
    }
	
}

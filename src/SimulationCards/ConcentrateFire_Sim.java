package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class ConcentrateFire_Sim extends Simtemplate
{
	//"id":149,"name":"Concentrate Fire","description":"Target Ranged or Lobber unit makes an extra attack after its next attack."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_lobbers_or_ranged_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Concentrate Fire", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	
	public void onAttackDone(Board b , Minion m, Minion self)
    {
		//do annother attack!
		if(m != self.owner)
        {
			return;
        }
		//remove enchantment
		
		m.removeEnchantment(self, true, b);
		
		//minion attacks a second time
		UColor otherColor = Board.getOpposingColor(m.position.color);
		Minion[][] defffield = b.getPlayerField(otherColor);
		b.unitAttacking(m, defffield, m.getAttack(b), m.attackType, DamageType.COMBAT);
		
    	return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
}

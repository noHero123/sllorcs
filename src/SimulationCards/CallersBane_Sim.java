package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class CallersBane_Sim extends Simtemplate 
{

	//"id":231,"name":"Caller's Bane","description":"When enchanted unit deals damage to an idol during combat, other idols on that side are dealt 1 damage."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Caller's Bane", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(DamageType.COMBAT == dmgtype && damagedMinion.isIdol && triggerEffectMinion.owner == attacker)
		 {
			 ArrayList<Minion> idols = new ArrayList<Minion>();
			 for(Minion id : b.getPlayerIdols(damagedMinion.position.color))
			 {
				 if(id != damagedMinion && id.Hp >=1)
				 {
					 id.aoeDmgToDo=1;
					 idols.add(id);
				 }
			 }
			 b.doDmg(idols, triggerEffectMinion, -100, AttackType.UNDEFINED, DamageType.SUPERIOR);
		 }
		 return;
	 }
}

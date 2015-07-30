package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class PilgrimsFeet_Sim extends Simtemplate 
{

	//"id":274,"name":"Pilgrim's Feet","description":"When enchanted creature moves, it is [healed] by 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Pilgrim's Feet", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
        return;
    }
	
	public  void onMinionMoved(Board b, Minion triggerEffectMinion, Minion movedMinion)
    {
		if(movedMinion==triggerEffectMinion)
		{
			movedMinion.healMinion(2, b);
		}
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class StagHeart_Sim extends Simtemplate 
{

	//"id":208,"name":"Stag Heart","description":"Enchanted unit gets +1 Attack and +1 Health for each Stag Heart on the same side."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		//TODO do same procedure with metal heart?
		Minion target = b.getMinionOnPosition(targets.get(0));
		int buffs = 1;
		for(Minion m : b.getPlayerFieldList(target.position.color))
		{
			for(Minion e : m.getAttachedCards())
			{
				if(e.typeId == 208)
				{
					m.buffMinion(1, 1, 0, b);
					buffs++;
				}
			}
		}
		target.buffMinionWithoutMessage(buffs, buffs, 1, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Stag Heart", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		//unbuff other minions!
		for(Minion mnn : b.getPlayerFieldList(m.owner.position.color))
		{
			for(Minion e : mnn.getAttachedCards())
			{
				if(e.typeId == 208)
				{
					mnn.buffMinion(-1, -1, 0, b);
				}
			}
		}
		
        return;
    }
	
}

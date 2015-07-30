package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class EternalSword_Sim extends Simtemplate 
{

	//"id":320,"name":"Eternal Sword","description":"Enchanted creature gets +2 Attack. When enchanted creature\\nis destroyed, a random adjacent creature gets Eternal Sword."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.buffMinionWithoutMessage(2, 0, 0, b);//status update is done in add card as enchantment
		target.addCardAsEnchantment("ENCHANTMENT", "Eternal Sword", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(diedMinion == triggerEffectMinion.owner)
		{
			//put sord on nightbour creature:
			ArrayList<Minion> ncreatures = new ArrayList<Minion>();
			for(Minion unit : b.getMinionsFromPositions(diedMinion.position.getNeightbours()))
			{
				if(unit.cardType == Kind.CREATURE)
				{
					ncreatures.add(unit);
				}
			}
			
			if(ncreatures.size()==0) return;
			
			int random = b.getRandomNumber(0, ncreatures.size()-1);
			Minion newTarget = ncreatures.get(random);
			newTarget.buffMinionWithoutMessage(2, 0, 0, b);
			Minion newEnch = newTarget.addnewEnchantments("ENCHANTMENT", "Eternal Sword", triggerEffectMinion.card.cardDescription, triggerEffectMinion.card, b, triggerEffectMinion.position.color);
			
			newEnch.cardID = triggerEffectMinion.cardID;
			//dont add triggerEffectMinion to grave
			triggerEffectMinion.cardID = -1;
	        
			
			
		}
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return;
    }
	
}

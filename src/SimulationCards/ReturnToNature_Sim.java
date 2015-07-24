package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class ReturnToNature_Sim extends Simtemplate {
	//"id":137,"name":"Return To Nature","description":"Creatures on target tile and adjacent tiles get -1 Health until end of turn. If a creature with this effect is destroyed, increase Growth by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all;
	}
	
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		ArrayList<UPosition> posses = targets.get(0).getNeightbours();
		posses.add(targets.get(0));
		int growth=0;
		for(Minion m : b.getMinionsFromPositions(posses))
		{
			if(m.cardType == Kind.CREATURE)
			{
				m.buffMinion(0, -1, 0, b);
				m.addnewEnchantments("BUFF", "Return To Nature", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
				if(m.Hp<=0) growth++; //have to count that, because minion dies in .buffMinion, before the enchantment is added!
			}
		}
		
		if(growth >=1)
		{
			b.changeMaxRessource(ResourceName.GROWTH, playedCard.position.color, growth);
		}
		
        return;
    }
	
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		//if()
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(0, 0, 1, b);
		}
        return true;//buff is removed, so we return true
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion.owner != null && triggerEffectMinion.owner == diedMinion)
		{
			b.changeMaxRessource(ResourceName.GROWTH, triggerEffectMinion.position.color, 1);
		}
			
        return;
    }
	
	
}

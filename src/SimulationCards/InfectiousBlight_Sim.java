package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class InfectiousBlight_Sim extends Simtemplate
{
	//"id":187,"name":"Infectious Blight","description":"When Infectious Blight comes into play, enchanted creature becomes [poisoned]. If it is destroyed by poison, a random creature on the same side gets Infectious Blight."

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Infectious Blight", playedCard.card.cardDescription, playedCard, b);
		target.addnewPoison(b, playedCard.position.color);
        return;
    }
	
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion == triggerEffectMinion.owner && dmgtype == DamageType.POISON)
		{
			ArrayList<Minion> creatures = new ArrayList<Minion>();
			for(Minion m : b.getPlayerFieldList(triggerEffectMinion.owner.position.color))
			{
				if(m.cardType == Kind.CREATURE)
				{
					creatures.add(m);
				}
			}
			
			if(creatures.size()==0) return;
			int randomint = b.getRandomNumber(0, creatures.size()-1);
			//create a new copy of infectious blight and change the cardids, so the old one is not added to graveyard
			Minion playedCard = triggerEffectMinion.getMinionToken();
			playedCard.cardID = triggerEffectMinion.cardID;
			
			creatures.get(randomint).addCardAsEnchantment("ENCHANTMENT", "Infectious Blight", playedCard.card.cardDescription, playedCard, b);
			//remove enchantment of dieing minion, so that infectious is not added to grave!
			triggerEffectMinion.cardID=-1;
			
		}
			
        return;
    }
	
	
}

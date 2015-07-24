package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Corrode_Sim extends Simtemplate {
	//"id":236,"name":"Corrode","description":"Units deal double damage to idols until end of turn. If a unit destroys an idol this turn, draw 1 scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		b.drawCorrodePossible++;
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			m.addnewEnchantments("BUFF", "Corrode", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
		}
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion.owner == attacker && damagedMinion.isIdol  && attackType != AttackType.UNDEFINED)
		{
			if(damagedMinion.Hp>=1)
			{
				b.doDmg(damagedMinion, triggerEffectMinion.owner, dmgdone, AttackType.UNDEFINED, DamageType.SUPERIOR);
			}
			
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if((attacker.cardType == Kind.CREATURE || attacker.cardType == Kind.STRUCTURE) && diedMinion.isIdol && b.drawCorrodePossible >=1 )
		{
			for(int i=0; i < b.drawCorrodePossible; i++)
			{
				b.drawCards(triggerEffectMinion.owner.position.color, 1);
			}
			
			b.drawCorrodePossible = 0;
		}
        return;
    }
	
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {

        return true;//buff is removed, so we return true
    }
}

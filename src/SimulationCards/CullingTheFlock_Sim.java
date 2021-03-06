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

public class CullingTheFlock_Sim extends Simtemplate {
	//"id":290,"name":"Culling the Flock","description":"Sacrifice target Beast. Other Beasts you control have their Attack increased by that unit's Attack until end of turn, and become fully [healed]."
	

	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_beasts;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		int attack = target.getAttack(b);
		b.destroyMinion(target, playedCard);
		
		for(Minion m: b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.cardType == Kind.CREATURE && m.getSubTypes().contains(SubType.Beast))
			{
				m.buffMinionWithoutMessage(attack, 0, 0, b);
				Minion ench = m.addnewEnchantments("BUFF", "Culling the Flock", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
				if(m.Hp>=1 && m.Hp<m.maxHP)
				{
					m.healMinion(m.maxHP-m.Hp, b);
				}
				
				ench.turnCounter+=attack;
			}
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
		
        return true;//buff is removed, so we return true
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage( -m.turnCounter, 0, 0, b);
		m.turnCounter = 0;
        return;
    }
	
}

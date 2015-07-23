package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class Efficiency_Sim extends Simtemplate {
	//"id":134,"name":"Efficiency","description":"Target unit gets +2 Attack until end of turn. If that unit destroys another unit this turn, increase Energy by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_ac;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0)
			{
				m.buffMinionWithoutMessage(2, 0, 0, b);
				m.addnewEnchantments("BUFF", "Efficiency", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
        return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		triggerEffectMinion.aoeDmgToDo = 0;
		//if()
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}
        return true;//buff is removed, so we return true
    }
	
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion.owner == diedMinion || triggerEffectMinion.aoeDmgToDo == 1) return;
		if(triggerEffectMinion.owner == attacker)
		{
			b.changeMaxRessource(ResourceName.ENERGY, triggerEffectMinion.position.color, 1);
			triggerEffectMinion.aoeDmgToDo = 1;
		}
        return;
    }
}

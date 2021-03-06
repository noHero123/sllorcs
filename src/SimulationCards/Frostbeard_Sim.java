package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Frostbeard_Sim extends Simtemplate {
	//id":91,"name":"Frostbeard","description":"When Frostbeard is destroyed during your opponent's turn, units you control get +2 Attack until end of your next turn."

	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
		if(m.owner==null)
		{
			//minion dies
			if(b.activePlayerColor == m.position.color) return; //opponent turn!
		
			for(Minion mnn : b.getPlayerFieldList(m.position.color))
			{
			if(mnn.getAc()>=0)
			{
				mnn.buffMinionWithoutMessage(2, 0, 0, b);
				mnn.addnewEnchantments("BUFF", "Frostbeard", m.card.cardDescription, m.card, b, m.position.color);
				//mnn.frostbeardCounter = 1;
			}
			}
		}
		else
		{
			//buff dies
			m.owner.buffMinionWithoutMessage(-2, 0, 0, b);
		}
		
        return;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		if(triggerEffectMinion.owner==null) return false;//its the minion, not the effect!
		if(turnEndColor != triggerEffectMinion.owner.position.color) return false; //not your end of turn
		//we dont have to count, because its always enemys turn, when your minions are buffed!
		
		return true;
		

    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
}

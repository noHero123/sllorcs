package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class OutcastRebel_Sim extends Simtemplate {
	//"id":289,"name":"Outcast Rebel","description":""
	//inspiring +1 attack, 
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getMinionsFromPositions(own.position.getNeightbours()))
		{
			m.buffMinionWithoutMessage(1, 0, 0, b);
			m.addnewEnchantments("BUFF", "Outcast Rebel", own.card.cardDescription, own.card, b, own.position.color);
		}
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		
		if(triggerEffectMinion.owner == null)
		{
			return false;
		}
		
		triggerEffectMinion.owner.buffMinionWithoutMessage(-1, 0, 0, b);
        return true;//buff is removed, so we return true
    }
	 
}

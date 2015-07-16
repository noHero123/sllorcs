package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;

public class KinfolkJarl_Sim extends Simtemplate {
	//"id":42,"name":"Kinfolk Jarl","description":"Kinfolk Jarl has +1 Attack for each adjacent creature."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		//buff existing minions
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.getAc()>=0)
			{
				m.buffMinionWithoutMessage(1, 0, 0, b);
				m.addnewEnchantments("BUFF", "Kinfolk Jarl", own.card.cardDescription, own.card, b, own.position.color);
			}
		}
		
		//buff jarl
		Position p = new Position(own.position);
		int buffs =b.getMinionsFromPositions(p.getNeightbours()).size();
		if(buffs>=1)own.buffMinion(buffs, 0, 0, b);
		
		own.currentAttackPlus = buffs;
		
        return;
    }
	
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		Position p = new Position(triggerEffectMinion.position);
		int buffs =b.getMinionsFromPositions(p.getNeightbours()).size();
		if(triggerEffectMinion.currentAttackPlus == buffs) return;
		
		
		triggerEffectMinion.buffMinionWithoutMessage(-1*triggerEffectMinion.currentAttackPlus, 0, 0, b);
		triggerEffectMinion.buffMinion(buffs, 0, 0, b);
		triggerEffectMinion.currentAttackPlus = buffs;
    	return;
    }
	
	
	public Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {

		if(triggerEffectMinion.owner == null) return false;//its the minion, not the buff!
		//if(turnEndColor != triggerEffectMinion.color) return false; //frostbeard lasts until our next turn
		if(triggerEffectMinion.owner.getAc()>=0)
		{
			triggerEffectMinion.owner.buffMinionWithoutMessage(-1, 0, 0, b);
		}
        return true;//buff is removed, so we return true
    }
	
}

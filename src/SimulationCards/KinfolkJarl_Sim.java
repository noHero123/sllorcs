package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class KinfolkJarl_Sim extends Simtemplate {
	//"id":42,"name":"Kinfolk Jarl","description":"Kinfolk Jarl has +1 Attack for each adjacent creature."
	//inspiring (comes in to play) +1 attack to adjacent units
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		//buff existing minions
		for(Minion m : b.getMinionsFromPositions(own.position.getNeightbours()))
		{
			if(m.getAc()>=0)
			{
				m.buffMinionWithoutMessage(1, 0, 0, b);
				m.addnewEnchantments("BUFF", "Kinfolk Jarl", own.card.cardDescription, own.card, b, own.position.color);
			}
		}
		
		//buff jarl
		UPosition p = new UPosition(own.position);
		int buffs =b.getMinionsFromPositions(p.getNeightbours()).size();
		if(buffs>=1)own.buffMinion(buffs, 0, 0, b);
		
		own.currentAttackPlus = buffs;
		
        return;
    }
	
	public void onFieldChanged(Board b, Minion triggerEffectMinion)
    {
		UPosition p = new UPosition(triggerEffectMinion.position);
		int buffs =b.getMinionsFromPositions(p.getNeightbours()).size();
		if(triggerEffectMinion.currentAttackPlus == buffs) return;
		
		
		triggerEffectMinion.buffMinionWithoutMessage(-1*triggerEffectMinion.currentAttackPlus, 0, 0, b);
		triggerEffectMinion.buffMinion(buffs, 0, 0, b);
		triggerEffectMinion.currentAttackPlus = buffs;
    	return;
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {

		if(triggerEffectMinion.owner == null) return false;//its the minion, not the buff!
		//if(turnEndColor != triggerEffectMinion.color) return false; //frostbeard lasts until our next turn
        return true;//buff is removed, so we return true
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-1, 0, 0, b);
        return;
    }
	
}

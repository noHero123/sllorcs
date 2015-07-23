package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class JarlUrhald_Sim extends Simtemplate {
	//"id":297,"name":"Jarl Urhald","description":""
	//unique, relentless, inspiring +2 attack, pillage countdown to 0
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.isRelentless=true;
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 297) b.destroyMinion(m, own);
		}
		
		for(Minion m : b.getMinionsFromPositions(own.position.getNeightbours()))
		{
			m.buffMinionWithoutMessage(2, 0, 0, b);
			m.addnewEnchantments("BUFF", "Jarl Urhald", own.card.cardDescription, own.card, b, own.position.color);
		}
    }
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
		if(m.owner!=null) return false;
        return true;
    }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, Color turnEndColor)
    {
		
		if(triggerEffectMinion.owner == null)
		{
			if(triggerEffectMinion.didDmgToIdol && triggerEffectMinion.position.color == turnEndColor)
			{
				triggerEffectMinion.buffMinion(0, 0, -1000, b);
			}
			triggerEffectMinion.didDmgToIdol=false;
			return false;
		}
		
		triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return true;//buff is removed, so we return true
    }
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion.owner != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 triggerEffectMinion.didDmgToIdol=true;
	     return;
	 }
	 
}

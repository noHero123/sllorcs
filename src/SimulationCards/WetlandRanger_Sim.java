package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class WetlandRanger_Sim extends Simtemplate {
	//"id":296,"name":"Wetland Ranger","description":""
	// pillage = your untis get +1 during your  and your opponents next turn.
	

	
	//TODO test pillage and double idol-attack buffs
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 for(Minion mnn : b.getPlayerFieldList(triggerEffectMinion.position.color))
		 {
				if(mnn.getAc()>=0)
				{
					mnn.buffMinionWithoutMessage(1, 0, 0, b);
					mnn.addnewEnchantments("BUFF", "Wetland Ranger", triggerEffectMinion.card.cardDescription, triggerEffectMinion.card, b, triggerEffectMinion.position.color, 2);
				}
		 }
		 
		 
		 
	     return;
	 }
	 
	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	 {
			if(triggerEffectMinion.owner==null) return false;//its the minion, not the effect!
			
			if(triggerEffectMinion.turnCounter <= 0)
			{
				return true;//buff is removed, so we return true
			}
			else
			{
				triggerEffectMinion.turnCounter-=1;
				return false;
			}
	 }
	 
		public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
	    {
		 	if(m.owner== null) return;
		 	m.owner.buffMinionWithoutMessage(-1, 0, 0, b);
		 	m.turnCounter=0;
	        return;
	    }
	 
}

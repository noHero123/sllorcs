package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class TopReaverThea_Sim extends Simtemplate {
	//"id":318,"name":"Top Reaver Thea","description":""
	//unique, Piercing, ranged, 
	//Pillage: Your [current] Energy is increased by 2 at the beginning of your next turn
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 318) b.destroyMinion(m, own);
		}
		
    }
	
	public boolean hasPiercing(Board b ,Minion m)
    {
    	return true;
    }
	
	public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
    {
		if(triggerEffectMinion.didDmgToIdol && triggerEffectMinion.position.color == turnStartColor)
		{
			b.changeCurrentRessource(ResourceName.ENERGY, triggerEffectMinion.position.color, 2);
			triggerEffectMinion.didDmgToIdol=false;
		}
        return;
    }
	
	 public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
	 {
		 if(triggerEffectMinion.owner != null || triggerEffectMinion != attacker || !damagedMinion.isIdol || dmgdone <=0 || dmgtype!=DamageType.COMBAT) return;
		 
		 
		 triggerEffectMinion.didDmgToIdol=true;
	     return;
	 }
	 
}

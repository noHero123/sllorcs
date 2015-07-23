package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Gravehawk_Sim extends Simtemplate {
	//"id":4,"name":"Gravehawk","description":"Gravehawk gets +1 Attack for each Gravelock you control."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		own.isRelentless=true;
		int buffs = 0;
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.getSubTypes().contains(SubType.Gravelock))
			{
				buffs++;
			}
		}
		if(buffs>=1) own.buffMinion(buffs, 0, 0, b);
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
    	return true;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(triggerEffectMinion.owner == summonedMinion) return;
		if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.getSubTypes().contains(SubType.Gravelock))
		{
			triggerEffectMinion.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		//unbuff wolf if a wolf dies
		if(triggerEffectMinion == diedMinion) return;
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.getSubTypes().contains(SubType.Gravelock))
		{
			triggerEffectMinion.buffMinion(-1, 0, 0, b);
		}
        return;
    }
	
	public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Gravelock)
		 {
			 triggerEffectMinion.buffMinion(1, 0, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Gravelock)
		 {
			 triggerEffectMinion.buffMinion(-1, 0, 0, b);
		 }
		 return;
	 }
	
}

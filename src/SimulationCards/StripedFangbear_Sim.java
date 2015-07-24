package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class StripedFangbear_Sim extends Simtemplate {
	//"id":307,"name":"Striped Fangbear","description":"As long as opponent controls any Humans, Striped Fangbear is [Relentless] and has +3 Attack."

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
		boolean buffs = false;
		for(Minion m : b.getPlayerFieldList(Board.getOpposingColor(own.position.color)))
		{
			if(m.getSubTypes().contains(SubType.Human))
			{
				buffs=true;
			}
		}
		if(buffs) 
		{
			own.buffMinion(3, 0, 0, b);
			own.fangbear=true;
		}
        return;
    }
	
	public boolean isRelentless(Board b ,Minion m)
    {
		return m.fangbear;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(triggerEffectMinion == summonedMinion || summonedMinion.position.color == triggerEffectMinion.position.color || triggerEffectMinion.fangbear) return;
		if(summonedMinion.getSubTypes().contains(SubType.Human))
		{
			triggerEffectMinion.buffMinion(3, 0, 0, b);
			triggerEffectMinion.fangbear=true;
		}
		
        return;
    }
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion == diedMinion|| diedMinion.position.color == triggerEffectMinion.position.color || !triggerEffectMinion.fangbear) return;
		//unbuff wolf if a wolf dies
		boolean buffs = false;
		for(Minion m : b.getPlayerFieldList(diedMinion.position.color))
		{
			if(m.getSubTypes().contains(SubType.Human))
			{
				if(diedMinion == m) continue;
				buffs=true;
			}
		}
		if(!buffs) 
		{
			triggerEffectMinion.buffMinion(-3, 0, 0, b);
			triggerEffectMinion.fangbear=false;
		}
        return;
    }
	
	public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		if(triggerEffectMinion == m || m.position.color == triggerEffectMinion.position.color || triggerEffectMinion.fangbear) return;
		if(m.getSubTypes().contains(SubType.Human))
		{
			triggerEffectMinion.buffMinion(3, 0, 0, b);
			triggerEffectMinion.fangbear=true;
		}
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion == m|| m.position.color == triggerEffectMinion.position.color || !triggerEffectMinion.fangbear) return;
			//unbuff wolf if a wolf dies
			boolean buffs = false;
			for(Minion mnn : b.getPlayerFieldList(m.position.color))
			{
				if(mnn == m) continue;
				if(mnn.getSubTypes().contains(SubType.Human))
				{
					buffs=true;
				}
			}
			if(!buffs) 
			{
				triggerEffectMinion.buffMinion(-3, 0, 0, b);
				triggerEffectMinion.fangbear=false;
			}
		 return;
	 }
	

}

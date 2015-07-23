package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class Junkyard_Sim extends Simtemplate {
	//"id":40,"name":"Junkyard","description":"Rats you control have +1 Attack and +1 Health."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.getSubTypes().contains(SubType.Rat))
			{
				m.buffMinion(1, 1, 0, b);
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.getSubTypes().contains(SubType.Rat))
		{
			summonedMinion.buffMinion(1, 1, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.getSubTypes().contains(SubType.Rat))
			{
				m.buffMinion(-1, -1, 0, b);
			}
		}
        return;
    }
	
	public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Rat)
		 {
			 m.buffMinion(1, 1, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Rat)
		 {
			 m.buffMinion(-1, -1, 0, b);
		 }
		 return;
	 }
}

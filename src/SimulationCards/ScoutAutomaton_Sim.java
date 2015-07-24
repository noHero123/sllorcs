package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class ScoutAutomaton_Sim extends Simtemplate {
	//"id":227,"name":"Scout Automaton","description":"Other Automatons you control have +1 Attack."
	
	
	 public int getArmor(Board b ,Minion triggerEffectMinion, Minion minion)
	    {
	    	return 1;
	    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.getSubTypes().contains(SubType.Automaton))
			{
				m.buffMinion(0, 1, 0, b);
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		
		if(summonedMinion.getSubTypes().contains(SubType.Automaton))
		{
			summonedMinion.buffMinion(0, 1, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.getSubTypes().contains(SubType.Automaton))
			{
				m.buffMinion(0, -1, 0, b);
			}
		}
        return;
    }
	
	public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Automaton)
		 {
			 m.buffMinion(0, 1, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion == m) return;
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Automaton)
		 {
			 m.buffMinion(0, -1, 0, b);
		 }
		 return;
	 }
}

package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class MachinePriest_Sim extends Simtemplate {
	//"id":79,"name":"Machine Priest","description":"Machine Priest does not attack. Structures and Automatons you control have +1 Attack."
	
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.getSubTypes().contains(SubType.Automaton) || m.cardType == Kind.STRUCTURE)
			{
				m.buffMinion(1, 0, 0, b);
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position) ) return; //dont buff himself
		if(summonedMinion.getSubTypes().contains(SubType.Automaton) || summonedMinion.cardType == Kind.STRUCTURE)
		{
			summonedMinion.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.getSubTypes().contains(SubType.Automaton) || m.cardType == Kind.STRUCTURE)
			{
				m.buffMinion(-1, 0, 0, b);
			}
		}
        return;
    }
	
	public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Automaton)
		 {
			 m.buffMinion(1, 0, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(triggerEffectMinion.position.color == m.position.color && subt == SubType.Automaton)
		 {
			 m.buffMinion(-1, 0, 0, b);
		 }
		 return;
	 }
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class GroundsOfTheFallen_Sim extends Simtemplate {
	//"id":337,"name":"Grounds of the Fallen","description":"All Humans have +1 Attack and [Magic resistance] 1."
	//linger 5
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 5;
	 }
	 
	 public int getMagicResistance(Board b ,Minion triggerEffectMinion, Minion minion)
	 {
		 //doenst has to be checked :D (it is checked before triggered
		 if(minion.getSubTypes().contains(SubType.Human) && minion.cardType == Kind.CREATURE)
			{
			 	return 1;
			}
		 return 0;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean added = b.addRule(playedCard);
		if(!added) return;
		for(Minion m: b.getAllMinionOfField())
		{
			if(m.getSubTypes().contains(SubType.Human) && m.cardType == Kind.CREATURE)
			{
				m.buffMinion(1, 0, 0, b);
			}
			
		}
		
	    return;
	 }
	 
	 public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
			if(summonedMinion.getSubTypes().contains(SubType.Human))
			{
				triggerEffectMinion.buffMinion(1, 0, 0, b);
			}
	        return;
	    }

	 
	 public void onSubTypeAdded(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(subt == SubType.Human)
		 {
			 m.buffMinion(1, 0, 0, b);
		 }
		 return;
	 }
	
	 public void onSubTypeDeleted(Board b, Minion triggerEffectMinion, Minion m, SubType subt )
	 {
		 if(subt == SubType.Human)
		 {
			 m.buffMinion(-1, 0, 0, b);
		 }
		 return;
	 }
	 
	 public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
	    {
			for(Minion m : b.getAllMinionOfField())
			{
				if(m.getSubTypes().contains(SubType.Human))
				{
					m.buffMinion(-1, 0, 0, b);
				}
			}
	        return;
	    }
	 
}

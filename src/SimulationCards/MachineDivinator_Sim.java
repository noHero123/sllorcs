package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class MachineDivinator_Sim extends Simtemplate {
	//"id":258,"name":"Machine Divinator","description":"Machine Divinator does not attack. Structures you control have +1 Health. When Machine Divinator's Countdown becomes 0, a [random] structure you control has its Countdown decreased by 1."
	
	
	public  boolean doesAttack(Board b, Minion m)
    {
        return false;
    }
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.cardType == Kind.STRUCTURE)
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
		
		if(summonedMinion.cardType == Kind.STRUCTURE)
		{
			summonedMinion.buffMinion(0, 1, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.cardType == Kind.STRUCTURE)
			{
				m.buffMinion(0, -1, 0, b);
			}
		}
        return;
    }
	
	public void onCountdownReachesZero(Board b , Minion m)
    {
		//TODO random :D
		//we priorize non-zero countdown >= all :D
		ArrayList<Minion> strucswa = new ArrayList<Minion>();
		for(Minion mnn : b.getPlayerFieldList(m.position.color))
		{
			if(mnn.getAc()>=1 && mnn.cardType == Kind.STRUCTURE) strucswa.add(mnn);
		}
		if(strucswa.size() ==0)
		{
			for(Minion mnn : b.getPlayerFieldList(m.position.color))
			{
				if(mnn.getAc()>=0 && mnn.cardType == Kind.STRUCTURE) strucswa.add(mnn);
			}	
		}
		
		if(strucswa.size() ==0)
		{
			return;
		}
		
		int random = b.getRandomNumber(0, strucswa.size()-1);
		
		strucswa.get(random).buffMinion(0, 0, -1, b);
    	return;
    }
}

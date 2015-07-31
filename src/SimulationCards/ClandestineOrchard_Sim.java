package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class ClandestineOrchard_Sim extends Simtemplate {
	//"id":336,"name":"Clandestine Orchard","description":"When a creature comes into play, another [random] creature on the same side has its Countdown decreased by 1."
	//linger 5

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 5;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
	    return;
	 }
	 
	    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
	    	//buff another minion
	    	if(summonedMinion.cardType == Kind.CREATURE)
	    	{
	    		ArrayList<Minion> creatures = new ArrayList<Minion>();
	    		for(Minion m : b.getPlayerFieldList(summonedMinion.position.color))
	    		{
	    			if(m==summonedMinion) continue;
	    			if(m.cardType == Kind.CREATURE) creatures.add(m);
	    		}
	    		if(creatures.size()==0) return;
	    		int random = b.getRandomNumber(0, creatures.size()-1);
	    		creatures.get(random).buffMinion(0, 0, -1, b);
	    	}
	        return;
	    }

}

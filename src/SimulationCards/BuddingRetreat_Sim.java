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

public class BuddingRetreat_Sim extends Simtemplate {
	//"id":335,"name":"Budding Retreat","description":"When a creature comes into play, increase owner's [current] Wild by 1."
	//linger 6

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 6;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		boolean isNew = b.addRule(playedCard);
	    return;
	 }
	 
	    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
	    	if(summonedMinion.cardType == Kind.CREATURE)
	    	{
	    		b.changeCurrentRessource(ResourceName.WILD, summonedMinion.position.color, 1);
	    	}
	        return;
	    }

}

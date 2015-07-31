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

public class BuddingVaettr_Sim extends Simtemplate {
	//"id":375,"name":"Budding Vaettr","description":"When a Beast comes into play on your side, increase [current] Growth by 1."

	
	 
	    public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
	    {
	    	if(summonedMinion.position.color == triggerEffectMinion.position.color && summonedMinion.getSubTypes().contains(SubType.Beast))
	    	{
	    		b.changeCurrentRessource(ResourceName.GROWTH, summonedMinion.position.color, 1);
	    	}
	        return;
	    }

}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class InvocationSentry_Sim extends Simtemplate {
	//"id":345,"name":"Invocation Sentry","description":"When a [Lingering] spell is played, a random opponent idol is dealt 2 damage."
	
	
    public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
    {
    	
    	if(spell.getSubTypes().contains(SubType.Lingering))
    	{
    	
    		//do dmg to random idol
    		ArrayList<Minion> idols = new ArrayList<Minion>();
    		UColor oppcol = Board.getOpposingColor(triggerEffectMinion.position.color);
    		for(Minion idol : b.getPlayerIdols(oppcol))
    		{
    			if(idol.Hp>=1) idols.add(idol);
    		}
    		if( idols.size()==0) return;
	
    		int randomint = b.getRandomNumber(0, idols.size()-1);
				
    		b.doDmg(b.getPlayerIdol(oppcol, randomint), triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
    	
    	}
    	return;
    }
	

	

}

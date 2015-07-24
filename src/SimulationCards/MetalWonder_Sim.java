package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.ResourceName;
import BattleStuff.SubType;

public class MetalWonder_Sim extends Simtemplate {
	//"id":210,"name":"Metal Wonder","description":"When your opponent plays a spell or enchantment, Metal Wonder deals 2 damage to a random idol they control."
	
	public void onPlayerPlayASpellOrEnchantment(Board b ,Minion triggerEffectMinion, Minion spell)
    {
		if(triggerEffectMinion.position.color == spell.position.color)  return; //opponent have to play the spell
		
		ArrayList<Minion> idols = new ArrayList<Minion>();
		UColor oppcol = Board.getOpposingColor(triggerEffectMinion.position.color);
		for(Minion idol : b.getPlayerIdols(oppcol))
		{
			if(idol.Hp>=1) idols.add(idol);
		}
		if( idols.size()==0) return;
	
		int randomint = b.getRandomNumber(0, idols.size()-1);
				
		b.doDmg(b.getPlayerIdol(oppcol, randomint), triggerEffectMinion, 2, AttackType.UNDEFINED, DamageType.SUPERIOR);
        return;
    }
	
	
}

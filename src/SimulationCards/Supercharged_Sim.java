package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class Supercharged_Sim extends Simtemplate 
{

	//"id":225,"name":"Supercharged","description":"When Supercharged comes into play, and at the beginning of each of enchanted unit's turns, enchanted unit makes a ranged attack dealing 1 [physical damage]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	private Minion getRangedTarget(Board b, Minion m)
	{
		Color oppcol = Board.getOpposingColor(m.position.color);
		
		for(int i=0;i<3;i++)
		{
			Minion mnn = b.getMinionOnPosition(new Position(oppcol, m.position.row, i));
			if(mnn!=null) return mnn;
		}
		
		
		return b.getPlayerIdol(oppcol, m.position.row);
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		target.addCardAsEnchantment("ENCHANTMENT", "Supercharged", playedCard.card.cardDescription, playedCard, b);
		b.doDmg(this.getRangedTarget(b, target), target, 1, AttackType.RANGED, DamageType.PHYSICAL);
		
        return;
    }
	
	 public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, Color turnStartColor)
	 {
		 b.doDmg(this.getRangedTarget(b, triggerEffectMinion), triggerEffectMinion, 1, AttackType.RANGED, DamageType.PHYSICAL);
	     return;
	 }
	
	
	
}

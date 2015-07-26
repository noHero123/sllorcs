package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Electrify_Sim extends Simtemplate 
{

	//"id":317,"name":"Electrify","description":"Each structure you control makes a ranged attack dealing 2 [physical damage]."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	private Minion getRangedTarget(Board b, Minion m)
	{
		UColor oppcol = Board.getOpposingColor(m.position.color);
		
		for(int i=0;i<3;i++)
		{
			Minion mnn = b.getMinionOnPosition(new UPosition(oppcol, m.position.row, i));
			if(mnn!=null) return mnn;
		}
		
		
		return b.getPlayerIdol(oppcol, m.position.row);
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion target : b.getPlayerFieldList(playedCard.position.color))
		{
			if(target.cardType == Kind.STRUCTURE)
			{
				b.doDmg(this.getRangedTarget(b, target), target, 2, AttackType.RANGED, DamageType.PHYSICAL);
			}
		}
		
        return;
    }
	
	
	
	
}

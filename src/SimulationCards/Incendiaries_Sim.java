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

public class Incendiaries_Sim extends Simtemplate {
	//"id":143,"name":"Incendiaries","description":"Deal 3 [magic damage] to all opponent structures."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		UColor opcol = Board.getOpposingColor(playedCard.position.color);
		ArrayList<Minion> all = new ArrayList<Minion>();
		
		for(Minion m : b.getPlayerFieldList(opcol))
		{
			if(m.cardType == Kind.STRUCTURE) 
				{
					all.add(m);
				}
		}
		
		b.doDmg(all, playedCard, 3, AttackType.UNDEFINED, DamageType.MAGICAL);//dmg=-100 => aoedmg :D
		
        return;
    }
	
}

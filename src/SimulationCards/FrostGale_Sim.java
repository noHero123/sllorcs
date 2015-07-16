package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.subType;
import BattleStuff.tileSelector;

public class FrostGale_Sim extends Simtemplate {
	//"id":84,"name":"Frost Gale","description":"Deal 1 [magic damage] to all units."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		Color opp = Board.getOpposingColor(b.activePlayerColor);
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(opp));
		all.addAll(b.getPlayerFieldList(b.activePlayerColor));
		
		b.doDmg(all, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
}

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

public class FrostGale_Sim extends Simtemplate {
	//"id":84,"name":"Frost Gale","description":"Deal 1 [magic damage] to all units."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> all = new ArrayList<Minion>(b.getAllMinionOfField());
		
		b.doDmg(all, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
}

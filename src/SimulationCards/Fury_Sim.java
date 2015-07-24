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

public class Fury_Sim extends Simtemplate {
	//"id":77,"name":"Fury","description":"All units have their Countdown decreased by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> all = new ArrayList<Minion>(b.getAllMinionOfField());
		for(Minion m : all)
		{
			m.buffMinion(0, 0, -1, b);
		}
        return;
    }
	
}

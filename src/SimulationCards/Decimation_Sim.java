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

public class Decimation_Sim extends Simtemplate {
	//"id":116,"name":"Decimation","description":"Units on target row are dealt 1 [magic damage]. Opponent's idol on that row is dealt 2 damage."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all; //rowfullidols is done automatically (by client):D
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> alle = new ArrayList<Minion>(b.getAllMinionOfField());
		ArrayList<Minion> all = new ArrayList<Minion>();
		int row = targets.get(0).row;
		for(Minion m : alle)
		{
			if(m.position.row == row)
			{
				m.aoeDmgToDo=1;
				all.add(m);
			}
		}
		
		Minion idol = b.getPlayerIdol(Board.getOpposingColor(playedCard.position.color),row);
		idol.aoeDmgToDo=2;
		all.add(idol);
		
		b.doDmg(all, playedCard, -100, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class UnderdogsSpite_Sim extends Simtemplate {
	//"id":295,"name":"Underdog's Spite","description":"Creatures you control with Attack 2 or less have their Countdown decreased by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.cardType == Kind.CREATURE && m.getAttack() <=2)
			{
				m.buffMinion(0, 0, -1, b);
			}
		}
        return;
    }
	
}

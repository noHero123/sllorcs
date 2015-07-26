package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class EyeOfEagle_Sim extends Simtemplate {
	//id":89,"name":"Eye of Eagle","description":"Draw 1 creature scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		b.drawSpecialCard(playedCard.position.color, Kind.CREATURE);
		
        return;
    }
	
}

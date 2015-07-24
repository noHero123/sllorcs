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

public class Transposition_Sim extends Simtemplate {
	//"id":62,"name":"Transposition","description":"Switch places of two units you control. Draw 1 scroll."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_units;
	}
	
	public tileSelector getTileSelectorForSecondSelection()
	{
		return tileSelector.own_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		UPosition p1=targets.get(0);
		UPosition p2=targets.get(1);
		String sp1=p1.posToString();
		String sp2=p2.posToString();
		String s = "{\"TeleportUnits\":{\"units\":[{\"from\":"+sp1+",\"to\":"+sp2+"},{\"from\":"+sp2+",\"to\":"+sp1+"}]}}";
		b.addMessageToBothPlayers(s);
		b.unitChangesPlace(p1, p2, true, false);	
		b.drawCards(playedCard.position.color, 1);
        return;
    }
	
}

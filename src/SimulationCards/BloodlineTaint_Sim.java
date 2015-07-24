package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class BloodlineTaint_Sim extends Simtemplate {
	//"id":252,"name":"Bloodline Taint","description":"Target unit, and other units on the same side that share a subtype, get [Curse] 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		ArrayList<Minion> field = b.getPlayerFieldList(target.position.color);
		
		for(Minion m : field)
		{
			m.aoeDmgToDo=0;
		}
		
		for(SubType st : target.getSubTypes())
		{
			for(Minion m : field)
			{
				for(SubType stm : m.getSubTypes())
				{
					if(st==stm) m.aoeDmgToDo=1;
				}
			}
		}
		
		for(Minion m : field)
		{
			if(m.aoeDmgToDo==1)
			{
				m.addnewCurse(b, playedCard.position.color, 1);
			}
			m.aoeDmgToDo=0;//unmark
		}
		
		
		
        return;
    }
	
	
}

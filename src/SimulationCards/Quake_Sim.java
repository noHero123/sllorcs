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

public class Quake_Sim extends Simtemplate {
	//"id":34,"name":"Quake","description":"Deal 3 [magic damage] to all structures and 2 [magic damage] to all creatures."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public int getGrowthCost(Board b ,Minion m)
    {
		int tax = b.whiteQuakeTaxing;
		if(m.position.color == Color.black) tax = b.blackQuakeTaxing;
    	return m.card.costGrowth + tax;
    }
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> all = new ArrayList<Minion>(b.getAllMinionOfField());
		for(Minion m : all)
		{
			if(m.cardType == Kind.CREATURE) m.aoeDmgToDo = 2;
			if(m.cardType == Kind.STRUCTURE) m.aoeDmgToDo = 3;
		}
		
		b.doDmg(all, playedCard, -100, AttackType.UNDEFINED, DamageType.MAGICAL);//dmg=-100 => aoedmg :D
		if(playedCard.position.color == Color.white)
		{
			b.whiteQuakeTaxing+=2;
		}
		else
		{
			b.blackQuakeTaxing+=2;
		}
        return;
    }
	
}

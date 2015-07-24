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

public class EndOfReason_Sim extends Simtemplate {
	//"id":81,"name":"End of Reason","description":"Sacrifice all units you control with a non-zero Countdown. Each unit sacrificed this way deals 2 [magic damage] to opponent units on the same row."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		

		UColor oppcol = Board.getOpposingColor(playedCard.position.color);
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(playedCard.position.color));
		int[] dmgrow = {0,0,0,0,0};
		
		for(Minion m :  all)
		{
			if(m.getAc()!=0)
			{
				b.destroyMinion(m, playedCard);
				dmgrow[m.position.row] +=1;
			}
		}
		
		for(int i=0;i < 5;i++)
		{
			for(int j=0;j < dmgrow[i];j++)
			{
				ArrayList<Minion> allop = new ArrayList<Minion>(b.getPlayerFieldList(oppcol));
				ArrayList<Minion> dmgop = new ArrayList<Minion>();
				for(Minion m : allop)
				{
					if(m.position.row == i)
					{
						m.aoeDmgToDo=2;
						dmgop.add(m);
					}
				}
				
				b.doDmg(dmgop, playedCard, -100, AttackType.UNDEFINED, DamageType.MAGICAL);//dmg=-100 => aoedmg :D
			}
		}
        return;
    }
	
}

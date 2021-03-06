package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.TargetAreaGroup;
import BattleStuff.tileSelector;

public class ThunderSurge_Sim extends Simtemplate
{
	
	//"id":25,"name":"Thunder Surge","description":"Target unit and connected units are dealt 2 [magic damage]."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public TargetAreaGroup getTargetAreaGroup()
	{
		return TargetAreaGroup.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		ArrayList<ArrayList<UPosition>> sequentials = b.getSequentialPositions(UColor.white, this.getTargetAreaGroup());
		sequentials.addAll(b.getSequentialPositions(UColor.black,this.getTargetAreaGroup()));
		
		UPosition selected = targets.get(0);
		System.out.println(selected.posToString());
		ArrayList<Minion> mins = new ArrayList<Minion>();
		
		for(ArrayList<UPosition> pos : sequentials )
		{
			boolean inpos = false;
			//does contains work ? 
			System.out.println("seqental");
			for(UPosition po : pos)
			{
				System.out.println(po.posToString());
				if(po.isEqual(selected))
				{
					inpos = true;
				}
			}
			
			if(inpos)
			{
				mins.addAll(b.getMinionsFromPositions(pos));
			}
		}
		
		for(Minion t : mins)
		{
			t.aoeDmgToDo=2;
		}
		b.doDmg(mins, playedCard, -100, AttackType.UNDEFINED, DamageType.MAGICAL); 
        return;
    }
	
}

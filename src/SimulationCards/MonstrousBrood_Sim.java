package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.TargetAreaGroup;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class MonstrousBrood_Sim extends Simtemplate
{
	
	//id":246,"name":"Monstrous Brood","description":"Target creature you control and connected creatures are destroyed, and each is replaced by a <Monstrosity>.
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_creatures;
	}
	
	public TargetAreaGroup getTargetAreaGroup()
	{
		return TargetAreaGroup.own_creatures;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		ArrayList<ArrayList<Position>> sequentials = b.getSequentialPositions(Color.white, this.getTargetAreaGroup());
		sequentials.addAll(b.getSequentialPositions(Color.black, this.getTargetAreaGroup()));
		
		Position selected = targets.get(0);
		System.out.println(selected.posToString());
		ArrayList<Minion> mins = new ArrayList<Minion>();
		ArrayList<Position> poses = new ArrayList<Position>();
		
		for(ArrayList<Position> pos : sequentials )
		{
			boolean inpos = false;
			//does contains work ? 
			System.out.println("seqental");
			for(Position po : pos)
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
		
		
		for(Minion m : mins)
		{
			poses.add(new Position(m.position));
			//we do a clever trick: add a monstrosity on that position before it is killed, do other summon effects wont work on that tile!
			Card c = CardDB.getInstance().cardId2Card.get(247);
			Minion ill = new Minion(c, -1, m.position.color);
			b.summonList.add(b.new SummonItem(ill, new Position(m.position)));
			b.destroyMinion(m, playedCard);
		}
		
        return;
    }
	
}

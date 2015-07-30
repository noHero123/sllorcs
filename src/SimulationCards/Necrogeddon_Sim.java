package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class Necrogeddon_Sim extends Simtemplate {
	//"id":188,"name":"Necrogeddon","description":"Sacrifice all creatures you control. Creatures sacrificed this way are replaced by <Husks> at Countdown 0."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		//TODO: ignores resummon effects of own side! (seed of insurgent, illthorn seed, witchdoc...) (all tested)
		
		ArrayList<Minion> all = new ArrayList<Minion>();
		for(Minion m : b.getPlayerFieldList(b.activePlayerColor))
		{
			if(m.cardType == Kind.CREATURE) all.add(m);
		}
		ArrayList<UPosition> poses = new ArrayList<UPosition>();
		
		for(Minion m : all)
		{
			poses.add(new UPosition(m.position));
			//we do a clever trick: add a husk on that position before it is killed, do other summon effects wont work on that tile!
			Card c = CardDB.getInstance().cardId2Card.get(163);
			Minion ill = new Minion(c, -1, m.position.color);
			b.addItemToSummonList(b.new SummonItem(ill, new UPosition(m.position)));
			b.destroyMinion(m, playedCard);
		}
		
		/*for(Position p : poses)
		{
			Card c = CardDB.getInstance().cardId2Card.get(163);
			Minion ill = new Minion(c, -1, p.color);
			b.summonUnitOnPosition(p, ill);
		}*/
		
		
		for(Minion m : b.getMinionsFromPositions(poses))
		{
			if(m.typeId == 163)
			{
				m.buffMinion(0, 0, -10, b);
			}
		}
		
        return;
    }
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class SomataShift_Sim extends Simtemplate
{
	
	//"id":377,"name":"Somata Shift","description":"Summon a random creature scroll from your library on target tile. Its Health is set to 2."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.own_free;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = null;
		
		ArrayList<Minion> deck = b.getPlayerDeck(playedCard.position.color);
		for(Minion m : deck)
		{
			if(m.cardType == Kind.CREATURE) 
			{
				target=m;
				break;
			}
		}
        
		if(target == null) return;
		deck.remove(target);
		b.shuffleList(deck);
		
		b.summonUnitOnPosition(targets.get(0), target);
		//set hp to 2
		int hp = 2-target.Hp;
		if(hp!=0)target.buffMinion(0, hp, 0, b);
		
        return;
    }
	
}

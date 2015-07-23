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
import BattleStuff.SubType;
import BattleStuff.tileSelector;
import BattleStuff.Board.SummonItem;

public class Unbind_Sim extends Simtemplate {
	//"id":159,"name":"Unbind","description":"Destroy target enchanted unit."
	

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units_with_enchantments;
	}
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		
		//summon mire shambler
		Card c = CardDB.getInstance().cardId2Card.get(164);
		Minion ill = new Minion(c, -1, playedCard.position.color);
		
		ArrayList<Position> poses = b.getFreePositions(playedCard.position.color);
		if(poses.size()==0) return;
		
		int randomint = b.getRandomNumber(0, poses.size()-1);
		b.summonUnitOnPosition(poses.get(randomint), ill);
		
        return;
    }
	
}

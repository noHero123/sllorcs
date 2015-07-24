package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.Board.SummonItem;

public class CorpusCollector_Sim extends Simtemplate {
	//"id":271,"name":"Corpus Collector","description":"When Corpus Collector destroys a creature, summon a <Husk> with 2 Countdown on an adjacent tile."
	
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		if(triggerEffectMinion != attacker || diedMinion.cardType == Kind.CREATURE) return;
		
		//summon husk
		Card c = CardDB.getInstance().cardId2Card.get(163);
		Minion ill = new Minion(c, -1, triggerEffectMinion.position.color);
		
		ArrayList<UPosition> freepos = b.getFreePositionsFromPosition(triggerEffectMinion.position.getNeightbours());
		
		if(freepos.size() ==0) return;
		int randint = b.getRandomNumber(0, freepos.size()-1);
		b.summonUnitOnPosition(freepos.get(randint), ill);
		int ac = ill.getAc();
		if(ac>2) 
		{
			int diff = 2 - ac; // < 0
			ill.buffMinion(0, 0, diff, b);
		}
		
		
		
        return;
    }

}

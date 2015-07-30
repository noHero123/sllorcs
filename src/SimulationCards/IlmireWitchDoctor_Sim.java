package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.Board.SummonItem;

public class IlmireWitchDoctor_Sim extends Simtemplate {
	//"id":166,"name":"Ilmire Witch Doctor","description":"When a Human creature adjacent to Ilmire Witch Doctor is destroyed, a <Husk> is summoned in its place."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.cardType == Kind.CREATURE && diedMinion.position.isNeightbour(triggerEffectMinion.position))
		{
			Card c = CardDB.getInstance().cardId2Card.get(163);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			b.addItemToSummonList(b.new SummonItem(ill, diedMinion.position));
		}
			
        return;
    }

	

}

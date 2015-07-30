package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.Board.SummonItem;
import BattleStuff.UPosition;

public class AnimaConduit_Sim extends Simtemplate {
	//"id":353,"name":"Anima Conduit","description":"Whenever a non-<Revenant> creature you control is destroyed, a Revenant is summoned adjacent to Anima Conduit, and Anima Conduit is dealt 1 [pure damage]."
	
	public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
    {
		
		if(diedMinion.position.color == triggerEffectMinion.position.color && diedMinion.cardType == Kind.CREATURE && diedMinion.typeId != 349)
		{
			Card c = CardDB.getInstance().cardId2Card.get(349);
			Minion ill = new Minion(c, -1, diedMinion.position.color);
			
			ArrayList<UPosition> poses = b.getFreePositionsFromPosition(triggerEffectMinion.position.getNeightbours());
			if(poses.size()==0) return;
			
			int random = b.getRandomNumber(0, poses.size()-1);
			
			b.addItemToSummonList(b.new SummonItem(ill, poses.get(random)));
			b.doDmg(triggerEffectMinion, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		}
			
        return;
    }

	

}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class SisterOfTheOwl_Sim extends Simtemplate {
	//"id":222,"name":"Sister of the Owl","description":"An <Owl> is summoned together with Sister of the Owl. When an Owl deals damage, Sister of the Owl gets +1 Attack.","flavor":"Knowing and nocturnal."
	// only on adj. tiles an owl could be summoned!
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		
		UPosition center = own.position;
		ArrayList<UPosition> temp = center.getNeightbours(); 
		temp.add(center);
		ArrayList<UPosition> freenbrs  = b.getFreePositionsFromPosition(temp); 
		if(freenbrs.size()==0) return;
	
		int rndm = b.getRandomNumber(0, freenbrs.size()-1);
		Card c = CardDB.getInstance().cardId2Card.get(189);
		Minion ill = new Minion(c, -1, own.position.color);
		b.summonUnitOnPosition(freenbrs.get(rndm), ill);
        return;
    }
	
	public  void onMinionDidDmgTrigger(Board b, Minion triggerEffectMinion, Minion damagedMinion, Minion attacker, int dmgdone, AttackType attackType, DamageType dmgtype)
    {
		if(dmgdone <=0 || attacker.position.color != triggerEffectMinion.position.color) return;
		if(attacker.typeId == 221)
		{
			triggerEffectMinion.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	
}

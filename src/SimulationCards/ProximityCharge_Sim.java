package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class ProximityCharge_Sim extends Simtemplate {
	//"id":90,"name":"Proximity Charge","description":"When destroyed in [melee combat], attacking unit is dealt 4 [physical damage]. When Proximity Charge comes into play, a copy is summoned adjacent to it."
	
	//tested: its dmg is done before spiky dmg: "attackType":"UNDEFINED","damageType":"COMBAT"
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		//summon another proximity!
		if(target.isToken) return;
		
		Card c = CardDB.getInstance().cardId2Card.get(90);
		Minion ill = new Minion(c, -1, own.position.color);
		//TODO : unefficient ;D
		ArrayList<Position> poses = own.position.getNeightbours(); //own.position is not included, so new minion will not spawn there!
		
		ArrayList<Minion> mins = b.getMinionsFromPositions(poses);
		
		ArrayList<Position> isp = new ArrayList<Position>();
		
		for(Position pp : poses)
		{
			Boolean isEmpty = true;
			for(Minion mi : mins)
			{
				if(mi.position.isEqual(pp))
				{
					isEmpty=false;
				}
			}
			
			if(isEmpty) isp.add(pp);
		}
		
		if(isp.size() == 0) return;
		
		int randomint = b.getRandomNumber(0, isp.size()-1);
		
		b.summonUnitOnPosition(isp.get(randomint), ill);
		
        return;
    }
	
}

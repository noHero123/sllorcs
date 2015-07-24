package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class BalefulWitch_Sim extends Simtemplate {
	//"id":272,"name":"Baleful Witch","description":"When Baleful Witch comes into play, any [Curses] on creatures in play are [triggered]."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getAllMinionOfField())
		{
			if(m.Hp>=1 && m.curse>=1)
			{
				b.doDmg(m, own, m.curse, AttackType.UNDEFINED, DamageType.SUPERIOR);
			}
		}
		
    }
	

}

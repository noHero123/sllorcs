package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class AncestralTotem_Sim extends Simtemplate {
	//"id":65,"name":"Ancestral Totem","description":"Creatures you control have +1 Attack."
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.card.cardKind == Kind.CREATURE)
			{
				m.buffMinion(1, 0, 0, b);
			}
		}
        return;
    }
	
	public  void onMinionIsSummoned(Board b, Minion triggerEffectMinion, Minion summonedMinion)
    {
		if(summonedMinion.position.color != triggerEffectMinion.position.color) return; //only buff opp. minions
		
		if(summonedMinion.position.isEqual(triggerEffectMinion.position)) return; //dont buff himself
		
		if(summonedMinion.card.cardKind == Kind.CREATURE)
		{
			summonedMinion.buffMinion(1, 0, 0, b);
		}
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.card.cardKind == Kind.CREATURE)
			{
				m.buffMinion(-1, 0, 0, b);
			}
		}
        return;
    }
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class DamningCurse_Sim extends Simtemplate {
	//"id":180,"name":"Damning Curse","description":"Destroy target creature. Units you control are dealt 1 [magic damage]."
	
	public int getDecayCost(Board b ,Minion m)
    {
		int tax = b.whiteDamningTaxing;
		if(m.position.color == UColor.black) tax = b.blackDamningTaxing;
    	return m.card.costDecay + tax;
    }
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_creatures;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		Minion target = b.getMinionOnPosition(targets.get(0));
		b.destroyMinion(target, playedCard);
		
		ArrayList<Minion> all = new ArrayList<Minion>(b.getPlayerFieldList(playedCard.position.color));
		
		b.doDmg(all, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
		if(playedCard.position.color == UColor.white)
		{
			b.whiteDamningTaxing+=1;
		}
		else
		{
			b.blackDamningTaxing+=1;
		}
		
		int cost = this.getDecayCost(b, playedCard);
		String s ="{\"CostUpdate\":{\"profileId\":"+b.getPlayer(player).profileId+",\"costs\":[{\"cardTypeId\":180,\"cost\":"+cost+"}]}}";
		b.addMessageToBothPlayers(s);
        return;
    }
	
}

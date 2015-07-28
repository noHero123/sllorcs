package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.ResourceName;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class StoneEnigma_Sim extends Simtemplate
{
	
	//"id":347,"name":"Stone Enigma","description":"All [Lingering] spells are destroyed. Increase Energy by 1."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		ArrayList<Minion> rules = new ArrayList<Minion>(b.getPlayerRules(playedCard.position.color));
		for(Minion rule : rules)
		{
			b.ruleCountDown(rule, 1000);
		}
        
		b.changeMaxRessource(ResourceName.ENERGY, playedCard.position.color, 1);
		
        return;
    }
	
}

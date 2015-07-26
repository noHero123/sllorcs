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

public class SuddenEruption_Sim extends Simtemplate
{
	
	//"id":288,"name":"Sudden Eruption","description":""
	//"Surge: 2 [magic damage] to X random opponent units."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	//TODO is sudden eruption really that random?
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		int surge = b.getCurrentRessource(ResourceName.ENERGY, playedCard.position.color);
		b.changeCurrentRessource(ResourceName.ENERGY, playedCard.position.color, -surge);
		UColor col = Board.getOpposingColor(playedCard.position.color);
		for(int i=0; i< surge;i++)
		{
			ArrayList<Minion> mins = b.getPlayerFieldList(col);
			if(mins.size() == 0) return;
			//choose random target
			int random = b.getRandomNumber(0, mins.size()-1);
			
			b.doDmg(mins.get(random), playedCard, 2, AttackType.UNDEFINED, DamageType.MAGICAL);
		}
		
		
        
        return;
    }
	
}

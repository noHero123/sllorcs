package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.SiftItem;
import BattleStuff.SiftPlace;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class AdvantageousOutlook_Sim extends Simtemplate {

	//"id":384,"name":"Advantageous Outlook","description":"[Sift] 3 scrolls from your library, and draw 1 of them. If it is a spell, Knights you control get +2 Attack until end of turn."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	

	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		 

		 	ArrayList<SiftItem> siftcards = b.whiteShiftCards;
		 	if(playedCard.position.color == UColor.black) siftcards = b.blackShiftCards;
		 	siftcards.clear();
		 	
		 	ArrayList<Minion> grave = b.getPlayerDeck(playedCard.position.color);//we shift from player deck this time :D
		 	if(grave.size() == 0) return;//TESTED! it doesnt shuffle the graveyard in!
		 	int iter = Math.min(3, grave.size());
		 	
		 	for(int i = 0; i< iter; i++)
		 	{
		 		if(grave.size()>=i)break;
		 		int rand = b.getRandomNumber(0, grave.size()-1);
		 		SiftItem si = new SiftItem(grave.get(rand), SiftPlace.LIBRARY, SiftPlace.HAND);
		 		siftcards.add(si);
		 		grave.remove(rand);
		 		i--;
		 	}
		 	
		 	//send cardstack update
		 	String s= b.getCardStackUpdate(playedCard.position.color);
			b.addMessageToBothPlayers(s);
			
			s = b.getSiftMessage(playedCard.position.color);
			b.addMessageToPlayer(playedCard.position.color, s);
			b.doAdvantageousOutlookEffect=true;
		 	
	        return;
	    }
	 
	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	    {
			//if()
		 	if(triggerEffectMinion.owner == null) return false;
		 	triggerEffectMinion.owner.buffMinionWithoutMessage(-2, 0, 0, b);
	        return true;//buff is removed, so we return true
	    }
	 

}

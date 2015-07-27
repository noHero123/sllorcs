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

public class HallsOfOumLasa_Sim extends Simtemplate {
	//"id":343,"name":"Halls of Oum Lasa","description":"At the beginning of your turn, [sift] 3 random scrolls from your discard pile, and draw 1 of them."
	//linger 4

	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	 public int getLingerDuration(Board b ,Minion m)
	 {
		 return 4;
	 }
	 
	 public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
	 {
		b.addRule(playedCard);
	    return;
	 }
	 
	 public  void onTurnStartTrigger(Board b, Minion triggerEffectMinion, UColor turnStartColor)
	    {
		 	if(triggerEffectMinion.position.color != turnStartColor || b.getPlayerGrave(triggerEffectMinion.position.color).size()==0) return;
		 	
		 	ArrayList<SiftItem> siftcards = b.whiteShiftCards;
		 	if(triggerEffectMinion.position.color == UColor.black) siftcards = b.blackShiftCards;
		 	siftcards.clear();
		 	
		 	ArrayList<Minion> grave = b.getPlayerGrave(triggerEffectMinion.position.color);
		 	if(grave.size() == 0) return;
		 	int iter = Math.min(3, grave.size());
		 	
		 	for(int i = 0; i< iter; i++)
		 	{
		 		if(grave.size() == 0) break;
		 		int rand = b.getRandomNumber(0, grave.size()-1);
		 		SiftItem si = new SiftItem(grave.get(rand), SiftPlace.GRAVEYARD, SiftPlace.HAND);
		 		siftcards.add(si);
		 		grave.remove(rand);
		 	}
		 	
		 	//send cardstack update
		 	String s= b.getCardStackUpdate(triggerEffectMinion.position.color);
			b.addMessageToBothPlayers(s);
			
			s = b.getSiftMessage(triggerEffectMinion.position.color);
			b.addMessageToPlayer(triggerEffectMinion.position.color, s);
		 	
	        return;
	    }
	 

}

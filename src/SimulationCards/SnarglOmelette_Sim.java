package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.SiftItem;
import BattleStuff.SiftPlace;
import BattleStuff.UColor;
import BattleStuff.UPosition;
import BattleStuff.SubType;

public class SnarglOmelette_Sim extends Simtemplate {
	//"id":383,"name":"Snargl Omelette","description":"Pay 2 Energy: [Sift] 2 Gravelocks from your library, and draw 1 of them. Use this ability only if you have 0 scrolls in hand."
	
	
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<UPosition> targets )
    {
		
		
		ArrayList<SiftItem> siftcards = b.whiteShiftCards;
	 	if(triggerEffectMinion.position.color == UColor.black) siftcards = b.blackShiftCards;
	 	siftcards.clear();
	 	
	 	ArrayList<Minion> library = b.getPlayerDeck(triggerEffectMinion.position.color);
	 	if(library.size() == 0) return;
	 	int iter = library.size();
	 	int added = 0;
	 	for(int i = 0; i< iter; i++)
	 	{
	 		if(library.size()>=i || added >= 2)break;

	 		if(library.get(i).getSubTypes().contains(SubType.Gravelock))
	 		{
	 			SiftItem si = new SiftItem(library.get(i), SiftPlace.LIBRARY, SiftPlace.HAND);
	 			siftcards.add(si);
	 			library.remove(i);
	 			i--;
	 			added++;
	 		}
	 	}
	 	
	 	//send cardstack update
	 	String s= b.getCardStackUpdate(triggerEffectMinion.position.color);
		b.addMessageToBothPlayers(s);
		
		s = b.getSiftMessage(triggerEffectMinion.position.color);
		b.addMessageToPlayer(triggerEffectMinion.position.color, s);
		
        return;
    }
	

}

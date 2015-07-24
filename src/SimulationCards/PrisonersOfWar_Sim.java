package SimulationCards;

import java.util.ArrayList;

import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.ResourceName;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class PrisonersOfWar_Sim extends Simtemplate {
	//"id":316,"name":"Prisoners of War","description":"Draw 1 Growth, 1 Decay and 1 Energy creature. Increase [current] Wild by 1."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		
		//draw creature scroll
		ArrayList<Minion> deck = new ArrayList<Minion>(b.currentDeck);
		Boolean foundg = false;
		Boolean foundd = false;
		Boolean founde = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.card.cardKind == Kind.CREATURE)
			{
				if(m.card.costDecay>=1 && !foundd)
				{
					foundd=true;
					b.currentHand.add(m);
					deck.remove(i);
					continue;
				}
				if(m.card.costGrowth>=1 && !foundg)
				{
					foundg=true;
					b.currentHand.add(m);
					deck.remove(i);
					continue;
				}
				if(m.card.costEnergy>=1 && !founde)
				{
					founde=true;
					b.currentHand.add(m);
					deck.remove(i);
					continue;
				}
				
			}
		}
		if(foundg || foundd || founde)
		{
			b.shuffleList(b.currentDeck);
		}
		//look into graveyard for more
		if(!foundg || !foundd ||! founde)
		{
			deck.clear();
			deck.addAll(b.currentGrave);
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.card.cardKind == Kind.CREATURE)
				{
					if(m.card.costDecay>=1 && !foundd)
					{
						foundd=true;
						b.currentHand.add(m);
						deck.remove(i);
						b.shuffleList(deck);
						continue;
					}
					if(m.card.costGrowth>=1 && !foundg)
					{
						foundg=true;
						b.currentHand.add(m);
						deck.remove(i);
						b.shuffleList(deck);
						continue;
					}
					if(m.card.costEnergy>=1 && !founde)
					{
						founde=true;
						b.currentHand.add(m);
						deck.remove(i);
						b.shuffleList(deck);
						continue;
					}
					
				}
			}
			
			b.shuffleList(b.currentGrave);
		}
		
		if(foundg || foundd || founde)
		{
			//hand+ cardstack update
			b.addMessageToPlayer(b.activePlayerColor, b.getHandUpdateMessage(b.activePlayerColor));
			b.addMessageToBothPlayers(b.getCardStackUpdate(b.activePlayerColor));
		}
		
		b.changeCurrentRessource(ResourceName.WILD, playedCard.position.color, 1);
		
        return;
    }
	
}

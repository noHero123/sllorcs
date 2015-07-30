package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.SubType;
import BattleStuff.tileSelector;

public class EssenceFeast_Sim extends Simtemplate {
	//"id":56,"name":"Essence Feast","description":"Idols you control are healed by 1. Beasts get +2 Attack until end of turn. Draw 1 Beast."
	
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.None;
	}
	
	public Boolean isEffect(Minion m)
    {
		if(m.owner!=null) return true;
        return false;
    }
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		for(Minion m : b.getPlayerIdols(playedCard.position.color))
		{
			m.healMinion(1, b);
		}
		
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.getAc()>=0 && m.getSubTypes().contains(SubType.Beast))
			{
				m.buffMinionWithoutMessage(2, 0, 0, b);
				m.addnewEnchantments("BUFF", "Essence Feast", playedCard.card.cardDescription, playedCard.card, b, playedCard.position.color);
			}
		}
		
		//draw beast scroll
		UColor col = playedCard.position.color;
		ArrayList<Minion> playerdeck = b.getPlayerDeck(col);
		ArrayList<Minion> hand = b.getPlayerHand(col);
		ArrayList<Minion> deck = new ArrayList<Minion>(playerdeck);
		Boolean found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.getSubTypes().contains(SubType.Beast))
			{
				found=true;
				hand.add(m);
				playerdeck.remove(m);
				b.shuffleList(playerdeck);
				break;
			}
		}
		
		//look into graveyard
		if(!found)
		{
			playerdeck = b.getPlayerGrave(col);
			deck.clear();
			deck.addAll(playerdeck);
			
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.getSubTypes().contains(SubType.Beast))
				{
					found=true;
					hand.add(m);
					playerdeck.remove(m);
					b.shuffleList(playerdeck);
					break;
				}
			}
		}
		
		if(found)
		{
			//hand+ cardstack update
			b.addMessageToPlayer(col, b.getHandUpdateMessage(col));
			b.addMessageToBothPlayers(b.getCardStackUpdate(col));
		}
		
        return;
    }
	
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-2, 0, 0, b);
        return;
    }
	
	public Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		if(triggerEffectMinion.owner== null) return false;
        return true;//buff is removed, so we return true
    }
}

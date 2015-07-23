package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Card;
import BattleStuff.CardDB;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.SubType;

public class EarthbornMystic_Sim extends Simtemplate {
	//"id":302,"name":"Earthborn Mystic","description":"Enchanted creatures you control have +1 Attack. When Earthborn Mystic's Countdown is 0, you may reset its Countdown to draw an enchantment scroll. If you do, Earthborn Mystic is dealt 1 [pure damage]."
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.hasEnchantment())
			{
				m.buffMinion(1, 0, 0, b);
			}
		}
        return;
    }
	
	public  void onAbilityIsActivated(Board b, Minion triggerEffectMinion, ArrayList<Position> targets )
    {
		triggerEffectMinion.resetAcWithMessage(b);
		b.doDmg(triggerEffectMinion, triggerEffectMinion, 1, AttackType.UNDEFINED, DamageType.SUPERIOR);
		
		//draw creature scroll
				ArrayList<Minion> deck = new ArrayList<Minion>(b.currentDeck);
				Boolean found = false;
				for(int i=0; i<deck.size(); i++)
				{
					Minion m= deck.get(i);
					if(m.card.cardKind == Kind.ENCHANTMENT)
					{
						found=true;
						b.currentHand.add(m);
						deck.remove(i);
						b.shuffleList(deck);
						break;
					}
				}
				
				//look into graveyard
				if(!found)
				{
					deck.clear();
					deck.addAll(b.currentGrave);
					for(int i=0; i<deck.size(); i++)
					{
						Minion m= deck.get(i);
						if(m.card.cardKind == Kind.ENCHANTMENT)
						{	
							found=true;
							b.currentHand.add(m);
							deck.remove(i);
							b.shuffleList(deck);
							break;
						}
					}
				}
				
				if(found)
				{
					//hand+ cardstack update
					b.addMessageToPlayer(b.activePlayerColor, b.getHandUpdateMessage(b.activePlayerColor));
					b.addMessageToBothPlayers(b.getCardStackUpdate(b.activePlayerColor));
				}
		
		
        return;
    }
	
	public  void onMinionLeavesBattleField(Board b, Minion auraendminion)
    {
		for(Minion m : b.getPlayerFieldList(auraendminion.position.color))
		{
			if(m.hasEnchantment())
			{
				m.buffMinion(-1, 0, 0, b);
			}
		}
        return;
    }
	
	public  void onUnitGotEnchantment(Board b, Minion triggerEffectMinion, Minion minion, boolean isItsFirstEnchantment )
    {
		if(triggerEffectMinion.position.color != minion.position.color || !isItsFirstEnchantment) return;
		minion.buffMinion(1, 0, 0, b);
        return;
    }
    
    public  void onUnitLostAllEnchantments(Board b, Minion triggerEffectMinion, Minion minion )
    {
    	if(triggerEffectMinion.position.color != minion.position.color) return;
    	minion.buffMinion(-1, 0, 0, b);
        return;
    }
    
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.DamageType;
import BattleStuff.Kind;
import BattleStuff.Minion;
import BattleStuff.Position;
import BattleStuff.tileSelector;

public class GolemSkin_Sim extends Simtemplate
{
	
	//"id":83,"name":"Golem Skin","description":"When Golem Skin comes into play, enchanted creature gains Attack and Health equal to the number of structures you control. Structures you control take 1 [magic damage]."
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	
	public void onCardPlay(Board b, Color player , ArrayList<Position> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		
		ArrayList<Minion> all = new ArrayList<Minion>();
		for(Minion m : b.getPlayerFieldList(playedCard.position.color))
		{
			if(m.cardType == Kind.STRUCTURE) all.add(m);
		}
		
		int buff = all.size();
		
		if(buff>=1)
		{
			target.buffMinionWithoutMessage(buff, buff, 0, b);
		}
		
		target.addCardAsEnchantment("ENCHANTMENT", "Golem Skin", playedCard.card.cardDescription, playedCard, b);//TODO is carddescription realy so long=?
		
		
		b.doDmg(all, playedCard, 1, AttackType.UNDEFINED, DamageType.MAGICAL);
		
        return;
    }
	
	
}

package SimulationCards;

import java.util.ArrayList;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.UColor;
import BattleStuff.Minion;
import BattleStuff.UPosition;
import BattleStuff.tileSelector;

public class Redesign_Sim extends Simtemplate 
{

	//"id":145,"name":"Redesign","description":"Target creature's Attack and Health switch values."
	
	public tileSelector getTileSelectorForFirstSelection()
	{
		return tileSelector.all_units;
	}
	
	public void onCardPlay(Board b, UColor player , ArrayList<UPosition> targets, Minion playedCard)
    {
		Minion target = b.getMinionOnPosition(targets.get(0));
		int attack = target.getAttack(b);
		int hp = target.Hp;
		int abuff = hp-attack;
		int hbuff = attack-hp;
		playedCard.attackbuff = abuff;
		playedCard.healthbuff = hbuff;
		target.buffMinionWithoutMessage(abuff, hbuff, 0, b);
		
		target.addCardAsEnchantment("ENCHANTMENT", "Redisign", playedCard.card.cardDescription, playedCard, b);
        return;
    }
	
	public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
    {
	 	if(m.owner== null) return;
	 	m.owner.buffMinionWithoutMessage(-m.attackbuff, -m.healthbuff, 0, b);
        return;
    }
	
}

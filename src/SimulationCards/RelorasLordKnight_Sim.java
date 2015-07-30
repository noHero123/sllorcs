package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.DamageType;
import BattleStuff.Minion;
import BattleStuff.SubType;
import BattleStuff.UColor;

public class RelorasLordKnight_Sim extends Simtemplate {
	//"id":354,"name":"Reloras, Lord Knight","description":""
	//resonance: adjacent minion +1 attack this round
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 354) b.destroyMinion(m, own);
		}
		
    }
	
	 public void onPlayerPlayASpell(Board b ,Minion triggerEffectMinion, Minion spell)
	    {
		 	if(triggerEffectMinion.position.color == spell.position.color)
		 	{
		 		for(Minion m : b.getMinionsFromPositions(triggerEffectMinion.position.getNeightbours()))
		 		{
		 			m.buffMinionWithoutMessage(1, 0, 0, b);
		 			m.addnewEnchantments("BUFF", "Reloras, Lord Knight", triggerEffectMinion.card.cardDescription, triggerEffectMinion.card, b, triggerEffectMinion.position.color);
		 		}
		 	}
	    	return;
	    }

	 public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
	    {
			if(triggerEffectMinion.owner==null) return false;
	        return true;//buff is removed, so we return true
	    }
	
		public  void onDeathrattle(Board b, Minion m, Minion attacker, AttackType attacktype, DamageType dmgtype)
	    {
		 	if(m.owner== null) return;
		 	m.owner.buffMinionWithoutMessage(-2, 0, 0, b);
	        return;
	    }

}

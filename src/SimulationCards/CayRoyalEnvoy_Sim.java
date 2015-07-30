package SimulationCards;

import BattleStuff.AttackType;
import BattleStuff.Board;
import BattleStuff.UColor;
import BattleStuff.DamageType;
import BattleStuff.GameState;
import BattleStuff.Minion;
import BattleStuff.SubType;

public class CayRoyalEnvoy_Sim extends Simtemplate {
	//"id":314,"name":"Cay, Royal Envoy","description":"At the end of your turn, if an opponent unit was destroyed in combat, draw 1 scroll."
	//NOTE: "IN COMBAT" means a battlephase ==1
	
	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		for(Minion m : b.getPlayerFieldList(own.position.color))
		{
			if(m.typeId == 314) b.destroyMinion(m, own);
		}
		
    }
	
	 public boolean hasWard(Board b ,Minion m)
	 {

		 return b.isDominionActive(m.position.color);
	 }
	
	public  Boolean onTurnEndsTrigger(Board b, Minion triggerEffectMinion, UColor turnEndColor)
    {
		
		if(triggerEffectMinion.owner == null)
		{
			if(triggerEffectMinion.didDmgToIdol && triggerEffectMinion.position.color == turnEndColor)
			{
				b.drawCards(turnEndColor, 1);
			}
			triggerEffectMinion.didDmgToIdol=false;
			return false;
		}
		
        return false;
    }
	
	 
	 public  void onMinionDiedTrigger(Board b, Minion triggerEffectMinion, Minion diedMinion, Minion attacker, AttackType attackType, DamageType dmgtype)
	 {
		 if(diedMinion.position.color == triggerEffectMinion.position.color || triggerEffectMinion.position.color != b.activePlayerColor || b.gameState != GameState.Battle) return;
		 triggerEffectMinion.didDmgToIdol=true;//we use this trigger :D
	      return;
	 }
	 
}

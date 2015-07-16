package SimulationCards;

import BattleStuff.Board;
import BattleStuff.Minion;
import BattleStuff.subType;

public class SisterOfTheFox_Sim extends Simtemplate {
	//"id":53,"name":"Sister of the Fox","description":"When Sister of the Fox comes into play, draw 1 scroll."

	public  void getBattlecryEffect(Board b, Minion own, Minion target)
    {
		b.drawCards(own.position.color, 1);
		b.addMessageToPlayer(own.position.color, b.getHandUpdateMessage(own.position.color));
		b.addMessageToBothPlayers(b.getCardStackUpdate(own.position.color));
        return;
    }
	
}

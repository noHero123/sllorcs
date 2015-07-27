package BattleStuff;

public class SiftItem {
	Minion minion;
	SiftPlace from = SiftPlace.LIBRARY; //0= library, 1 = grave
	SiftPlace whereToAdd = SiftPlace.HAND;
	
	public SiftItem(Minion m, SiftPlace siftedFrom, SiftPlace siftTO)
	{
		this.minion=m;
		this.from=siftedFrom;
		this.whereToAdd = siftTO;
	}
}

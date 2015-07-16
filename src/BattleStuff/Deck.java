package BattleStuff;

import java.util.ArrayList;

public class Deck {
	
	public String deckname = "";
	public long playerowner = -1;
	public int valid = 0;
	public String ressis="";
	public ArrayList<Integer> cardIds = new ArrayList<Integer>();
	public long timestamp = 0;
	
	public void print()
	{
		String cs= "";
		for(Integer i : cardIds )
		{
			if(!cs.equals("")) cs+=",";
			cs+=i+"";
		}
		System.out.println(deckname + " " + playerowner+ " [" + cs +"]");
	}

}

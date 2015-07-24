package BattleStuff;

import java.util.ArrayList;

import ServerStuff.BattleServerThread;
import ServerStuff.LobbyThread;


public class Player {

	public String name  = "Easy AI";
	public long profileId=-1;
	public int gold = 2000;
	public int shards = 0;
	
	public int admin = 0;
	public int featureType=0; // premium or not
	
	public int acceptTrades=1;
	public int acceptChallenges=1;
	public int spectatePermission=1;
	public int rating=1000;
	
	//avatar
	public int head=37;
	public int body=11;
	public int leg = 40;
	public int armBack = 1;
	public int armFront = 17;
	
	//idolstuff
	public String idolType ="DEFAULT";
	//idol appearance
	public int idol1 = 2;
	public int idol2 = 2;
	public int idol3 = 2;
	public int idol4 = 2;
	public int idol5 = 2;
	
	//temp stuff for battle
	public boolean isBattling = false;
	public boolean canMulligan = true;
	public UColor color = UColor.white;
	
	public int goingToChallengeID = -1; //id of gallenged player
	public String deckname = "growth"; //deck he uses in Next/current fight
	public String activeRessis = "";
	public boolean multicolor = false;
	
	public LobbyThread lt=null;
	public BattleServerThread bt=null;
	
	//TODO rename this ;D to something like sendBattleMessageToClient
	public void sendMessgeToBattleServer(String msg)
	{
		if(this.bt == null) 
		{
			System.out.println("send error, cant find BattleThread of " + this.name + " " + this.profileId);
			return;
		}
		this.bt.sendToClient(msg);
	}
	
	public volatile boolean initGame=false;
	
}

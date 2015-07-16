package ServerStuff;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.json.*;


import BattleStuff.Board;
import BattleStuff.Deck;
import BattleStuff.Minion;
import BattleStuff.Player;


public class LobbyThread extends Thread{
	
	private Socket  socket;
	DataOutputStream out = null;
	private boolean running =true;
	public Player ownPlayer= new Player();
	InterThreadStuff its = InterThreadStuff.getInstance();
	LobbyServer lbbsrvr = LobbyServer.getInstance();
	
	//TODO handle messaging better (without looks): if we assume you can message/challenge to ppl only in a room, you can 
	//save thoose ppl in rooms and manage them yourself to send stuff like messages + challenges! whispering doesnt count
	public ArrayList<String> rooms = new ArrayList<String>();
	
	public LobbyThread(Socket s)
	{
		this.socket = s;
		try {
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
        	System.out.println("lookupthread error: " + e);
            return;
        }
		
	}
	
	public void cancel()
	{
		running=false;
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.interrupt();
	}
	
	public void sendToClient( String s)
	{
		try{
			this.out.writeBytes(s);
			this.out.flush();
			if(!s.contains("\"msg\":\"Ping\"") && !s.contains("\"msg\":\"CardTypes\""))System.out.println("sended to " + this.ownPlayer.name + " "+s );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	private String getPingMessage()
	{
		String s  = 
		new JSONStringer()
	     .object()
	         .key("msg")
	         .value("Ping")
	         .key("time")
	         .value(System.currentTimeMillis())
	     .endObject()
	     .toString();
		return s;
	}
	
	private void saveDeck(String name, long profid, String cards)
	{
		//cards = string of cardids seperated with "," like: 20172638,43733187,...
		MyLittleDatabase.getInstance().addDeck(name, profid, cards);
		
	}
	
	private String getRessis(ArrayList<Minion> deck)
	{
		boolean d=false;
		boolean e=false;
		boolean g=false;
		boolean o=false;
		for(Minion m :deck)
		{
			if(m.card.costDecay>=1) d=true;
			if(m.card.costEnergy>=1) e=true;
			if(m.card.costGrowth>=1) g=true;
			if(m.card.costOrder>=1) o=true;
		}
		String ressis = "";
		int anz =0;
		if(d) 
		{
			if(!ressis.equals("")) ressis+=",";
			ressis += "\"DECAY\"";
			anz++;
		}
		if(e) 
		{
			if(!ressis.equals("")) ressis+=",";
			ressis += "\"ENERGY\"";
			anz++;
		}
		if(g) 
		{
			if(!ressis.equals("")) ressis+=",";
			ressis += "\"GROWTH\"";
			anz++;
		}
		if(o) 
		{
			if(!ressis.equals("")) ressis+=",";
			ressis += "\"ORDER\"";
			anz++;
		}
		
		return ressis;
	}
	
	private String getDeckCards(String deck)
	{
		ArrayList<Minion> cards = MyLittleDatabase.getInstance().getDeckFromPlayer(deck, this.ownPlayer.profileId, true);
		String s="";
		String ressis = this.getRessis(cards);
		//{"deck":"s1u2","metadata":"{'pos':'8979204,0,959|48290535,0,989|1846499,71,959|1846500,71,989|4014639,142,959|21085877,142,989|1846459,214,959|1846460,214,989|23949788,285,959|48371807,285,989|1846504,357,959|1846505,357,989|1846458,428,959|6350653,428,989|1846478,500,959|48515765,500,989|18101204,571,959|21080342,571,989|1846471,642,959|25531673,642,989|1846485,714,959|1846486,714,989|29527132,785,959|48371836,785,989|20958470,857,959|20958474,857,989|1846487,928,959|1846488,928,989|9749752,1000,959|21091814,1000,989|1846463,0,109|1846464,0,139|25160924,71,109|25531687,71,139|1846470,142,109|7866239,142,139|1846465,214,109|1846466,214,139|1846506,285,109|1846507,285,139|1846476,357,109|48685208,357,139|1846467,428,109|1846468,428,139|6710615,500,109|21080328,500,139|21080352,571,109|21085790,571,139|18731465,642,109|21080323,642,139|7511602,714,109|17717993,714,139|1846473,785,109|7866360,785,139|7081250,857,109|7866681,857,139|1846489,928,109|28820375,928,139|20172638,1000,109|43733187,1000,139'}","cards":[{"id":1846473,"typeId":56,"tradable":true,"isToken":false,"level":0},{"id":1846476,"typeId":91,"tradable":true,"isToken":false,"level":0},{"id":18731465,"typeId":113,"tradable":true,"isToken":false,"level":0},{"id":21080352,"typeId":112,"tradable":true,"isToken":false,"level":0},{"id":1846478,"typeId":40,"tradable":true,"isToken":false,"level":0},{"id":1846465,"typeId":100,"tradable":true,"isToken":false,"level":0},{"id":1846464,"typeId":115,"tradable":true,"isToken":false,"level":0},{"id":1846467,"typeId":44,"tradable":true,"isToken":false,"level":0},{"id":1846466,"typeId":100,"tradable":true,"isToken":false,"level":0},{"id":1846468,"typeId":44,"tradable":true,"isToken":false,"level":0},{"id":1846471,"typeId":49,"tradable":true,"isToken":false,"level":0},{"id":20172638,"typeId":34,"tradable":true,"isToken":false,"level":0},{"id":1846470,"typeId":94,"tradable":true,"isToken":false,"level":0},{"id":1846488,"typeId":84,"tradable":true,"isToken":false,"level":0},{"id":1846489,"typeId":38,"tradable":true,"isToken":false,"level":0},{"id":25531673,"typeId":49,"tradable":false,"isToken":false,"level":0},{"id":1846485,"typeId":88,"tradable":true,"isToken":false,"level":0},{"id":1846486,"typeId":88,"tradable":true,"isToken":false,"level":0},{"id":1846487,"typeId":84,"tradable":true,"isToken":false,"level":0},{"id":21085790,"typeId":112,"tradable":true,"isToken":false,"level":0},{"id":1846507,"typeId":63,"tradable":true,"isToken":false,"level":0},{"id":1846506,"typeId":63,"tradable":true,"isToken":false,"level":0},{"id":23949788,"typeId":89,"tradable":true,"isToken":false,"level":0},{"id":1846505,"typeId":18,"tradable":true,"isToken":false,"level":0},{"id":1846504,"typeId":18,"tradable":true,"isToken":false,"level":0},{"id":21080323,"typeId":113,"tradable":true,"isToken":false,"level":0},{"id":1846499,"typeId":17,"tradable":true,"isToken":false,"level":0},{"id":48685208,"typeId":91,"tradable":true,"isToken":false,"level":0},{"id":17717993,"typeId":154,"tradable":true,"isToken":false,"level":0},{"id":25531687,"typeId":41,"tradable":false,"isToken":false,"level":0},{"id":21080328,"typeId":76,"tradable":true,"isToken":false,"level":0},{"id":1846500,"typeId":17,"tradable":true,"isToken":false,"level":0},{"id":8979204,"typeId":189,"tradable":true,"isToken":false,"level":0},{"id":21080342,"typeId":43,"tradable":true,"isToken":false,"level":0},{"id":20958474,"typeId":60,"tradable":true,"isToken":false,"level":0},{"id":43733187,"typeId":34,"tradable":true,"isToken":false,"level":0},{"id":20958470,"typeId":60,"tradable":true,"isToken":false,"level":0},{"id":7511602,"typeId":154,"tradable":true,"isToken":false,"level":0},{"id":6710615,"typeId":76,"tradable":true,"isToken":false,"level":0},{"id":21085877,"typeId":129,"tradable":true,"isToken":false,"level":0},{"id":7866681,"typeId":29,"tradable":true,"isToken":false,"level":0},{"id":7081250,"typeId":29,"tradable":true,"isToken":false,"level":0},{"id":29527132,"typeId":16,"tradable":true,"isToken":false,"level":0},{"id":28820375,"typeId":38,"tradable":true,"isToken":false,"level":0},{"id":48371807,"typeId":89,"tradable":true,"isToken":false,"level":0},{"id":48515765,"typeId":40,"tradable":true,"isToken":false,"level":0},{"id":48290535,"typeId":189,"tradable":true,"isToken":false,"level":2},{"id":4014639,"typeId":129,"tradable":true,"isToken":false,"level":0},{"id":6350653,"typeId":114,"tradable":true,"isToken":false,"level":0},{"id":18101204,"typeId":43,"tradable":true,"isToken":false,"level":0},{"id":25160924,"typeId":41,"tradable":true,"isToken":false,"level":0},{"id":1846463,"typeId":115,"tradable":true,"isToken":false,"level":0},{"id":21091814,"typeId":4,"tradable":true,"isToken":false,"level":0},{"id":1846460,"typeId":13,"tradable":true,"isToken":false,"level":0},{"id":1846458,"typeId":114,"tradable":true,"isToken":false,"level":0},{"id":1846459,"typeId":13,"tradable":true,"isToken":false,"level":0},{"id":7866239,"typeId":94,"tradable":true,"isToken":false,"level":0},{"id":7866360,"typeId":56,"tradable":true,"isToken":false,"level":0},{"id":48371836,"typeId":16,"tradable":true,"isToken":false,"level":0},{"id":9749752,"typeId":4,"tradable":true,"isToken":false,"level":0}],"resources":["GROWTH"],"valid":true,"msg":"DeckCards"}
		
		for(Minion sc : cards)
		{
			if(!s.equals("")) s+=",";
			s+="{\"id\":"+sc.cardID+",\"typeId\":"+sc.typeId+",\"tradable\":true,\"isToken\":false,\"level\":"+sc.lvl+"}";
				
		}
		
		s="{\"deck\":\""+deck+"\",\"metadata\":\"\",\"cards\":[" + s + "],\"resources\":["+ressis+"],\"valid\":true,\"msg\":\"DeckCards\"}";
		return s;
	}
	
	private String getLibraryView(long profileid)
	{
		//TODO save the deck to ownplayer for faster access the second time
		String s="";
		long pid= profileid;
		if(profileid == 0) 
		{
			pid = this.ownPlayer.profileId;
		}
		ArrayList<SmallCard> cards = MyLittleDatabase.getInstance().getCardsFromPlayerID(pid);
		for(SmallCard sc : cards)
		{
			if(!s.equals("")) s+=",";
			s+="{\"id\":"+sc.cardid+",\"typeId\":"+sc.typeid+",\"tradable\":true,\"isToken\":false,\"level\":"+sc.level+"}";
				
		}
		s = "{\"cards\":[" + s + "],\"profileId\":"+pid+",\"msg\":\"LibraryView\"}";
		return s;
	}
	
	private String getBattleRedirect()
	{
	
		return "{\"ip\":\"" + its.usedServerIp + "\",\"port\":"+ its.battlePort+",\"msg\":\"BattleRedirect\"}";
	}
	
	private String getMappedStringsMessage()
	{
		return CardTypesMessageProvider.getMappedStrings();		    
	}
	
	private String getCardTypesMessage()
	{
        return CardTypesMessageProvider.getCardTypesMessage();
	}
	
	private String getAvatarTypesMessage()
	{
        return CardTypesMessageProvider.getAvatarTypes();
	}
	
    private String getIdolTypes()
    {
    	return "{\"types\":[{\"id\":1,\"name\":\"Northern Ward\",\"type\":\"COMMON\",\"filename\":\"ps_northern_ward_\"},{\"id\":2,\"name\":\"Cryptwalker\",\"type\":\"COMMON\",\"filename\":\"ps_cryptwalker_\"},{\"id\":3,\"name\":\"Blue Crystal\",\"type\":\"STORE\",\"filename\":\"ps_blue_\"},{\"id\":4,\"name\":\"Arcane Conduit\",\"type\":\"STORE\",\"filename\":\"ps_energy_idol_\"},{\"id\":5,\"name\":\"Totem Stone\",\"type\":\"STORE\",\"filename\":\"ps_growth_idol_\"},{\"id\":6,\"name\":\"Red Crystal\",\"type\":\"STORE\",\"filename\":\"ps_red_\"},{\"id\":7,\"name\":\"Izulr Egg\",\"type\":\"STORE\",\"filename\":\"random_idol_\"},{\"id\":8,\"name\":\"Masked Effigy\",\"type\":\"STORE\",\"filename\":\"random_idol2_\"},{\"id\":9,\"name\":\"Pie\",\"type\":\"STORE\",\"filename\":\"pie_\"},{\"id\":10,\"name\":\"Dyrran Etherarium\",\"type\":\"STORE\",\"filename\":\"dyrran_etherarium_\"},{\"id\":11,\"name\":\"Mire Vaporstack\",\"type\":\"STORE\",\"filename\":\"mire_vaporstack_\"},{\"id\":12,\"name\":\"Remnant Coil\",\"type\":\"STORE\",\"filename\":\"remnant_coil_\"}],\"msg\":\"IdolTypes\"}";
    }

    private String getAchievementTypes()
    {
        return "{\"achievementTypes\":[{\"id\":1,\"name\":\"Win 1 match\",\"description\":\"Win any match\",\"goldReward\":100,\"group\":1,\"sortId\":2,\"icon\":\"Misc.png\"}],\"msg\":\"AchievementTypes\"}";
    }

    private String getProfileInfo(Player p)
    {
        return "{\"profile\":{\"id\":" + p.profileId +",\"name\":\""+ p.name + "\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"userUuid\":\"123456789\",\"profileUuid\":\"123asd123asd\",\"msg\":\"ProfileInfo\"}";
    }
    
    private String getProfileDataInfo()
    {
        return "{\"profileData\":{\"gold\":"+ this.ownPlayer.gold + ",\"shards\":"+ this.ownPlayer.shards + ",\"starterDeckVersion\":1,\"spectatePermission\":\"ALLOW\",\"acceptTrades\":true,\"acceptChallenges\":true,\"rating\":"+ this.ownPlayer.rating + "},\"msg\":\"ProfileDataInfo\"}";
    }

    private String getFriends()
    {
        return "{\"friends\":[],\"msg\":\"GetFriends\"}";
    }

    private String getFriendRequests()
    {
        return "{\"requests\":[],\"msg\":\"GetFriendRequests\"}";
    }

    private String getBlockedPersons()
    {
        return  "{\"blocked\":[],\"msg\":\"GetBlockedPersons\"}";
    }
    
    private synchronized boolean addPlayerToRoom(String name)
    {
    	if(!this.rooms.contains(name))
    	{
    		this.rooms.add(name);
    		return true;
    	}
    	return false;
    }
    
    public synchronized boolean isPlayerInRoom(String name)
    {
    	return this.rooms.contains(name);
    }
    
    private synchronized void removePlayerFromRoom(String roomname)
    {
    	
    	String msg = "{\"roomName\":\""+ roomname +"\",\"removed\":[{\"profileId\":" + this.ownPlayer.profileId + ",\"name\":\"" + this.ownPlayer.name+ "\"}],\"msg\":\"RoomInfo\"}";
    	this.rooms.remove(roomname);
    	this.sendMessageToAllRoommates(roomname, msg);
    	
    }
    
    public void sendMessageToAllRoommates(String room, String msg)
    {
    	List<LobbyThread> list  = lbbsrvr.clientlist;
    	
    	synchronized (list) 
    	{
    	      Iterator i = list.iterator(); // Must be in synchronized block
    	      while (i.hasNext())
    	      {
    	    	  LobbyThread lt = (LobbyThread)i.next();
    	    	  if(lt.isPlayerInRoom(room))
    	    	  {
    	    		  lt.sendToClient(msg);
    	    	  }
    	      }
    	}
    }
    
    public void sendWhisperMessageToPlayer(Player player, String msg)
    {
    	List<LobbyThread> list  = lbbsrvr.clientlist;
    	
    	if(player.lt != null)
  	  	{
    		player.lt.sendToClient(msg);
    		this.sendToClient(msg);
  		  return;
  	  	}
    	
    	//send error or offline msg
    	
    	this.sendToClient("{\"text\":\"The user is offline or doesnt exist.\",\"msg\":\"CliResponse\"}");
    }
    
    public void sendMessageToPlayer(Player p, String msg)
    {
    	sendMessageToPlayer(p, msg, false);
    }
    
   
    public void sendMessageToPlayer(Player p, String msg, boolean endQueque)
    {
    	if(p.lt != null)
  	  	{
  		  if(endQueque) 
  		  {
  			  p.lt.endQueues();
  		  }
  		  p.lt.sendToClient(msg);
  		  return;
  	  	}
    	
    	//send error or offline msg
    	
    	this.sendToClient("{\"text\":\"The user is offline or doesnt exist.\",\"msg\":\"CliResponse\"}");
    }
    
    public void endQueues()
    {
    	//TODO queues
    	this.sendToClient("{\"inQueue\":false,\"gameType\":\"MP_QUICKMATCH\",\"msg\":\"GameMatchQueueStatus\"}");
		this.sendToClient("{\"inQueue\":false,\"gameType\":\"MP_RANKED\",\"msg\":\"GameMatchQueueStatus\"}");
		this.sendToClient("{\"inQueue\":false,\"gameType\":\"MP_LIMITED\",\"msg\":\"GameMatchQueueStatus\"}");
    }
    
    public void goIntoRoomMessaging(String room, String msg)
    {
    	List<LobbyThread> list  = lbbsrvr.clientlist;
    	
    	String ownRoomEnterList="";
    	synchronized (list) 
    	{
    	      Iterator i = list.iterator(); // Must be in synchronized block
    	      while (i.hasNext())
    	      {
    	    	  LobbyThread lt = (LobbyThread)i.next();
    	    	  if(lt.isPlayerInRoom(room) && lt.ownPlayer.profileId != this.ownPlayer.profileId)
    	    	  {
    	    		  //send all other clients that we come in
    	    		  lt.sendToClient(msg);
    	    		  if(!ownRoomEnterList.equals(""))
    	    		  {
    	    			  ownRoomEnterList+=",";
    	    		  }
    	    		  ownRoomEnterList+="{\"profileId\":"+lt.ownPlayer.profileId+",\"name\":\""+ lt.ownPlayer.name +"\",\"acceptChallenges\":true,\"acceptTrades\":true,\"adminRole\":\"None\",\"featureType\":\"PREMIUM\"}";
    	    	  }
    	      }
    	}
    	
    	//send list to own client + himself
    	if(!ownRoomEnterList.equals(""))
		  {
			  ownRoomEnterList+=",";
		  }
		  ownRoomEnterList+="{\"profileId\":"+this.ownPlayer.profileId+",\"name\":\""+ this.ownPlayer.name +"\",\"acceptChallenges\":true,\"acceptTrades\":true,\"adminRole\":\"None\",\"featureType\":\"PREMIUM\"}";
	  
    	
    	ownRoomEnterList = "{\"roomName\":\""+room+"\",\"updated\":[" + ownRoomEnterList + "],\"msg\":\"RoomInfo\"}";
		this.sendToClient(ownRoomEnterList);
    }
	
    private void roomEnter(String roomname)
    {
    	if(this.addPlayerToRoom(roomname))
		{
			this.sendToClient( "{\"roomName\":\""+roomname+"\",\"msg\":\"RoomEnter\"}" );
			//send all roommates that you joined
			this.goIntoRoomMessaging(roomname, "{\"roomName\":\""+roomname+"\",\"updated\":[{\"profileId\":"+this.ownPlayer.profileId+",\"name\":\""+ this.ownPlayer.name +"\",\"acceptChallenges\":true,\"acceptTrades\":true,\"adminRole\":\"None\",\"featureType\":\"PREMIUM\"}],\"msg\":\"RoomInfo\"}");

			//remove player
			//{"roomName":"general-1","removed":[{"profileId":0,"name":"_Karma"}],"msg":"RoomInfo"}
			//"adminRole":"None"
			//"adminRole":"Moderator"
			//"adminRole":"Mojang"
			//"featureType":"PREMIUM"
			//"featureType":"DEMO"
			
			this.sendToClient( "{\"roomName\":\""+roomname+"\",\"from\":\"Scrolls\",\"text\":\"You have joined \\\""+roomname+"\\\"\",\"msg\":\"RoomChatMessage\"}");
		}
    }
    
    private void startMatch()
    {
    	//last message
    	this.sendToClient( this.getBattleRedirect());
    	//this.closeConnection();//dont do this, it will reconnect and reconnect twice to battleserver :D
    }
    
    private void closeConnection()
    {
    	
    	try 
    	{
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	onSocketClose();
    }
    
    private void onSocketClose()
    {
    	//remove all rooms
    	ArrayList<String> temp = new ArrayList<String>();
    	temp.addAll(this.rooms);
    	for(String s : temp)
    	{
    		this.removePlayerFromRoom(s);
    	}
    	//TODO remove from MP-waiting lists + send challenge decline
    	this.ownPlayer.lt = null;
    	this.ownPlayer.goingToChallengeID=-1;
    	//this.its.removePlayerfromLobby(this.ownPlayer);//is not necessary.. only on real disconnect.. but how do we spot that? if there is no ping anymore?
    	this.running=false;
    }
    
	public void handleInput(JSONObject jo, DataOutputStream out)
	{

		String mssgName = jo.getString("msg");
		if(mssgName.equals("DidYouKnow"))
    	{
    		this.sendToClient( "{\"id\":999,\"hint\":\"scrolls never dies\",\"msg\":\"DidYouKnow\"}");
    		return;
    	}
		
		if(mssgName.equals("Ping"))
    	{
			this.sendToClient( this.getPingMessage());
    		return;
    	}
		
		if(mssgName.equals("FirstConnect") || mssgName.equals("Connect") )
    	{
			String username = jo.getJSONObject("accessToken").getJSONObject("selectedProfile").getString("name");
			
			Player p = new Player();
			
			p = this.its.getPlayerByName(username);
			if(p==null)
			{
				p = MyLittleDatabase.getInstance().getPlayerbyName(username);
				p = this.its.addPlayerToLobby(p);
	            System.out.println("Player found in db:" + p.name + " " + p.profileId);
			}
			
			p.lt = this;
			this.ownPlayer = p;
			
            
            
			this.sendToClient( this.getMappedStringsMessage());
            this.sendToClient(this.getCardTypesMessage());
            this.sendToClient(this.getAvatarTypesMessage());
            this.sendToClient(this.getIdolTypes());
            this.sendToClient(this.getAchievementTypes());
            this.sendToClient(this.getProfileInfo(p));
            this.sendToClient("{\"op\":\"FirstConnect\",\"msg\":\"Ok\"}");
            this.sendToClient("{\"msg\":\"ActiveGame\"}");
            
            //only for test:
            System.out.println("Cards");
            ArrayList<SmallCard> scs = MyLittleDatabase.getInstance().getCardsFromPlayerID(this.ownPlayer.profileId);
            //for(SmallCard sc : scs) {sc.print();}
            System.out.println("DECKS");
            ArrayList<Deck> ds = MyLittleDatabase.getInstance().getDecksFromPlayerID(this.ownPlayer.profileId);
            for(Deck d : ds) {d.print();}
            
    		return;
    	}
		
		if(mssgName.equals("JoinLobby"))
    	{
			this.sendToClient( this.getProfileDataInfo());
			this.sendToClient( this.getFriends());
			this.sendToClient( this.getFriendRequests());
			this.sendToClient( this.getBlockedPersons());
			this.sendToClient("{\"op\":\"JoinLobby\",\"msg\":\"Ok\"}");
    		return;
    	}
		
		if(mssgName.equals("RoomEnter") || mssgName.equals("RoomEnterFree") )
    	{
			//we got:{"roomName" : "nhtt","msg" : "RoomEnter"}
			//or we got: {"roomName" : "General","msg" : "RoomEnterFree"}
			String roomname = jo.getString("roomName");
			if(mssgName.equals("RoomEnterFree"))
			{
				roomname="sllorcs-1";
			}
			roomname=roomname.toLowerCase();
			this.roomEnter(roomname);
			return;
    	}
		
		if(mssgName.equals("RoomEnterMulti") )
    	{
			//we got:{"roomNames" : ["sllorcs-1"],"msg" : "RoomEnterMulti"}
			JSONArray arrr = jo.getJSONArray("roomNames");
			for(int i=0; i< arrr.length();i++)
			{
				String roomname = arrr.getString(i).toLowerCase();
				this.roomEnter(roomname);
			}
			return;
    	}
		
		if(mssgName.equals("Whisper") )
    	{
			//we got:{"text" : "hallo du","toProfileName" : "xxx","msg" : "Whisper"}

			//we answer to both participants
			//{"toProfileName":"xxx","from":"ueHero","text":"hallo du","msg":"Whisper"}
			String targetp = jo.getString("toProfileName");
			Player cp = this.its.getPlayerByName(targetp);
			if(cp.profileId==-1 || cp.lt == null)
			{
				this.sendToClient("{\"op\":\"GameChallengeRequest\",\"info\":\"user is offline\",\"msg\":\"Fail\"}");
				return;
			}
			
			String txt = jo.getString("text");
			String msg = "{\"toProfileName\":\""+ targetp +"\",\"from\":\"" + this.ownPlayer.name + "\",\"text\":\"" + txt + "\",\"msg\":\"Whisper\"}";
			sendWhisperMessageToPlayer(cp, msg);
			return;
    	}
		
		
		if(mssgName.equals("DeckList"))
    	{
			//we got:{"msg" : "DeckList"}
			String deckstring ="";
			for(Deck d : MyLittleDatabase.getInstance().getDecksFromPlayerID(this.ownPlayer.profileId))
			{
				//{\"name\":\"Decay Starter\",\"resources\":\"DECAY\",\"valid\":true,\"updated\":\"5 months ago\",\"timestamp\":1423663410000}
			
				if(!deckstring.equals(""))deckstring+=",";
				deckstring+="{\"name\":\"" + d.deckname + "\",\"resources\":\"" + d.ressis + "\",\"valid\":true,\"updated\":\"5 months ago\",\"timestamp\":" + d.timestamp+"}";
			}
			
			String s = "{\"decks\":["+deckstring+"],\"msg\":\"DeckList\"}";
			this.sendToClient(s);
			//this.sendToClient( "{\"decks\":[{\"name\":\"Decay Starter\",\"resources\":\"DECAY\",\"valid\":true,\"updated\":\"5 months ago\",\"timestamp\":1423663410000},{\"name\":\"Order Starter\",\"resources\":\"ORDER\",\"valid\":true,\"updated\":\"5 months ago\",\"timestamp\":1423646637000},{\"name\":\"Tiny Feet Preconstructed\",\"resources\":\"ORDER,GROWTH\",\"valid\":false,\"updated\":\"1 year ago\",\"timestamp\":1391697995000}],\"msg\":\"DeckList\"}");
			return;
    	}
		
		if(mssgName.equals("LibraryView"))
    	{
			//we got:{"profileId" : 0,"msg" : "LibraryView"}
			//profileId==0 our library!
			this.sendToClient(this.getLibraryView(jo.getLong("profileId")));
			return;
    	}
		
		if(mssgName.equals("DeckCards"))
    	{
			//we got:{"deck" : "s1u2","msg" : "DeckCards"}
			
			this.sendToClient(this.getDeckCards(jo.getString("deck")));
			return;
    	}
		
		if(mssgName.equals("DeckValidate"))
    	{
			//we got:{"cards" : ["1846473","1846476",...],"msg" : "DeckValidate"}
			//TODO: look for errors: like not your cards... to few,  more than 3 of a type etc
			this.sendToClient("{\"errors\":[],\"msg\":\"DeckValidate\"}");
			return;
    	}
		
		if(mssgName.equals("DeckSave"))
    	{
			//we got:{"name" : "s1u22","cards" : ["8979204","48290535",...],"metadata" : "...","msg" : "DeckSave"}
			//TODO: look for errors: like not your cards... to few,  more than 3 of a type etc
			String name = jo.getString("name");
			String cards = "";
			JSONArray jarr = jo.getJSONArray("cards"); 
			for(int i=0; i<jarr.length();i++)
			{
				String cca= jarr.getString(i);
				if(!cards.equals(""))cards +=",";
				cards +=cca;
			}
			this.saveDeck(name,  this.ownPlayer.profileId, cards);
			this.sendToClient("{\"op\":\"DeckSave\",\"msg\":\"Ok\"}");
			return;
    	}
		
		if(mssgName.equals("RoomsList"))
    	{
			//we got:{"msg" : "RoomsList"}
			this.sendToClient( "{\"rooms\":[{\"room\":{\"name\":\"Sllorcs\",\"autoIncrement\":true},\"numberOfUsers\":0},{\"room\":{\"name\":\"Trading\",\"autoIncrement\":true},\"numberOfUsers\":0},{\"room\":{\"name\":\"Newbie\",\"autoIncrement\":false},\"numberOfUsers\":0},{\"room\":{\"name\":\"Scrolls\",\"autoIncrement\":false},\"numberOfUsers\":0}],\"msg\":\"RoomsList\"}");
			return;
    	}
		
		if(mssgName.equals("OverallStats"))
    	{
			//we got:{"msg" : "OverallStats"}
			this.sendToClient(
				"{\"serverName\":\"Lobby 1\",\"loginsLast24h\":2042,\"topRanked\":[{\"name\":\"Alpha_Century\",\"rating\":1922},{\"name\":\"Holofoil\",\"rating\":1899},{\"name\":\"Nrp123\",\"rating\":1878},{\"name\":\"Vetscroll\",\"rating\":1863},{\"name\":\"Rock_Jesus\",\"rating\":1853},{\"name\":\"_antirad_\",\"rating\":1850},{\"name\":\"Blinky\",\"rating\":1827},{\"name\":\"PewQ\",\"rating\":1820},{\"name\":\"Eva\",\"rating\":1792},{\"name\":\"Monthy\",\"rating\":1785},{\"name\":\"sysp\",\"rating\":1777},{\"name\":\"NrpTheNinja\",\"rating\":1761},{\"name\":\"Zadrim\",\"rating\":1756},{\"name\":\"Rotzbengel53\",\"rating\":1749},{\"name\":\"Owl_Sage\",\"rating\":1747},{\"name\":\"Tajic\",\"rating\":1741},{\"name\":\"Francony\",\"rating\":1726},{\"name\":\"Magpieman\",\"rating\":1725},{\"name\":\"ChiefBromden\",\"rating\":1725},{\"name\":\"Carlitosming\",\"rating\":1718},{\"name\":\"AllToDust\",\"rating\":1716},{\"name\":\"DIE_BART\",\"rating\":1715},{\"name\":\"unreachable\",\"rating\":1715},{\"name\":\"JAD\",\"rating\":1712},{\"name\":\"Dryad\",\"rating\":1710},{\"name\":\"Lahopaa\",\"rating\":1706},{\"name\":\"I_do_not_talk\",\"rating\":1705},{\"name\":\"Mase\",\"rating\":1700},{\"name\":\"Dzees\",\"rating\":1698},{\"name\":\"Solaus\",\"rating\":1698},{\"name\":\"Raytional\",\"rating\":1698},{\"name\":\"Kustov\",\"rating\":1698},{\"name\":\"KungfuDojo\",\"rating\":1697},{\"name\":\"Alouu\",\"rating\":1694},{\"name\":\"Voidhead\",\"rating\":1692},{\"name\":\"Legrandin\",\"rating\":1690},{\"name\":\"vollgiin\",\"rating\":1690},{\"name\":\"Kounelos\",\"rating\":1687,\"gameId\":13316283},{\"name\":\"Ragnire\",\"rating\":1686},{\"name\":\"Donkey74\",\"rating\":1683},{\"name\":\"AstinusKuai\",\"rating\":1681},{\"name\":\"Demmiremmi\",\"rating\":1681,\"gameId\":13316311},{\"name\":\"srt\",\"rating\":1676},{\"name\":\"Atmaz\",\"rating\":1674},{\"name\":\"Tak4n\",\"rating\":1667,\"gameId\":13316275},{\"name\":\"RauweDouwe\",\"rating\":1667},{\"name\":\"Ghost_Bomb\",\"rating\":1665},{\"name\":\"Indus\",\"rating\":1662},{\"name\":\"Novi\",\"rating\":1659},{\"name\":\"Flayer\",\"rating\":1657},{\"name\":\"alvarpq\",\"rating\":1656},{\"name\":\"Yugi\",\"rating\":1654},{\"name\":\"JonasHasbach\",\"rating\":1654},{\"name\":\"SpyC\",\"rating\":1654},{\"name\":\"Buliar\",\"rating\":1652},{\"name\":\"Gravekper\",\"rating\":1652},{\"name\":\"Drakul\",\"rating\":1651},{\"name\":\"TheDisconnect\",\"rating\":1651},{\"name\":\"black_hawk\",\"rating\":1649},{\"name\":\"rimrock\",\"rating\":1648},{\"name\":\"Felk\",\"rating\":1648},{\"name\":\"GiaLoTa\",\"rating\":1647},{\"name\":\"Grimgork1\",\"rating\":1647},{\"name\":\"dranreb070\",\"rating\":1646},{\"name\":\"Caller_Outkast\",\"rating\":1646},{\"name\":\"Phobophile\",\"rating\":1645},{\"name\":\"DaOvalord\",\"rating\":1643},{\"name\":\"SolemnGiant\",\"rating\":1643},{\"name\":\"wbmc\",\"rating\":1643},{\"name\":\"Bronol\",\"rating\":1638},{\"name\":\"Cantor\",\"rating\":1638},{\"name\":\"siRisacc\",\"rating\":1636},{\"name\":\"futboleddy_\",\"rating\":1636},{\"name\":\"kehmesis\",\"rating\":1633},{\"name\":\"Buecherwurm\",\"rating\":1633},{\"name\":\"BabySpiders\",\"rating\":1630},{\"name\":\"ktrey\",\"rating\":1629},{\"name\":\"Yoshi29pi\",\"rating\":1627},{\"name\":\"Exscalab3r\",\"rating\":1627},{\"name\":\"Rocky_Wolf\",\"rating\":1626},{\"name\":\"Cimon\",\"rating\":1626},{\"name\":\"Gwothor\",\"rating\":1625},{\"name\":\"CrabbitFamiliar\",\"rating\":1624},{\"name\":\"Tygoz\",\"rating\":1624},{\"name\":\"Archmage199\",\"rating\":1624},{\"name\":\"Bloomblebee\",\"rating\":1622},{\"name\":\"fdagpigj\",\"rating\":1622},{\"name\":\"Oslohund\",\"rating\":1621},{\"name\":\"noobizm9\",\"rating\":1620,\"gameId\":13316311},{\"name\":\"Jubape\",\"rating\":1620},{\"name\":\"Guardiance\",\"rating\":1619},{\"name\":\"vishapakagh\",\"rating\":1618},{\"name\":\"Snikkit2nd\",\"rating\":1618},{\"name\":\"Dr_Solus\",\"rating\":1618},{\"name\":\"Seph_ITA\",\"rating\":1618},{\"name\":\"Elbandit\",\"rating\":1617},{\"name\":\"Filman\",\"rating\":1617},{\"name\":\"Qua\",\"rating\":1617},{\"name\":\"8bitfusion\",\"rating\":1616},{\"name\":\"Ahmurat\",\"rating\":1615}],\"nrOfProfiles\":130,\"weeklyWinners\":[{\"profileId\":42232,\"winType\":\"GOLD\",\"userName\":\"Holofoil\"},{\"profileId\":23662,\"winType\":\"SILVER\",\"userName\":\"Alpha_Century\"},{\"profileId\":65321,\"winType\":\"BRONZE\",\"userName\":\"Tajic\"},{\"profileId\":42032,\"winType\":\"MOST_WINS\",\"userName\":\"Francony\"}],\"topLabEntries\":[],\"msg\":\"OverallStats\"}"                     
				);
			return;
    	}
		
		/*if(jo.getString("msg").equals("GetTwitterFeed"))
    	{
			this.sendToClient( " ");
			return;
				
    	}*/
		
		if(mssgName.equals("RoomChatMessage"))
    	{
			//we got {"text" : "hi","roomName" : "nhtt","msg" : "RoomChatMessage"}  // note: no from!
			String roomname = jo.getString("roomName");
			String msg = "{\"roomName\":\""+roomname+"\",\"from\":\""+ this.ownPlayer.name + "\",\"text\":\""+ jo.getString("text") + "\",\"msg\":\"RoomChatMessage\"}";
			this.sendMessageToAllRoommates(roomname, msg);
			return;
    	}
		
		if(mssgName.equals("RoomExit"))
    	{
			//we got {"roomName" : "nhtt","msg" : "RoomExit"}
			
			String roomname = jo.getString("roomName");
			this.sendToClient("{\"op\":\"RoomExit\",\"msg\":\"Ok\"}");
			this.removePlayerFromRoom(roomname);
			return;
    	}
		
		if(mssgName.equals("PlaySinglePlayerQuickMatch"))
		{
			//TODO make it right 
			//we got {"robotName" : "RobotEasy","deck" : "Energy Starter","msg" : "PlaySinglePlayerQuickMatch"}
			//we response:
			
			
			//remove player form all queques
			//dont know why server sended it to me.. :D wasnt in queque
			if(jo.has("deck"))
			{
				this.ownPlayer.deckname = jo.getString("deck");
				System.out.println("set deck of " + this.ownPlayer.name + " to " + this.ownPlayer.deckname);
			}
			this.endQueues();
			//this.sendToClient( this.getBattleRedirect());
			this.startMatch();
			return;
		}
		
		//game challenges ################################################################################################
		//TODO : auto decline after timeout
		if(mssgName.equals("GameChallengeRequest"))
		{
			//we got {"customGameId" : -1,"chooseDeck" : false,"profileId" : 92923,"deck" : "cheap growth","msg" : "GameChallengeRequest"}
			//we response:
			
			if(this.ownPlayer.goingToChallengeID==-1)
			{
				
				int pid = jo.getInt("profileId");
				Player cp = this.its.getPlayerById(pid);
				
				if(cp == null || cp.profileId==-1 || cp.lt == null)
				{
					this.sendToClient("{\"op\":\"GameChallengeRequest\",\"info\":\"user is offline\",\"msg\":\"Fail\"}");
					return;
				}
				this.ownPlayer.goingToChallengeID = pid;
				this.ownPlayer.deckname="";
				if(jo.has("deck"))
				{
					this.ownPlayer.deckname = jo.getString("deck");
					System.out.println("set deck of " + this.ownPlayer.name + " to " + this.ownPlayer.deckname);
				}
				this.sendToClient("{\"op\":\"GameChallengeRequest\",\"msg\":\"Ok\"}");
				String msg="{\"from\":{\"id\":"+this.ownPlayer.profileId +",\"name\":\""+this.ownPlayer.name+"\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"msg\":\"GameChallenge\"}";
				this.sendMessageToPlayer(cp, msg);
				
			}
			else
			{
				this.sendToClient("{\"op\":\"GameChallengeRequest\",\"info\":\"can't have more than one open challenge\",\"msg\":\"Fail\"}");
			}
			
			return;
		}
		
		if(mssgName.equals("GameChallengeDecline"))
		{
			//we got {"profileId" : 92923,"msg" : "GameChallengeDecline"}
			
			int pid = jo.getInt("profileId");
			Player cp = this.its.getPlayerById(pid);
			if(cp.profileId==-1 || cp.lt==null)
			{
				this.sendToClient("{\"op\":\"GameChallengeAccept\",\"info\":\"challenge not active\",\"msg\":\"Fail\"}");
				return;
			}
			cp.goingToChallengeID=-1;
			this.sendToClient("{\"op\":\"GameChallengeDecline\",\"msg\":\"Ok\"}");
			//TODO on Timeout both players got the decline message of the Challenged user
			//from = player who challenged, to = player who was challenged
			String msg="{\"from\":{\"id\":"+cp.profileId+",\"name\":\""+cp.name+"\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"to\":{\"id\":"+this.ownPlayer.profileId+",\"name\":\""+this.ownPlayer.name+"\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"status\":\"DECLINE\",\"msg\":\"GameChallengeResponse\"}";

			this.sendMessageToPlayer(cp, msg);
			
			return;
		}
		
		if(mssgName.equals("GameChallengeAccept"))
		{
			//we got {"profileId" : 92923,"deck" : "cheap growth","msg" : "GameChallengeAccept"}
			
			int pid = jo.getInt("profileId");
			Player cp = this.its.getPlayerById(pid);
			if(cp.profileId==-1 || this.ownPlayer.isBattling || cp.isBattling)
			{
				this.sendToClient("{\"op\":\"GameChallengeAccept\",\"info\":\"challenge not active\",\"msg\":\"Fail\"}");
				return;
			}
			
			// create New Battlefield and add it to Boardlist!
			Board b = new Board();
			//b.setPlayers(this.ownPlayer, cp, true);
			//System.out.println(b.getGameInfoMessage(this.ownPlayer.name));
			
			
			
			this.ownPlayer.isBattling=true;
			cp.isBattling=true;
			
			if(this.ownPlayer.goingToChallengeID>=0)
			{
				//TODO send decline to that guy
			}
			this.ownPlayer.goingToChallengeID=-1;
			
			this.ownPlayer.deckname="";
			if(jo.has("deck"))
			{
				this.ownPlayer.deckname = jo.getString("deck");
				System.out.println("set deck of " + this.ownPlayer.name + " to " + this.ownPlayer.deckname);
			}
			
			b.setPlayers(this.ownPlayer, cp, true);
			
			boolean wasadded = this.its.addBoard(b);
			if(!wasadded) 
			{
				//someone is in battle! send declines
				this.sendToClient("{\"op\":\"GameChallengeAccept\",\"info\":\"challenge not active\",\"msg\":\"Fail\"}");
				return;
			}
			String msg="{\"from\":{\"id\":"+cp.profileId+",\"name\":\""+cp.name+"\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"to\":{\"id\":"+this.ownPlayer.profileId+",\"name\":\""+this.ownPlayer.name+"\",\"adminRole\":\"None\",\"featureType\":\"PREMIUM\",\"isParentalConsentNeeded\":false},\"status\":\"ACCEPT\",\"msg\":\"GameChallengeResponse\"}";

			//end own queue
			this.endQueues();
			//end queue of challenger, + send him that we accepted
			this.sendMessageToPlayer(cp, msg, true);
			
			this.sendMessageToPlayer(this.ownPlayer, "{\"op\":\"GameChallengeAccept\",\"msg\":\"Ok\"}");
			this.startMatch();
			cp.lt.startMatch();
			
			return;
		}
		
		
		
	}
	
	public void run() {
        InputStream inp = null;
        BufferedInputStream bis=null;
        
        System.out.println("lookupthread running");
        try {
            inp = socket.getInputStream();
            bis = new BufferedInputStream(inp);
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        	System.out.println("lookupthread error: " + e);
            return;
        }
        String line;  
        while (running) {
            try {
                line = null;
                
                byte[] contents = new byte[4*1024];

                int bytesRead = bis.read(contents);
                if(bytesRead!=-1)
                {
                	if(line==null)line="";
                    line += new String(contents, 0, bytesRead);
                }
                else
                {
                	System.out.println("client closed socket");
                	this.closeConnection();
                	this.running=false;
                	return;
                }
                
                /*while( (bytesRead = bis.read(contents)) != -1){ 
                	if(line==null)line="";
                    line += new String(contents, 0, bytesRead);
                    System.out.println("while " + line);
                }*/
                
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    //socket.close();
                    //return;
                } else {
                	
                	
                	if(!line.startsWith("{")) continue;
                	
                	//client is sometimes sending multiple messages in one rush like:
                	//{"roomName" : "General","msg" : "RoomEnterFree"}{"msg" : "RoomsList"}
                	//we make a array of it:
                	//[{"roomName" : "General","msg" : "RoomEnterFree"},{"msg" : "RoomsList"}]
                	
                	//JSONObject jo = new JSONObject(line);
                	String multiple = line.replaceAll("\\}\\{", "\\},\\{");
                	JSONArray joar = new JSONArray("["+multiple+"]");
                	for(int joi=0; joi< joar.length() ;joi++)
                	{
                		JSONObject joa = joar.getJSONObject(joi);
                		if(!joa.getString("msg").equals("Ping"))
                    	{

                    		System.out.println("rec from " +this.ownPlayer.name+": " + joa.toString());
                    	}
                		this.handleInput(joa, out);
                	}
                	
                	
                	/*if(!jo.getString("msg").equals("Ping"))
                	{
                		System.out.println("rec. from "+this.ownPlayer.name+":" + line);
                		System.out.println(jo.toString());
                	}
                	this.handleInput(jo, out);*/
                	
                
                }
            } catch (IOException e) {
            	System.out.println(this.ownPlayer.name + "closed connection");
            	onSocketClose();
                e.printStackTrace();
                return;
            }
        }
    }

}

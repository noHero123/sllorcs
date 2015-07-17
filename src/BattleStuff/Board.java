package BattleStuff;

import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import ServerStuff.InterThreadStuff;
import ServerStuff.MyLittleDatabase;


public class Board {
	//white player starts the game!
	public String serverip = "127.0.0.1"; // is set to InterThreadStuff.getInstance().usedServerIp; at start
	public int port = InterThreadStuff.getInstance().battlePort;
	
	public int GameState = 0; // 0=init, 1= turnbegin, 2= main, 3 = battle, 4 = endgame
	public int turnNumber =0;
	public Color activePlayerColor = Color.white;
	
	public boolean canAllwaysSacForWild =false;
	
	public int maxScrollsForCycle=7;
	
	public long gameID = System.currentTimeMillis(); //gameid
	public GameType gametype = GameType.SP_QUICKMATCH;
	public int maxRoundTimerSeconds = 90;
	public int currentRoundTime = -1;
	
	public Player whitePlayer = new Player();
	public Player blackPlayer = new Player();
	
	public Minion[][] whiteField = new Minion[5][3];
	public Minion[][] blackField = new Minion[5][3];
	
	public ArrayList<Minion> whiteRulesUpdates = new ArrayList<Minion>();
	public ArrayList<Minion> blackRulesUpdates = new ArrayList<Minion>();
	
	public ArrayList<Minion> whiteHand = new ArrayList<Minion>();
	public ArrayList<Minion> blackHand = new ArrayList<Minion>();
	
	public ArrayList<Minion> whiteDeck = new ArrayList<Minion>(); //ingame name its library
	public ArrayList<Minion> blackDeck = new ArrayList<Minion>(); //ingame name its library
	
	public ArrayList<Minion> whiteGrave = new ArrayList<Minion>(); //graveyard
	public ArrayList<Minion> blackGrave = new ArrayList<Minion>(); //graveyard
	
	/*public int[] whiteIdolsHP = {10,10,10,10,10}; 
	public int[] blackIdolsHP = {10,10,10,10,10}; 
	public int[] whiteIdolsMaxHP = {10,10,10,10,10}; 
	public int[] blackIdolsMaxHP = {10,10,10,10,10}; */
	
	public ArrayList<Minion> whiteIdols = new ArrayList<Minion>();
	public ArrayList<Minion> blackIdols = new ArrayList<Minion>();
	
	
	public int[] whiteRessources = {0,0,0,0,0}; //GROWTH, ORDER, ENERGy, DECAY, SPECIAL
	public int[] blackRessources = {0,0,0,0,0}; 
	public int[] whitecurrentRessources = {0,0,0,0,0}; 
	public int[] blackcurrentRessources = {0,0,0,0,0}; 
	
	public ArrayList<Minion> currentHand;
	public ArrayList<Minion> currentDeck;
	public ArrayList<Minion> currentGrave;
	public Minion[][] currentField;
	public ArrayList<Minion> currentRuleupdates;
	public boolean hasSacrificed = false;
	public Player currentPlayer;
	public Player opponentPlayer;
	
	public String effectMessage = "";
	
	Random randomNumberGenerator = new Random();
	
	
	private ArrayList<String> messagesToWhite= new ArrayList<String>();
	private ArrayList<String> messagesToBlack= new ArrayList<String>();
	
	public synchronized int initPlayer(Player p) 
	{
		int rdyplayers = 0;
		p.initGame=true;
		if(this.whitePlayer.initGame) rdyplayers++;
		if(this.blackPlayer.initGame) rdyplayers++;
        return rdyplayers;
    } 
	
	public void addEffectMessage(String msg)
	{
		if(this.effectMessage.equals(""))
		{
			this.effectMessage =  msg;
		}
		else
		{
			this.effectMessage += "," + msg;
		}
		
	}
	
	public void shuffleList(ArrayList<Minion> cards)
	{
		//Fisher–Yates shuffle
		int n = cards.size()-1;
		for(int i=0; i< n-1; i++ )
		{
			int j = this.getRandomNumber(i, n);
			
			//swap i and j
			Minion temp = cards.get(i);
			cards.set(i,cards.get(j));
			cards.set(j,temp);	
		}
		
	}
	
	public void addMessageToPlayer(Color col, String msg)
	{
		if(col == Color.white)
		{
			this.messagesToWhite.add(msg);
			return;
		}
		this.messagesToBlack.add(msg);
		return;
	}
	
	public void addMessageToBothPlayers(String msg)
	{
		this.messagesToWhite.add(msg);
		this.messagesToBlack.add(msg);
		return;
	}
	
	public void sendEffectMessagesToPlayers()
	{

		String s = "";
		if(this.messagesToWhite.size()>=1)
		{
			s= makeBigEffectMessage(Color.white);
			this.whitePlayer.sendMessgeToBattleServer(s);
		}
		if(this.messagesToBlack.size()>=1)
		{
			s= makeBigEffectMessage(Color.black);
			this.blackPlayer.sendMessgeToBattleServer(s);
		}
	}
	
	private void setActiveRessis(Player p)
	{
		ArrayList<Minion> deck = getPlayerDeck(p.color);
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
		
		p.multicolor = (anz >= 2);
		p.activeRessis = ressis;
	}
	
	public void updatePlayer(Player p)
	{
		//updating player... maybe old one isnt in list anymore
		if(this.whitePlayer.profileId == p.profileId) 
			{
				p.color = this.whitePlayer.color;
				p.deckname = this.whitePlayer.deckname;
				p.activeRessis = this.whitePlayer.activeRessis;
				p.multicolor = this.whitePlayer.multicolor;
				this.whitePlayer = p;
			}
		if(this.blackPlayer.profileId == p.profileId) 
		{
			p.color = this.blackPlayer.color;
			p.deckname = this.blackPlayer.deckname;
			p.activeRessis = this.blackPlayer.activeRessis;
			p.multicolor = this.blackPlayer.multicolor;
			this.blackPlayer = p;
		}
		//System.out.println(this.whitePlayer.name + " " + this.blackPlayer.name + " " +  p.name);
		
	}
	
	//called at beginning
	public void setPlayers(Player p1, Player p2, boolean randomstart)
	{
		this.whitePlayer = p1;
		this.blackPlayer = p2;
		this.currentPlayer = p1;
		this.opponentPlayer = p2;
		if(randomstart && getRandomNumber(1,2)>=2)
		{
			this.whitePlayer = p2;
			this.blackPlayer = p1;
			this.currentPlayer = p2;
			this.opponentPlayer = p1;
		}
		
		if(this.whitePlayer.name.equals("test"))
		{
			this.whiteRessources[4]=100;
			this.blackRessources[4]=100;
		}
		if(this.blackPlayer.name.equals("test"))
		{
			this.whiteRessources[4]=100;
			this.blackRessources[4]=100;
		}
		
		this.whitePlayer.color = Color.white;
		this.blackPlayer.color = Color.black;
		this.whitePlayer.canMulligan=true;
		this.blackPlayer.canMulligan=true;
		this.whitePlayer.initGame =false;
		this.blackPlayer.initGame =false;
		
		System.out.println("load decks: "+this.whitePlayer.deckname + " "+ this.whitePlayer.profileId);
		this.loadWhiteCards(MyLittleDatabase.getInstance().getDeckFromPlayer(this.whitePlayer.deckname, this.whitePlayer.profileId, true));
		System.out.println("load decks: "+this.blackPlayer.deckname + " "+ this.blackPlayer.profileId);
		this.loadBlackCards(MyLittleDatabase.getInstance().getDeckFromPlayer(this.blackPlayer.deckname, this.blackPlayer.profileId, true));
		
		shuffleList(this.whiteDeck);
		shuffleList(this.blackDeck);
		
		this.setActiveRessis(this.whitePlayer);
		this.setActiveRessis(this.blackPlayer);
		
		//create idols
		for(int i = 0; i<5; i++)
		{
			this.whiteIdols.add(new Minion(10, Color.white, i));
			this.blackIdols.add(new Minion(10, Color.black, i));
		}
		
		this.serverip = InterThreadStuff.getInstance().usedServerIp;
		
		//this.loadWhiteCards();
	}
	
	public Minion[][] getPlayerField(Color col)
	{
		if(col == Color.white) return this.whiteField;
		return this.blackField;
	}
	
	public ArrayList<Minion> getPlayerFieldList(Color col)
	{
		Minion[][] mins =  getPlayerField(col);
		ArrayList<Minion> alist = new ArrayList<Minion>();
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = mins[i][j];
				if(m != null && !m.deadTriggersDone)
				{
					alist.add(m);
				}
				
			}
		}
		
		return alist;
	}
	
	public ArrayList<Minion> getAllMinionOfField()
	{
		Minion[][] mins =  getPlayerField(this.activePlayerColor);
		ArrayList<Minion> alist = new ArrayList<Minion>();
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = mins[i][j];
				if(m != null && !m.deadTriggersDone)
				{
					alist.add(m);
				}
				
			}
		}
		
		mins =  getPlayerField(Board.getOpposingColor(this.activePlayerColor));
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = mins[i][j];
				if(m != null && !m.deadTriggersDone)
				{
					alist.add(m);
				}
				
			}
		}
		
		return alist;
	}
	
	public Player getPlayer(Color col)
	{
		if(col == Color.white) return this.whitePlayer;
		return this.blackPlayer;
	}
	
	public ArrayList<Minion> getPlayerHand(Color col)
	{
		if(col == Color.white) return this.whiteHand;
		return this.blackHand;
	}
	
	public ArrayList<Minion> getPlayerDeck(Color col)
	{
		if(col == Color.white) return this.whiteDeck;
		return this.blackDeck;
	}
	
	public ArrayList<Minion> getPlayerGrave(Color col)
	{
		if(col == Color.white) return this.whiteGrave;
		return this.blackGrave;
	}
	
	public ArrayList<Minion> getPlayerRules(Color col)
	{
		if(col == Color.white) return this.whiteRulesUpdates;
		return this.blackRulesUpdates;
	}
	
	public ArrayList<Minion> getPlayerIdols(Color col)
	{
		if(col == Color.white) return this.whiteIdols;
		return this.blackIdols;
	}
	
	
	
	public void loadWhiteCards(ArrayList<Minion> cards)
	{
		for(Minion c : cards)
		{
			//System.out.println("added to white: "+ c.typeId + " " + c.cardID);
			c.position.color = Color.white;
			this.whiteDeck.add(c);
		}
	}
	
	public void loadBlackCards(ArrayList<Minion> cards)
	{
		for(Minion c : cards)
		{
			//System.out.println("added to black: "+ c.typeId + " " + c.cardID);
			c.position.color = Color.black;
			this.blackDeck.add(c);
		}
	}
	
	public void drawCards(Color col, int howMany)
	{
		ArrayList<Minion> temphand = this.getPlayerHand(col);
		ArrayList<Minion> tempDeck = this.getPlayerDeck(col);

		
		if(tempDeck.size()< howMany)//first try.. add grave and reshuffle
		{
			//TODO dmg on recycle?
			ArrayList<Minion> tempGrave = this.getPlayerGrave(col);
			tempDeck.addAll(tempGrave);
			tempGrave.clear();
			this.shuffleList(tempDeck);
		}
		
		
		if(tempDeck.size()< howMany)//second try... dont draw so many cards :D
		{
			 howMany=tempDeck.size();
		}
		
		for(int i = 0; i< howMany; i++)
		{
			int n = this.getRandomNumber(0,tempDeck.size()-1);
			Minion c = tempDeck.remove(n);
			temphand.add(c);
			//System.out.println(c.cardType);
		}
		String s="";
		s=this.getHandUpdateMessage(col);
		this.addMessageToPlayer(col, s);
		s= this.getCardStackUpdate(col);
		this.addMessageToBothPlayers(s);
		
	}
	
	public int getRandomNumber(int min, int max)
	{
		//random number in [min, max]
		int randomNum = randomNumberGenerator.nextInt((max - min) + 1) + min;
		
		return randomNum;
	}
	
	
	public void initGame()
	{
		this.activePlayerColor= Color.white;
		drawCards(Color.white, 4);
		drawCards(Color.black, 5);
		this.messagesToWhite.clear();
		this.messagesToBlack.clear();
	}
	
	public String getPlayerColor(String playername)
	{
		if(playername == this.whitePlayer.name) return "white";
		return "black"; 
	}
	
	public String  getIdolsString(Color col)
	{
		//creates [{"color":"white","position":0,"hp":10,"maxHp":10},{"color":"white","position":1,"hp":10,"maxHp":10},{"color":"white","position":2,"hp":10,"maxHp":10},{"color":"white","position":3,"hp":10,"maxHp":10},{"color":"white","position":4,"hp":10,"maxHp":10}]
		
		//int[] idolHP = this.blackIdolsHP;
		//int[] idolMaxHP = this.blackIdolsMaxHP;
		
		String array = "[";
		String color = "black";
		ArrayList<Minion> idols = this.blackIdols;
		if(col == Color.white) 
		{
			//idolHP = this.whiteIdolsHP;
			//idolMaxHP = this.whiteIdolsMaxHP;
			idols=this.whiteIdols;
			color = "white";
		}
		
		for(int i=0;i<5; i++)
		{
			String idolsJS = "{";
			idolsJS+="\"color\":\"" + color + "\",";
			idolsJS+="\"position\":" + i + ",";
			idolsJS+="\"hp\":" + idols.get(i).Hp+ ",";
			idolsJS+="\"maxHp\":"+ idols.get(i).maxHP+ "}";
			
			if(i>=1) array +=",";
			
			array += idolsJS;
		}
		
		return array + "]";
	}
	
	public String getTilesJson(Color col)
	{
		//{"card":{"id":-1,"typeId":1,"tradable":true,"isToken":true,"level":0},"ap":4,"ac":2,"hp":18,"position":"0,0"},
		// OR {"card":{"id":-1,"typeId":1,"tradable":true,"isToken":true,"level":0},"ap":4,"ac":2,"hp":6,"position":"1,1","buffs":[{"name":"Elan Vital","description":"","type":"BUFF"}]}
		String s ="";
		
		Minion[][] ms = this.getPlayerField(col);
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = ms[i][j];
				if(m != null)
				{
					String buffs = "";
					for(Minion mm : m.attachedCards)
					{
						if(buffs.equals(""))
						{
							//BUFF
							buffs = "{\"name\":\"" + mm.card.cardname +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+m.bufftype+"\"}";
						}
						else
						{
							buffs += ",{\"name\":\"" + mm.card.cardname +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+m.bufftype+"\"}";
						}
					}
					
					if(!buffs.equals(""))
					{
						buffs= ",\"buffs\":[" + buffs + "]";
					}
					
					if(s.equals(""))
					{
						s = "{\"card\":{\"id\":"+m.cardID+",\"typeId\":" + m.typeId + ",\"tradable\":true,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"},\"ap\":" + m.Ap + ",\"ac\":"+m.getAc()+",\"hp\":"+m.Hp+",\"position\":\""+ m.position.row +","+ m.position.column +"\"" + buffs + "}";
						
					}
					else
					{
						s += ",{\"card\":{\"id\":"+m.cardID+",\"typeId\":" + m.typeId + ",\"tradable\":true,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"},\"ap\":" + m.Ap + ",\"ac\":"+m.getAc()+",\"hp\":"+m.Hp+",\"position\":\""+ m.position.row +","+ m.position.column +"\"" + buffs + "}";
						
					}
				}
			}
		}
		
		return s;
	}
	
	public String assetMessageBuilder(Color col)
	{
		int[] curr = this.blackcurrentRessources;
		int[] maxr= this.blackRessources;
		int handsize = this.blackHand.size();
		int libsize = this.blackDeck.size();
		int gravesize = this.blackGrave.size();
		ArrayList<Minion> rules = this.blackRulesUpdates;
		if(col == Color.white)
		{
			curr=this.whitecurrentRessources;
			maxr=this.whiteRessources;
			handsize = this.whiteHand.size();
			libsize= this.whiteDeck.size();
			gravesize = this.whiteGrave.size();
			rules = this.whiteRulesUpdates;
		}
		//{\"availableResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"outputResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"ruleUpdates\":[],\"handSize\":4,\"librarySize\":46,\"graveyardSize\":0}
		//"ruleUpdates":[{"card":{"id":2575,"typeId":339,"tradable":false,"isToken":false,"level":0},"color":"white","roundsLeft":8}],"handSize":3...
		String ruleupdates="";
		for(Minion m : rules)
		{
			if(!ruleupdates.equals(""))ruleupdates += ",";
			ruleupdates += "{\"card\":{\"id\":" + m.cardID + ",\"typeId\":"+m.typeId + ",\"tradable\":true,\"isToken\":" + Boolean.toString(m.isToken) + ",\"level\":"+m.lvl+"},\"color\":\"" + Board.colorToString(m.position.color)+"\",\"roundsLeft\":"+ m.lingerDuration+"}";
		}
		String s ="{\"availableResources\":{\"DECAY\":" + curr[3] + ",\"ORDER\":" + curr[1]+ ",\"ENERGY\":" + curr[2] + ",\"GROWTH\":" + curr[0] + ",\"SPECIAL\":"+ curr[4]+"},\"outputResources\":{\"DECAY\":"+ maxr[3] + ",\"ORDER\":" + maxr[1]+ ",\"ENERGY\":" + maxr[2] + ",\"GROWTH\":" + maxr[0] + ",\"SPECIAL\":"+ maxr[4]+"},\"ruleUpdates\":["+ruleupdates+"],\"handSize\":" + handsize + ",\"librarySize\":" + libsize + ",\"graveyardSize\":" + gravesize+ "}" ;
		
		return s;
	}
	
	public String getGameStateMessage()
	{
		//"{\"whiteGameState\":{\"playerName\":\"ueHero\",\"board\":{\"color\":\"white\",\"tiles\":[],\"idols\":[10,10,10,10,10]},\"mulliganAllowed\":true,\"assets\":{\"availableResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"outputResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"ruleUpdates\":[],\"handSize\":4,\"librarySize\":46,\"graveyardSize\":0}},
		//\"blackGameState\":{\"playerName\":\"Easy AI\",\"board\":{\"color\":\"black\",\"tiles\":[],\"idols\":[10,10,10,10,10]},\"mulliganAllowed\":true,\"assets\":{\"availableResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"outputResources\":{\"DECAY\":0,\"ORDER\":0,\"ENERGY\":0,\"GROWTH\":0,\"SPECIAL\":0},\"ruleUpdates\":[],\"handSize\":5,\"librarySize\":45,\"graveyardSize\":0}},
		//\"activeColor\":\"white\",\"phase\":\"Init\",\"turn\":0,\"hasSacrificed\":false,\"secondsLeft\":-1,\"msg\":\"GameState\"}";
		
		String whiteTiles = getTilesJson(Color.white);
		String blackTiles = getTilesJson(Color.black);
		String whiteIdolss=""+this.whiteIdols.get(0).Hp+","+this.whiteIdols.get(1).Hp+","+this.whiteIdols.get(2).Hp+","+this.whiteIdols.get(3).Hp+","+this.whiteIdols.get(4).Hp;
		String blackIdolss=""+this.blackIdols.get(0).Hp+","+this.blackIdols.get(1).Hp+","+this.blackIdols.get(2).Hp+","+this.blackIdols.get(3).Hp+","+this.blackIdols.get(4).Hp;
		String whiteAssets = this.assetMessageBuilder(Color.white);
		String blackAssets = this.assetMessageBuilder(Color.black);
		
		String s = 	"{\"whiteGameState\":{\"playerName\":\"" + this.whitePlayer.name + "\",\"board\":{\"color\":\"white\",\"tiles\":["+whiteTiles+"],\"idols\":["+whiteIdolss+"]},\"mulliganAllowed\":"+ Boolean.toString(this.whitePlayer.canMulligan) + ",\"assets\":"+whiteAssets+"},";
		s+="\"blackGameState\":{\"playerName\":\"" + this.blackPlayer.name + "\",\"board\":{\"color\":\"black\",\"tiles\":["+blackTiles+"],\"idols\":["+blackIdolss+"]},\"mulliganAllowed\":"+ Boolean.toString(this.blackPlayer.canMulligan) + ",\"assets\":"+blackAssets+"},";
		String phse = getPhaseString();
		String sacificed = Boolean.toString(this.hasSacrificed);
		int secondsleft = currentRoundTime;
		s+= "\"activeColor\":\""+this.activePlayerColor+"\",\"phase\":\""+phse+"\",\"turn\":"+turnNumber+",\"hasSacrificed\":"+sacificed+",\"secondsLeft\":"+ secondsleft +",\"msg\":\"GameState\"}";
		
		return s;
	}
	
	public String getPhaseString()
	{
		String phse = "Init";
		if(this.GameState == 1) phse = "PreMain";
		if(this.GameState == 2) phse = "Main";
		if(this.GameState == 3) phse = "End";
		return phse;
	}
	
	public String getGameInfoMessage(String playername)
	{
		String phse = getPhaseString();
		
		String gt = "MP_QUICKMATCH";
		String mycolor = getPlayerColor(playername);
		
		String whiteAvatar = "{";
		whiteAvatar+="\"profileId\":"  + this.whitePlayer.profileId + ",\"head\":"+this.whitePlayer.head + ",\"body\":" + this.whitePlayer.body;
		whiteAvatar+= ",\"leg\":" + this.whitePlayer.leg +",\"armBack\":" + this.whitePlayer.armBack + ",\"armFront\":" + this.whitePlayer.armFront+"}";
		
		String blackAvatar = "{";
		blackAvatar+="\"profileId\":"  + this.blackPlayer.profileId + ",\"head\":"+this.blackPlayer.head + ",\"body\":" + this.blackPlayer.body;
		blackAvatar+= ",\"leg\":" + this.blackPlayer.leg +",\"armBack\":" + this.blackPlayer.armBack + ",\"armFront\":" + this.blackPlayer.armFront+"}";
		
		
		String  whiteIdolTypes = "{";
		whiteIdolTypes+="\"profileId\":"+this.whitePlayer.profileId + ",\"type\":\""+this.whitePlayer.idolType + "\",\"idol1\":" + this.whitePlayer.idol1;
		whiteIdolTypes+= ",\"idol2\":" + this.whitePlayer.idol2 + ",\"idol3\":"+this.whitePlayer.idol3 + ",\"idol4\":"+this.whitePlayer.idol4 + ",\"idol5\":"+this.whitePlayer.idol5 + "}";
		
		String  blackIdolTypes = "{";
		blackIdolTypes+="\"profileId\":"+this.blackPlayer.profileId + ",\"type\":\""+this.blackPlayer.idolType + "\",\"idol1\":" + this.blackPlayer.idol1;
		blackIdolTypes+= ",\"idol2\":" + this.blackPlayer.idol2 + ",\"idol3\":"+this.blackPlayer.idol3 + ",\"idol4\":"+this.blackPlayer.idol4 + ",\"idol5\":"+this.blackPlayer.idol5 + "}";
		
		
		String customSettings = "[]"; //TODO (custom matches)
		
		//main json:
		String js = "{";
		
		js+= "\"white\":\"" + this.whitePlayer.name + "\",";
		js+= "\"black\":\"" + this.blackPlayer.name+ "\",";
		js+= "\"gameType\":\""+gt+ "\",";
		js+= "\"gameId\":"+this.gameID + ",";
		js+= "\"color\":\""+mycolor+ "\","; //own Color
		js+= "\"roundTimerSeconds\":"+maxRoundTimerSeconds + ",";
		js+= "\"phase\":\""+phse+ "\",";
		
		js+="\"whiteAvatar\":"+whiteAvatar+ ",";
		js+="\"blackAvatar\":"+blackAvatar+ ",";
		
		js+="\"whiteIdolTypes\":"+whiteIdolTypes+ ",";
		js+="\"blackIdolTypes\":"+blackIdolTypes+ ",";
		
		js+="\"customSettings\":"+customSettings+ ",";
		js+="\"rewardForIdolKill\":"+10+ ",";//TODO change this?
		js+="\"nodeId\":\"" + this.serverip + "\",";//TODO change this?
		js+="\"port\":" + this.port + ",";//TODO change this?
		
		js+="\"whiteIdols\":" + this.getIdolsString(Color.white)+ ",";
		js+="\"blackIdols\":" + this.getIdolsString(Color.black)+ ",";
		
		js+="\"refId\":" + 0 + ",";//for tutorial ... dunno
		js+="\"maxTierRewardMultiplier\":0.5,";
		String arr = "[0.025,0.05,0.1,0.1,0.1]";
		js+="\"tierRewardMultiplierDelta\":" + arr + ",";
		js+="\"msg\":" + "\"GameInfo\"}";
		
		//{"white":"ueHero","black":"Easy AI","gameType":"SP_QUICKMATCH","gameId":13323379,"color":"white",
		//"roundTimerSeconds":-1,"phase":"Init",
		//"whiteAvatar":{"profileId":68964,"head":117,"body":102,"leg":74,"armBack":99,"armFront":107},
		//"blackAvatar":{"profileId":128091,"head":37,"body":11,"leg":40,"armBack":1,"armFront":17}
		//,"whiteIdolTypes":{"profileId":68964,"type":"DEFAULT","idol1":2,"idol2":2,"idol3":2,"idol4":2,"idol5":2}
		//,"blackIdolTypes":{"profileId":128091,"type":"DEFAULT","idol1":1,"idol2":1,"idol3":1,"idol4":1,"idol5":1}
		//,"customSettings":[],"rewardForIdolKill":10,"nodeId":"54.236.217.221","port":8081,
		//"whiteIdols":[{"color":"white","position":0,"hp":10,"maxHp":10},{"color":"white","position":1,"hp":10,"maxHp":10},{"color":"white","position":2,"hp":10,"maxHp":10},{"color":"white","position":3,"hp":10,"maxHp":10},{"color":"white","position":4,"hp":10,"maxHp":10}],
		//"blackIdols":[{"color":"black","position":0,"hp":10,"maxHp":10},{"color":"black","position":1,"hp":10,"maxHp":10},{"color":"black","position":2,"hp":10,"maxHp":10},{"color":"black","position":3,"hp":10,"maxHp":10},{"color":"black","position":4,"hp":10,"maxHp":10}]
		//,"refId":0,"maxTierRewardMultiplier":0.5,"tierRewardMultiplierDelta":[0.025,0.05,0.1,0.1,0.1],"msg":"GameInfo"}
		 
		return js;
	}
	
	public void mulligan(long playerid)
	{
		
		if(this.GameState == 4) return; //game has ended
		//is the user allowed to use the ability
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		
		if(p.color != this.activePlayerColor) 
		{
			return ; // not your turn buddy!
		}
		
		ArrayList<Minion> tempHand = this.getPlayerHand(this.activePlayerColor);
		ArrayList<Minion> tempDeck = this.getPlayerDeck(this.activePlayerColor);
		
		int n = tempHand.size();
		tempDeck.addAll(tempHand);
		tempHand.clear();
		drawCards(this.activePlayerColor, n);
		
		this.addMulliganDisabledMessage();
		//this.addMessageToPlayer(this.activePlayerColor, this.getHandUpdateMessage(this.activePlayerColor));
		
		this.sendEffectMessagesToPlayers();
	}
	
	private String makeEffectMessage(String msg)
	{
		return "{\"effects\":[" + msg + "],\"msg\":\"NewEffects\"}";
	}
	
	private String makeBigEffectMessage(Color col)
	{
		ArrayList<String> msgs = this.messagesToBlack;
		if(col==Color.white)msgs = this.messagesToWhite;
		String msg="";
		for(String m : msgs)
		{
			if(!msg.equals("")) msg+=",";
			msg+=m;
		}
		msgs.clear();
		return "{\"effects\":[" + msg + "],\"msg\":\"NewEffects\"}";
	}
	
	
	public String getHandUpdateMessage(Color col)
	{
		ArrayList<Minion> hand = this.getPlayerHand(col);
		//{"HandUpdate":{"profileId":68964,"maxScrollsForCycle":7,"cards":[{"id":25531674,"typeId":49,"tradable":false,"isToken":false,"level":0},{"id":24010900,"typeId":302,"tradable":true,"isToken":false,"level":0},{"id":25531701,"typeId":26,"tradable":false,"isToken":false,"level":0},{"id":20958502,"typeId":156,"tradable":true,"isToken":false,"level":0},{"id":23976035,"typeId":289,"tradable":true,"isToken":false,"level":2}]}}
		Player p = this.getPlayer(col);
		String s = "{\"HandUpdate\":{\"profileId\":" + p.profileId + ",\"maxScrollsForCycle\":"+ this.maxScrollsForCycle+",\"cards\":[";
		String cards = "";
		for(Minion m : hand)
		{
			if(!cards.equals("")) cards += ",";
			//{"id":25531674,"typeId":49,"tradable":false,"isToken":false,"level":0}
			cards += "{\"id\":" + m.cardID + ",\"typeId\":" + m.typeId + ",\"tradable\":false,\"isToken\":" + Boolean.toString(m.isToken) + ",\"level\":" + m.lvl + "}";
			
		}
		s+=cards + "]}}";
		return s;
	}
	
	public String getActiveRessourcesMessage(Color col)
	{
		Player p = this.getPlayer(col);
		String s = "{\"types\":["+p.activeRessis+"],\"msg\":\"ActiveResources\"}";
		return s;
	}
	
	public String getTurnMessage(Color col)
	{
		String colo = Board.colorToString(this.activePlayerColor);
		String s ="{\"TurnBegin\":{\"color\":\""+ colo +"\",\"turn\":"+this.turnNumber+",\"secondsLeft\":"+maxRoundTimerSeconds+"}}";
		return s;
	}
	
	private void allMinionsOfASideCountDown(Color col, int ammount)
	{
		Minion[][] ms = this.getPlayerField(col);
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = ms[i][j];
				if(m != null)
				{
					
					if(m.getAc() >=1) 
					{
							m.buffMinionWithoutMessage(0, 0, -1, this);
							String s = getStatusUpdateMessage(m);
							this.addMessageToPlayer(Color.white, s);
							this.addMessageToPlayer(Color.black, s);
					}
				}
			}
		}
				
	}
	
	private void allMinionsOfASideResetCountDown(Color col)
	{
		Minion[][] ms = this.getPlayerField(col);
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = ms[i][j];
				if(m != null)
				{
					if(m.getAc() <=0 && m.maxAc>0) 
					{
							
							m.resetAc();
							String s = getStatusUpdateMessage(m);
							this.addMessageToPlayer(Color.white, s);
							this.addMessageToPlayer(Color.black, s);
					}
					
				}
			}
		}
				
	}
	

	public String getStatusUpdateMessage(Minion m)
	{
		if(m.isIdol)
		{
			String s="{\"IdolUpdate\":{\"idol\":{\"color\":\"" + Board.colorToString(m.position.color) + "\",\"position\":" + m.position.row + ",\"hp\":"+ m.Hp +",\"maxHp\":" + m.maxHP + "}}}";
			return s;
		}
		
		//{"StatsUpdate":{"target":{"color":"white","position":"2,1"},"hp":2,"ap":5,"ac":2,"buffs":[{"name":"Death Cap Berserk","description":"When Death Cap Berserk comes into play, and at the beginning of its turns, enchanted creature's Countdown is decreased by 2 and it is dealt 1 [magic damage].","type":"ENCHANTMENT"}]}}
		String s="{\"StatsUpdate\":{\"target\":"+m.position.posToString()+",\"hp\":"+ m.Hp +",\"ap\":"+ m.Ap +",\"ac\":"+ m.getAc() +",\"buffs\":[";
		String buffs="";
		for(Minion mm : m.attachedCards)
		{
			if(buffs.equals(""))
			{
				//BUFF
				buffs = "{\"name\":\"" + mm.buffName +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+mm.bufftype+"\"}";
			}
			else
			{
				buffs += ",{\"name\":\"" + mm.buffName +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+mm.bufftype+"\"}";
			}
		}
		
		s+=buffs + "]}}";
		return s;
	}
	
	
	//Rules are cound down
	private void rulecountdowner(Color col)
	{
		ArrayList<Minion> rules = this.getPlayerRules(col);
		ArrayList<Minion> removerules = new ArrayList<Minion>();
		ArrayList<Minion> grave = this.getPlayerGrave(col);
		for(Minion rule : rules)
		{
			if(rule.lingerDuration>=1) rule.lingerDuration--;
			String s = getRuleMessage(rule);
			this.addMessageToPlayer(Color.white, s);
			this.addMessageToPlayer(Color.black, s);
			if(rule.lingerDuration<=0) removerules.add(rule);
			
		}
		
		grave.addAll(removerules);
		rules.removeAll(removerules);
		
	}
	
	private String getRuleMessage(Minion rule)
	{
		//{"RuleUpdate":{"card":{"id":17305,"typeId":261,"tradable":false,"isToken":false,"level":0},"color":"white","roundsLeft":4}}
		//or 
		//{"RuleRemoved":{"card":{"id":15103,"typeId":261,"tradable":false,"isToken":false,"level":0},"color":"white","roundsLeft":0}}
	
		String s = "{\"card\":{\"id\":"+rule.cardID+",\"typeId\":"+rule.typeId+",\"tradable\":false,\"isToken\":false,\"level\":"+rule.lvl+"},\"color\":\""+Board.colorToString(rule.position.color)+"\",\"roundsLeft\":"+rule.lingerDuration+"}";
		if(rule.lingerDuration<=0)
		{
			return "{\"RuleRemoved\":"+s+"}";
		}
		
		return "{\"RuleUpdate\":"+s+"}";
		
	}
	
	
	private void refreshRessoures(Color col)
	{
		boolean sendupdate=false;
		if(col == Color.white)
		{
			
			for(int i=0; i<this.whitecurrentRessources.length;i++)
			{
				if(this.whitecurrentRessources[i] < this.whiteRessources[i])
				{
					this.whitecurrentRessources[i] = this.whiteRessources[i];
					sendupdate=true;
				}
			}
		}
		else
		{
			for(int i=0; i<this.blackcurrentRessources.length;i++)
			{
				if(this.blackcurrentRessources[i] < this.blackRessources[i])
				{
					this.blackcurrentRessources[i] = this.blackRessources[i];
					sendupdate=true;
				}
			}
		}
		
		if(sendupdate)
		{
			String s = this.getResourcesUpdateMessage();
			this.addMessageToBothPlayers(s);
		}
		
	}
	
	public String getResourcesUpdateMessage() {
		String whiteAssets = this.assetMessageBuilder(Color.white);
		String blackAssets = this.assetMessageBuilder(Color.black);
		String s="{\"ResourcesUpdate\":{\"whiteAssets\":"+whiteAssets+",\"blackAssets\":"+blackAssets+"}}";
		return s;
	}
	
	public String getCardStackUpdate(Color col)
	{
		ArrayList<Minion> lib = this.getPlayerDeck(col);
		ArrayList<Minion> grave = this.getPlayerGrave(col);
		//{"CardStackUpdate":{"color":"white","librarySize":3,"graveyardSize":1}}
		String s="{\"CardStackUpdate\":{\"color\":\""+Board.colorToString(col) + "\",\"librarySize\":" + lib.size() +",\"graveyardSize\":" + grave.size() + "}}";
		return s;
	}

	
	public void unitAttackStarting(Minion m, Minion[][] defffield)
	{
		String s ="";
		
		ArrayList<Minion> temp = new ArrayList<Minion>(m.attachedCards);
		for(Minion e : temp)
		{
			e.card.cardSim.onUnitIsGoingToAttack(this, e, m);//TODO bunnys do it after attack done! but concentrate fire not! (maybe we add two types?)
		}
		
		if(m.Hp<=0) return; // no attack if died
		
		if(m.card.cardSim.hasSpecialAttack()) //ether pump does this for example
		{
			//{"UnitPlayAnimation":{"tile":{"color":"white","position":"2,1"},"animation":"ACTION"}},
			s= "{\"UnitPlayAnimation\":{\"tile\":"+m.position.posToString()+",\"animation\":\"ACTION\"}}";
			this.addMessageToBothPlayers(s);
			m.card.cardSim.doSpecialAttack(this, m);
		}
		else
		{
			unitAttacking(m, defffield, m.Ap, m.attackType, DamageType.COMBAT);
		}
		//unit is ready
		
		temp = new ArrayList<Minion>(m.attachedCards);
		for(Minion e : temp)
		{
			if(e.typeId == 149) e.card.cardSim.onAttackDone(this, m, e);//only concentrate fire
		}
		
		s = getUnitAttackDoneMessage(m);
		this.addMessageToBothPlayers(s);
		
		m.card.cardSim.onAttackDone(this,m, m);//bunnys do it after attack done! but concentrate fire not! (maybe we add two types?)
		for(Minion e : temp)
		{
			if(e.typeId != 149) e.card.cardSim.onAttackDone(this, m, e);//for magmapack and stuff
		}
		//todo other minions call on Attack Done?
	}
	
	
	public void unitAttacking(Minion m, Minion[][] defffield, int attackvalue, AttackType attt, DamageType dmgt)
	{
		//writes UnitAttackTile or unitattackIdol message!
		//select positions, targets and stuff!
		//default attack:--------------------- (includes piercing + relentless)
		String s = "";
		
		ArrayList<Position> posis = Board.getAttackPositions(m);
		if(m.card.cardSim.hasSpecialAttackTarget())
		{
			posis = m.card.cardSim.getSpecialAttackTarget(this, m);
		}
		Color opcol = Board.getOpposingColor(m.position.color);
		ArrayList<Minion> idols = this.getPlayerIdols(opcol);
		ArrayList<Minion> targets = m.getTargets(defffield, posis, idols);

		
		int relentlessAttackvalue = 0;
		boolean idol=false;
		
		if(m.card.cardSim.doesAttack(this, m))
		{
			if(m.card.trgtArea == targetArea.RADIUS_4)
			{
				//its is a SiegeAttackTiles message!
				s = getSiegeAttackMessage(m, posis);
				this.addMessageToBothPlayers(s);
			}
			else
			{
				//hellspiters area is undefined, but he does also an unitattacktile message!
				//UnitAttackTile or UnitAttackIdol
				s = getUnitAttackMessage(m,targets);
				this.addMessageToBothPlayers(s);
			}
			
	
			//unit attacks (and attacks again if relentless):

			relentlessAttackvalue = this.doDmg(targets, m, attackvalue, attt, dmgt);
			
			for(Minion def : targets)
			{
				if(def.isIdol)idol=true;
			}
			
			
			if(idol) relentlessAttackvalue=0;
			
			boolean relentless = m.isRelentless || m.card.cardSim.isRelentless(this, m);
			for(Minion e: m.attachedCards)
			{
				relentless = relentless || e.card.cardSim.isRelentless(this, e);
			}
			
			if(relentless && relentlessAttackvalue >=1 && m.Hp>=1 )
			{
				unitAttacking(m, defffield, relentlessAttackvalue, attt, dmgt);
			}
			
		}

		
		return ;
	}
	
	
	
	
	private String getIdolInfoString(Minion m)
	{
		//"idol":{"color":"black","position":0,"hp":8,"maxHp":10}
		String s ="\"idol\":{\"color\":\""+Board.colorToString(m.position.color)+"\",\"position\":"+m.position.row+",\"hp\":"+m.Hp+",\"maxHp\":"+m.maxHP+"}";
		return s;
	}
	
	private String getSiegeAttackMessage(Minion m, ArrayList<Position> posis)
	{
		//"{"SiegeAttackTiles":{"source":{"color":"white","position":"1,1"},"targets":[{"color":"black","position":"1,1"},{"color":"black","position":"1,0"},{"color":"black","position":"0,1"},{"color":"black","position":"2,1"}]}}",

		String source=this.getSource(m);
		String targets = "";
		
		for(Position pos : posis)
		{
			if(!targets.equals("")) targets+=",";
			//{"color":"white","position":"0,0"}
			targets += pos.posToString();
		}
		
		String ret = "{\"SiegeAttackTiles\":{" + source + ",\"targets\":[" + targets + "]}}";

		return ret;
	}
	
	
	
	
	private String getSource(Minion m)
	{
		String source="\"source\":"+ m.position.posToString();
		return source;
	}
	
	private String getUnitAttackDoneMessage(Minion m)
	{
		String source=this.getSource(m);
		String ret = "{\"UnitAttackDone\":{"+ source +"}}";
		
		return ret;
	}
	
	
	private void allMinionsOfASideAttacks(Color col)
	{
		Color otherColor = Board.getOpposingColor(col);
		
		Minion[][] attackfield = this.getPlayerField(col);
		Minion[][] defffield = this.getPlayerField(otherColor);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = attackfield[i][j];
				if(m != null && m.getAc() == 0 && m.Hp>=1)
				{
				
					//minion is ready to attack
					this.unitAttackStarting(m, defffield);
					
				}
			}
		}
		
		doDeathRattles();
		
		
	}
	
	
	public void doTurnStartsTriggers()
	{
		
		for(Minion m: this.getAllMinionOfField())
		{
			if(m.Hp<=0) continue;
			m.movesThisTurn = 0;
			m.card.cardSim.onTurnStartTrigger(this, m, this.activePlayerColor);
			//tested: scrolls is doing this the same way:
			//if minion is healed, poisondmg that is performed after healding, is done too!
			ArrayList<Minion> temp = new ArrayList<Minion> (m.attachedCards); //we may change that list 
			for(Minion e : temp)
			{
				e.card.cardSim.onTurnStartTrigger(this, e, this.activePlayerColor);
				
				if(e.buffName.equals("Poison") && this.activePlayerColor == m.position.color) //perform poinson dmg on own turn
				{
					this.doDmg(m , e, 1, AttackType.UNDEFINED, DamageType.POISON);
				}
				
			}
		}
		this.doDeathRattles();
		
		
	}
	
	
	public void doTurnEndsTriggers()
	{
		
		for(Minion m: this.getAllMinionOfField())
		{
			if(m.Hp<=0) continue;
			m.card.cardSim.onTurnEndsTrigger(this, m, this.activePlayerColor);
			//delete turn-buffs and stuff
			m.turnEndingDebuffing(this);
		}
		this.doDeathRattles();
		
	}
	
	
	//is called from BattleThread (we got from player x {"phase" : "Init","msg" : "EndPhase"})
	public void doPhase (String phse, long playerid)
	{
		//TODO end phase (except of "Init") automatically after some time
		
		if(this.GameState == 4) return; //game has ended
		
		int phase = 0;
		if(phse.equals("PreMain"))phase = 1;
		if(phse.equals("Main"))phase = 2;
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		System.out.println("endphase: "+ phase + " curretn:" + this.GameState);
		if(phase == this.GameState)
		{
			
			if(phase==0)
			{
				int rdyplayers = initPlayer(p);
				
				if(rdyplayers==2)
				{
					this.GameState +=1;
					//init phase:
				
					this.initGame();//draw 4/5 cards
					
					//send gamestate
					this.whitePlayer.sendMessgeToBattleServer(this.getGameStateMessage());
					this.blackPlayer.sendMessgeToBattleServer(this.getGameStateMessage());
					
					//send handupdate
					this.whitePlayer.sendMessgeToBattleServer(this.makeEffectMessage(this.getHandUpdateMessage(Color.white)));
					this.blackPlayer.sendMessgeToBattleServer(this.makeEffectMessage(this.getHandUpdateMessage(Color.black)));
					
				
					//send acctive ressis
					this.whitePlayer.sendMessgeToBattleServer(this.getActiveRessourcesMessage(Color.white));
					this.blackPlayer.sendMessgeToBattleServer(this.getActiveRessourcesMessage(Color.black));
					
					//send start turn
					this.turnNumber++;
					this.hasSacrificed=false;
					String trnm = this.makeEffectMessage(this.getTurnMessage(this.activePlayerColor));
					this.setActivePlayerStuff();
					this.whitePlayer.sendMessgeToBattleServer(trnm);
					this.blackPlayer.sendMessgeToBattleServer(trnm);
					
				}
				
				return;
				
			}
			
			if(p.color!= this.activePlayerColor)
			{
				//sooo... a non-active player wants to change the GameState? <_< ... FUU!
				return;
			}
			
			if(this.GameState == 2) 
			{
				this.GameState =1;
			}
			else
			{
				if(this.GameState >=1) this.GameState +=1;
			}
			 
			
			if(phase==1)//do turn starting stuff
			{
			
				//TODO do effects of enchantments (like berserker) and other stuff!
				//--- and do this first---
				
				this.doTurnStartsTriggers();
				
				//lower AC
				this.rulecountdowner(this.activePlayerColor);
				
				//to know that death of vaettr will reduce current growth
				for(Minion m : this.getPlayerFieldList(this.activePlayerColor))
				{
					m.turnsInplay++;
				}
				
				this.refreshRessoures(this.activePlayerColor);
				
				this.allMinionsOfASideCountDown(this.activePlayerColor, 1);
				
				//draw a card
				this.drawCards(this.activePlayerColor, 1);
				
				String s = this.getResourcesUpdateMessage();
				this.addMessageToBothPlayers(s);
				//this.addMessageToPlayer(this.activePlayerColor, this.getHandUpdateMessage(this.activePlayerColor));
				
				//send handupdate to the active player
				
				
				
				//send cardstackupdates
				//s = getCardStackUpdate(this.activePlayerColor);
				//this.addMessageToBothPlayers(s);
				
				//send effect Messages!
				
				this.sendEffectMessagesToPlayers();
				return;

			}
			
			
			if(phase==2)//do end-turn stuff
			{
				String s ="";
				//TODO do attacking and endgame effects (like crimson bull)
				//--- and do this first---...............................................
				
				allMinionsOfASideAttacks(this.activePlayerColor);
				
				this.sendEffectMessagesToPlayers();

				//reset Ac (=current count down) of minions with Ac<=0-----------------------------------------
				
				this.doTurnEndsTriggers();
				this.allMinionsOfASideResetCountDown(this.activePlayerColor);
				this.sendEffectMessagesToPlayers();
				
				//the end:
				//switch active player, raise turn and send turnmessage to players
				this.activePlayerColor = Board.getOpposingColor(this.activePlayerColor);
				
				//send start new turn
				this.turnNumber++;
				this.hasSacrificed=false;
				
				String trnm = this.makeEffectMessage(this.getTurnMessage(this.activePlayerColor));
				this.setActivePlayerStuff();
				
				this.whitePlayer.sendMessgeToBattleServer(trnm);
				this.blackPlayer.sendMessgeToBattleServer(trnm);

			}
			
		}
		else
		{
			//TODO send error (correctly)
			String retstring = "{\"op\":\"EndPhase\",\"info\":\"Action not allowed! Phase name doesn't match. Msg phase: Init actual: PreMain\",\"msg\":\"Fail\"}";
			p.sendMessgeToBattleServer(retstring);
		}
	}

	private void setActivePlayerStuff()
	{
		
		this.currentHand = this.getPlayerHand(this.activePlayerColor);
		currentDeck = this.getPlayerDeck(this.activePlayerColor);
		currentGrave = this.getPlayerGrave(this.activePlayerColor);
		currentField = this.getPlayerField(this.activePlayerColor);
		currentRuleupdates = this.getPlayerRules(this.activePlayerColor);
		this.currentPlayer = this.getPlayer(this.activePlayerColor);
		Color oppc = this.getOpposingColor(this.activePlayerColor);
		this.opponentPlayer = this.getPlayer(oppc);
	}

	//return minion on specific position (null if not existing)
	public Minion getMinionOnPosition(Position pos)
	{
		
		for(Minion mnn : this.getPlayerFieldList(pos.color))
		{
			if(mnn.position.isEqual(pos)) 
			{
				return mnn;
			}
		}
		Minion m=null;
		return m;
	}
	
	//called from server to play a card
	public void playCard(long cardid, ArrayList<Position> positions, long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		if(this.activePlayerColor != p.color)
		{
			//TODO error
			return;
		}
		
		//search card
		Minion card = null;
		for(Minion c : this.currentHand)
		{
			if(c.cardID == cardid) card = c;
		}
		
		if(card == null)
		{
			//TODO error not such card in hand
			return;
		}
		
		//test if position is allowed:
		
		boolean allowedpos= true;
		ArrayList<Position> allowedPosses = new ArrayList<Position>();
		
		//get all allowed positions!
		if(card.card.cardKind == Kind.CREATURE || card.card.cardKind == Kind.STRUCTURE)
		{
			allowedPosses.addAll(this.getFreePositions(p.color));
		}
		
		if(card.card.cardKind == Kind.SPELL || card.card.cardKind == Kind.ENCHANTMENT)
		{
			tileSelector fts= card.card.cardSim.getTileSelectorForFirstSelection(); 
			if(fts!=tileSelector.None)
			{
				allowedPosses.addAll(getallLegalTargets(fts, card.position.color));
			}
			
		}
		
		//test if target position is allwed
		for(Position poo : positions)
		{
			boolean found = false;
			for(Position pooo : allowedPosses)
			{
				if(pooo.isEqual(poo)) found=true;
			}
			if(found==false) allowedpos=false;
		}
		
		if(!allowedpos || (positions.size()==0 && allowedPosses.size()>=1) )
		{
			//TODO error (position is not allowed, or position is required but not given)
			
			return;
		}
		
		//test if player has enough ressis
		Boolean hasEnoughResources = false;
		int[] cressis = this.blackcurrentRessources;
		if(p.color == Color.white) 
		{
			cressis=this.whitecurrentRessources;
		}
		
		int neededWild = 0;
		
		if(cressis[0] < card.card.costGrowth )
		{
			neededWild = card.card.costGrowth - cressis[0];
		}
		if(cressis[1] < card.card.costOrder )
		{
			neededWild = card.card.costOrder - cressis[1];
		}
		if(cressis[2] < card.card.costEnergy )
		{
			neededWild = card.card.costEnergy - cressis[2];
		}
		if(cressis[3] < card.card.costDecay )
		{
			neededWild = card.card.costDecay - cressis[3];
		}
		
		if(cressis[4] >= neededWild)
		{
			hasEnoughResources=true;
		}
		
		if(!hasEnoughResources) 
		{
			//TODO error
			return;
		}
		
		
		
		//remove card from hand
		this.currentHand.remove(card);
		
		//remove mana
		cressis[4]-=neededWild;
		cressis[0]-=card.card.costGrowth;
		cressis[1]-=card.card.costOrder;
		cressis[2]-=card.card.costEnergy;
		cressis[3]-=card.card.costDecay;
		for(int ii=0;ii<5;ii++)
		{
			cressis[ii] = Math.max(0, cressis[ii]);
		}
		
		String s = "";
		//ressource update message
		s=getResourcesUpdateMessage();
		this.addMessageToBothPlayers(s);
		
		
		//hand update message
		this.addMessageToPlayer(this.activePlayerColor, this.getHandUpdateMessage(this.activePlayerColor));
		
		//card played message
		s= this.getCardPlayMessage(card);
		this.addMessageToBothPlayers(s);
		
		//select Tiles message
		s=getSelectedTilesMessage(card,positions);
		this.addMessageToBothPlayers(s);

		
		if(card.cardType == Kind.CREATURE || card.cardType == Kind.STRUCTURE )
		{
			System.out.println("creature is summoned");
			Position posOfNewUnit = positions.get(0);
			this.summonUnitOnPosition(posOfNewUnit, card);
			
		}
		else
		{
			System.out.println("card is played");
			//TODO TargetTiles message
			card.card.cardSim.onCardPlay(this, this.activePlayerColor, positions, card);
		}

		//TODO do trigger onCardPlayedTrigger and stuff
		
		this.doDeathRattles();
		
		s=this.getCardStackUpdate(this.activePlayerColor);//TODO we may can delete this, if the last message on both stacks is a cardstack update?
		this.addMessageToBothPlayers(s);
		
		this.addMulliganDisabledMessage();
		
		this.sendEffectMessagesToPlayers();
		
	}
	
	private void addMulliganDisabledMessage()
	{
		if(!this.currentPlayer.canMulligan) return;
		this.currentPlayer.canMulligan=false;
		String s="";
		s="{\"MulliganDisabled\":{\"color\":\""+Board.colorToString(this.activePlayerColor)+"\"}}";
		
		this.addMessageToBothPlayers(s);
	}
	
	private String getCardPlayMessage(Minion card)
	{
		//{"CardPlayed":{"color":"white","card":{"id":17281,"typeId":261,"tradable":false,"isToken":false,"level":0}}}
		String s="{\"CardPlayed\":{\"color\":\"" + Board.colorToString(card.position.color) + "\",\"card\":{\"id\":" + card.cardID + ",\"typeId\":" + card.typeId + ",\"tradable\":false,\"isToken\":" + Boolean.toString(card.isToken) + ",\"level\":" + card.lvl + "}}}";
		return s;
	}
	
	private String getSelectedTilesMessage(Minion card, ArrayList<Position> posis)
	{
		//{"SelectedTiles":{"card":{"id":17281,"typeId":261,"tradable":false,"isToken":false,"level":0},"tiles":[],"area":"UNDEFINED","color":"white"}}
	
		String tiles ="";
		String area="UNDEFINED";
		if(card.cardType == Kind.CREATURE || card.cardType == Kind.ENCHANTMENT )
		{
			area="TILE";
		}
		else
		{
			area = card.card.trgtAreaString;
		}
		for(Position pos : posis)
		{
			if(!tiles.equals("")) tiles+=",";
			//{"color":"white","position":"0,0"}
			tiles += pos.posToString();
		}
		
		
		String s="{\"SelectedTiles\":{\"card\":{\"id\":" + card.cardID + ",\"typeId\":" + card.typeId + ",\"tradable\":false,\"isToken\":" + Boolean.toString(card.isToken) + ",\"level\":" + card.lvl + "}";
		s+=",\"tiles\":["+tiles+"],\"area\":\""+area+ "\",\"color\":\""+Board.colorToString(card.position.color)+"\"}}";
		return s;
	}
	
	public void summonUnitOnPosition(Position pos, Minion m)
	{
		Minion before = this.getPlayerField(pos.color)[pos.row][pos.column];
		
		if(before != null && before.Hp>=1) 
		{
			System.out.println("summon error, field full " + pos.posToString());
			return; //we also allow to add the minino if it has hp<=0 (maybe throud deathrattles it is replaced but not jet removed)
		}
		
		m.position.color = pos.color;
		m.position.row = pos.row;
		m.position.column = pos.column;
		
		m.setAc(m.card.ac);
		m.Ap = m.card.ap;
		m.Hp = m.card.hp;
		m.maxHP = m.Hp;
		
		m.deadTriggersDone=false;
		m.turnsInplay =0;
		m.movesThisTurn = 0;
		
		//summon unit + update unit messages:
		String s = getSummonUnitMessage(m);
		this.addMessageToBothPlayers(s);
		
		s = this.getStatusUpdateMessage(m);
		this.addMessageToBothPlayers(s);
		
		//do battlecry effect (of for scrolls: do the effects of the card :D)
		m.card.cardSim.getBattlecryEffect(this, m, null);
		
		//apply new effects to new minion
		for(Minion mins : this.getAllMinionOfField())
		{
			mins.card.cardSim.onMinionIsSummoned(this, mins, m);
		}
		
		//TODO add effects of rules-updates-scrolls and enchantments? (are there such enchantments?)
		
		//TODO add status updates if minion was changed? (should be done in the summon-triggers)
		
		//add minion to battlefield!
		this.getPlayerField(m.position.color)[m.position.row][m.position.column] = m;
		
	}
	
	
	private String getSummonUnitMessage(Minion m)
	{
		//{"SummonUnit":{"target":{"color":"white","position":"2,1"},"card":{"id":-1,"typeId":38,"tradable":true,"isToken":true,"level":0}}}
		String s ="{\"SummonUnit\":{\"target\":"+m.position.posToString()+",";
		s+="\"card\":{\"id\":"+m.cardID+",\"typeId\":"+m.typeId+",\"tradable\":false,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"}}}";
		return s;
	}
	
	public void surrendering(long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		
		this.GameState = 4;
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid ) p = this.whitePlayer;
		//send surrender effect to both
		String s = "{\"effects\":[{\"SurrenderEffect\":{\"color\":\""+Board.colorToString(p.color)+"\"}}],\"msg\":\"NewEffects\"}";
		this.whitePlayer.sendMessgeToBattleServer(s);
		this.blackPlayer.sendMessgeToBattleServer(s);
		
		String idolm="";
		ArrayList<Minion> idols = this.getPlayerIdols(p.color);
		for(Minion idol : idols)
		{
			idol.Hp=0;
			if(!idolm.equals(""))idolm+=",";
			idolm += getStatusUpdateMessage(idol);
		}
		s="{\"effects\":["+idolm+"],\"msg\":\"NewEffects\"}";


		this.whitePlayer.sendMessgeToBattleServer(s);
		this.blackPlayer.sendMessgeToBattleServer(s);
		
		//create endgame message:
		
		//{"effects":[
		//{"EndGame":{"winner":"black",
		//"whiteStats":{"profileId":68964,"idolDamage":0,"unitDamage":0,"unitsPlayed":0,"spellsPlayed":0,"enchantmentsPlayed":0,"scrollsDrawn":3,"totalMs":2433207,"mostDamageUnit":0},
		//"blackStats":{"profileId":128092,"idolDamage":0,"unitDamage":1,"unitsPlayed":1,"spellsPlayed":0,"enchantmentsPlayed":0,"scrollsDrawn":7,"totalMs":14,"mostDamageUnit":1,"mostDamageUnitId":8297},
		//"whiteGoldReward":{"matchReward":19,"tierMatchReward":0,"matchCompletionReward":0,"idolsDestroyedReward":0,"betReward":0},
		//"blackGoldReward":{"matchReward":0,"tierMatchReward":0,"matchCompletionReward":0,"idolsDestroyedReward":0,"betReward":0},
		
		//for custom games:
		//"lossGame":{"id":989,"name":"CARDS testing","profileId":68964,"code":"starts(P1);\ndifficulty(easy)\nhide();\nresources(P1, growth, 10 );\nresources(P1, special, 100 );\nunit(P1, Gravelock Elder, 1, 1);\nunit(P1, Druid Burial Ground, 3, 0);\nunit(P1, Catapult of Goo, 3, 2);\nunit(P2, Gravelock Elder, 1, 1);\nunit(P2, Druid Burial Ground, 3, 0);\nunit(P2, Catapult of Goo, 3, 2);\ndeck(P1, 100,100,13);\n","descriptionP1":"- You take the first turn.\n- Ai difficulty: easy.\n- This custom game is hidden!\n- You have custom deck settings.\n\n- You start with Gravelock Elder, Druid Burial Ground, Catapult of Goo\n- You start with 10 growth resources\n\n- Your opponent starts with Gravelock Elder, Druid Burial Ground, Catapult of Goo\n","descriptionP2":"- Your opponent takes the first turn.\n- Ai difficulty: easy.\n- This custom game is hidden!\n- Your opponent has custom deck settings.\n\n- You start with Gravelock Elder, Druid Burial Ground, Catapult of Goo\n\n- Your opponent starts with Gravelock Elder, Druid Burial Ground, Catapult of Goo\n- Your opponent starts with 10 growth resources\n","totalRates":1,"totalRating":3,"isSinglePlayer":true,"profileName":"ueHero","deckP1":"- Always in deck:\n    1x Elan Vital\n    2x Ranger's Bane\n","deckP2":"","bet":"","timer":"","compileSuccess":true,"chooseDeckP1":false,"chooseDeckP2":true,"chooseDifficulty":false,"isPublic":false,"isPuzzle":false,"isCampaign":false}}}],"msg":"NewEffects"}
		
		//],"msg":"NewEffects"}
		
		Color winnercolor = Board.getOpposingColor(p.color);
		//TODO statistics :D
		s= "{\"EndGame\":{\"winner\":\""+Board.colorToString(winnercolor)+"\",";
		s+="\"whiteStats\":{\"profileId\":"+this.whitePlayer.profileId+",\"idolDamage\":0,\"unitDamage\":0,\"unitsPlayed\":0,\"spellsPlayed\":0,\"enchantmentsPlayed\":0,\"scrollsDrawn\":42,\"totalMs\":2433207,\"mostDamageUnit\":0},"; // TODO ,\"mostDamageUnitId\":8297
		s+="\"blackStats\":{\"profileId\":"+this.blackPlayer.profileId+",\"idolDamage\":0,\"unitDamage\":0,\"unitsPlayed\":0,\"spellsPlayed\":0,\"enchantmentsPlayed\":0,\"scrollsDrawn\":42,\"totalMs\":2433207,\"mostDamageUnit\":0},"; // TODO ,\"mostDamageUnitId\":8297
		s+="\"whiteGoldReward\":{\"matchReward\":10,\"tierMatchReward\":0,\"matchCompletionReward\":0,\"idolsDestroyedReward\":0,\"betReward\":0},";
		s+="\"blackGoldReward\":{\"matchReward\":10,\"tierMatchReward\":0,\"matchCompletionReward\":0,\"idolsDestroyedReward\":0,\"betReward\":0}";
		
		s+="}}";
				
	
		String eg="{\"effects\":["+s+"],\"msg\":\"NewEffects\"}";
		
		this.whitePlayer.sendMessgeToBattleServer(eg);
		this.blackPlayer.sendMessgeToBattleServer(eg);
		System.out.println("endgamemsg sended");
		
	}
	
	
	
	public void handleCardInfoMessage(long cardId, long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		//{"card":{"id":23768093,"typeId":212,"tradable":true,"isToken":false,"level":0},"hasEnoughResources":false,"data":{"selectableTiles":{"tileSets":[[{"color":"white","position":"0,0"},{"color":"white","position":"1,0"},{"color":"white","position":"2,0"},{"color":"white","position":"3,0"},{"color":"white","position":"4,0"},{"color":"white","position":"0,1"},{"color":"white","position":"1,1"},{"color":"white","position":"2,1"},{"color":"white","position":"3,1"},{"color":"white","position":"4,1"},{"color":"white","position":"0,2"},{"color":"white","position":"1,2"},{"color":"white","position":"2,2"},{"color":"white","position":"3,2"},{"color":"white","position":"4,2"}]]},"targetArea":"TILE"},"alerts":["Not enough resources"],"msg":"CardInfo"}
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid ) p = this.whitePlayer;
		
		if(this.activePlayerColor != p.color)
		{
			//TODO ERROR
			//not your turn
			//p.sendMessgeToBattleServer(msg);
			return;
		}
		
		
		ArrayList<Minion> hand = this.getPlayerHand(p.color);
		
		Minion card = null;
		for(Minion m : hand)
		{
			if(m.cardID == cardId) card = m;
		}
		
		if(card == null)
		{
			//TODO error (not such card in hand)
			return;
		}
		
		Boolean hasEnoughResources = false;
		int[] cressis = this.blackcurrentRessources;
		if(p.color == Color.white) 
		{
			cressis=this.whitecurrentRessources;
		}
		
		int neededWild = 0;
		
		if(cressis[0] < card.card.costGrowth )
		{
			neededWild = card.card.costGrowth - cressis[0];
		}
		if(cressis[1] < card.card.costOrder )
		{
			neededWild = card.card.costOrder - cressis[1];
		}
		if(cressis[2] < card.card.costEnergy )
		{
			neededWild = card.card.costEnergy - cressis[2];
		}
		if(cressis[3] < card.card.costDecay )
		{
			neededWild = card.card.costDecay - cressis[3];
		}
		
		if(cressis[4] >= neededWild)
		{
			hasEnoughResources=true;
		}
		
		//create cardinfo:
		String s = "{\"card\":{\"id\":" + card.cardID + ",\"typeId\":" + card.typeId + ",\"tradable\":true,\"isToken\":" + Boolean.toString(card.isToken) + ",\"level\":" + card.lvl + "}";
		s+= ",\"hasEnoughResources\":" + Boolean.toString(hasEnoughResources) + ",\"data\":{\"selectableTiles\":{\"tileSets\":[";
		 
		String tilesets = "";
		String alerts ="";
		//TODO get alerts
		if(!hasEnoughResources) alerts = "Not enough resources"; //are there more alerts?
		
		
		
		if(card.card.cardKind == Kind.CREATURE || card.card.cardKind == Kind.STRUCTURE)
		{
			ArrayList<Position> posis = this.getFreePositions(p.color);
			
			for(Position pos : posis)
			{
				if(!tilesets.equals("")) tilesets+=",";
				//{"color":"white","position":"0,0"}
				tilesets += pos.posToString();
			}
			
			tilesets = "["+ tilesets + "]"; //even if its empty
			
			s+=tilesets;
			
			s+="]},\"targetArea\":\"TILE\"},\"alerts\":[\""+ alerts +"\"],\"msg\":\"CardInfo\"}";
			
			p.sendMessgeToBattleServer(s);
			return;
		}
		
		
		if(card.card.cardKind == Kind.SPELL || card.card.cardKind == Kind.ENCHANTMENT)
		{
			tileSelector fts= card.card.cardSim.getTileSelectorForFirstSelection(); 
			if(fts!=tileSelector.None)
			{
				ArrayList<Position> tiles1 = getallLegalTargets(fts, card.position.color);
				
				String ts1="";
				for(Position pos : tiles1)
				{
					if(!ts1.equals("")) ts1+=",";
					ts1 += pos.posToString();
				}
				tilesets = "["+ ts1 + "]";
				
				tileSelector sts = card.card.cardSim.getTileSelectorForSecondSelection();
				if(sts!=tileSelector.None)
				{
					ArrayList<Position> tiles2 = getallLegalTargets(sts, card.position.color);
					String ts2="";
					for(Position pos : tiles2)
					{
						if(!ts2.equals("")) ts2+=",";
						ts2 += pos.posToString();
					}
					tilesets += ",["+ ts2 + "]";
				}
				
			}
			
			s+=tilesets;
			String tarea= card.card.trgtAreaString;
			
			// TODO ,"targetArea":"SEQUENTIAL","targetAreaGroups":[... (for cards like thundersurge) ]}, alerts:...
			String targetAreaGroups = ""; //optional only if sequential
			if(tarea.equals("SEQUENTIAL"))
			{
				//TODO get all sequentials!
				
				ArrayList<ArrayList<Position>> sequentials = getSequentialPositions(Color.white);
				for(ArrayList<Position> sequals : sequentials)
				{
					String sequal = "";
					for(Position po : sequals)
					{
						if(!sequal.equals("")) sequal+=",";
						sequal+=po.posToString();
					}
					
					if(!targetAreaGroups.equals("")) targetAreaGroups+="],[";
					targetAreaGroups+=sequal;
				}
				
				sequentials.clear();
				sequentials = getSequentialPositions(Color.black);
				for(ArrayList<Position> sequals : sequentials)
				{
					String sequal = "";
					for(Position po : sequals)
					{
						if(!sequal.equals("")) sequal+=",";
						sequal+=po.posToString();
					}
					
					if(!targetAreaGroups.equals("")) targetAreaGroups+="],[";
					targetAreaGroups+=sequal;
				}
				
				targetAreaGroups = ",\"targetAreaGroups\":[[" +  targetAreaGroups + "]]";
				
			}
			
			if(!alerts.equals(""))
			{
				alerts = ",\"alerts\":[\""+ alerts +"\"]";
			}
			
			s+="]},\"targetArea\":\""+tarea+"\""+ targetAreaGroups +"}"+ alerts +",\"msg\":\"CardInfo\"}";
			//TODO is alerts:[] optional?
			p.sendMessgeToBattleServer(s);
			return;
		}
		
	}
	
	private ArrayList<Position> getallLegalTargets(tileSelector ts, Color ownColor)
	{
		ArrayList<Position> ret = new ArrayList<Position>();
		
		if(ts == tileSelector.None)
		{
			//do nothing :D
			return ret;
		}
		
		if(ts == tileSelector.all)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					ret.add(new Position(Color.white, i, j));
					ret.add(new Position(Color.black, i, j));
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_units)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null )
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null )
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_melees)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && m.attackType == AttackType.MELEE )
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null && m.attackType == AttackType.MELEE )
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_lobbers_or_ranged_units)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && (m.attackType == AttackType.RANGED || m.attackType == AttackType.BALLISTIC) )
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.attackType == AttackType.RANGED || m.attackType == AttackType.BALLISTIC) )
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_units_with_ac)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && m.maxAc>=1 )
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null && m.maxAc>=1 )
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_creatures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE))
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE))
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_structures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && (m.card.cardKind == Kind.STRUCTURE))
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.card.cardKind == Kind.STRUCTURE))
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_free)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m==null )
					{
						ret.add(new Position(Color.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m==null )
					{
						ret.add(new Position(Color.black, i, j));
					}
				}
			}
			return ret;
		}
		
		// opponent stuff----------------------------------------------
		Color oppCol= Board.getOpposingColor(ownColor);
		
		Minion[][] field = this.getPlayerField(oppCol);
		
		if(ts == tileSelector.opp_all)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					ret.add(new Position(oppCol, i, j));
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.opp_units)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null )
					{
						ret.add(new Position(oppCol, i, j));
					}
					
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.opp_creatures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE))
					{
						ret.add(new Position(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.opp_structures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null && (m.card.cardKind == Kind.STRUCTURE))
					{
						ret.add(new Position(oppCol, i, j));
					}
					
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.opp_free)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m==null )
					{
						ret.add(new Position(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		//own---------------------------------------------------------------
		oppCol= ownColor;
		field = this.getPlayerField(oppCol);
		
		if(ts == tileSelector.own_all)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					ret.add(new Position(oppCol, i, j));
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.own_units)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null )
					{
						ret.add(new Position(oppCol, i, j));
					}
					
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.own_creatures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE))
					{
						ret.add(new Position(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.own_structures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null && (m.card.cardKind == Kind.STRUCTURE))
					{
						ret.add(new Position(oppCol, i, j));
					}
					
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.own_free)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m==null )
					{
						ret.add(new Position(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		return ret;
	}
	
	
	public void sacrificeCard(long cardid, String ressource, long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		//{"effects":[
		//{"CardSacrificed":{"color":"white","resource":"GROWTH"}},
		//{"ResourcesUpdate":{"whiteAssets":{"availableResources":{"DECAY":0,"ORDER":0,"ENERGY":0,"GROWTH":2,"SPECIAL":0},"outputResources":{"DECAY":0,"ORDER":0,"ENERGY":0,"GROWTH":2,"SPECIAL":0},"ruleUpdates":[],"handSize":4,"librarySize":44,"graveyardSize":2},"blackAssets":{"availableResources":{"DECAY":0,"ORDER":0,"ENERGY":0,"GROWTH":0,"SPECIAL":0},"outputResources":{"DECAY":0,"ORDER":0,"ENERGY":0,"GROWTH":0,"SPECIAL":0},"ruleUpdates":[],"handSize":6,"librarySize":44,"graveyardSize":0}}},
		//{"HandUpdate":{"profileId":92923,"maxScrollsForCycle":7,"cards":[{"id":14168225,"typeId":38,"tradable":true,"isToken":false,"level":0},{"id":13849608,"typeId":75,"tradable":true,"isToken":false,"level":0},{"id":23975181,"typeId":117,"tradable":true,"isToken":false,"level":0},{"id":13196722,"typeId":41,"tradable":true,"isToken":false,"level":0}]}},
		//{"CardStackUpdate":{"color":"white","librarySize":44,"graveyardSize":2}}
		//],"msg":"NewEffects"}

		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		
		if(this.activePlayerColor != p.color)
		{
			//TODO ERROR
			return;
		}
		
		if(this.activePlayerColor != p.color)
		{
			//TODO ERROR
			//not your turn
			return;
		}
		
		if(this.hasSacrificed)
		{
			//TODO ERROR
			//allready sacced
			return;
		}
		
		// "DECAY"  "ORDER"  "ENERGY"  "GROWTH"  "SPECIAL" "CARDS"
		
		int[] mressis = this.blackRessources;
		int[] cressis = this.blackcurrentRessources;
		if(p.color == Color.white) 
		{
			mressis=this.whiteRessources;
			cressis=this.whitecurrentRessources;
		}
		
		
		if(ressource.equals("SPECIAL"))
		{
			
			if(!this.canAllwaysSacForWild)
			{
				//test if we can sac for special
				if(!p.multicolor)
				{
					//cant sac for wild
					//TODO ERROR
					//p.sendMessgeToBattleServer(msg);
					return;
				}
			
			
				int min = mressis[0];
				for(int i = 1;i<3;i++)
				{
					if(mressis[i] < min) min=mressis[i];
				}
			
				if(min <= mressis[4])
				{
					//cant sac for wild (again)
					//TODO ERROR
					//p.sendMessgeToBattleServer(msg);
					return;
				}
			}
			//we can sac:
			
			mressis[4]++;
			cressis[4]++;
			
		}
		
		if(ressource.equals("GROWTH"))
		{
			mressis[0]++;
			cressis[0]++;
		}
		
		if(ressource.equals("ORDER"))
		{
			mressis[1]++;
			cressis[1]++;
		}
		
		if(ressource.equals("ENERGY"))
		{
			mressis[2]++;
			cressis[2]++;
		}
		
		if(ressource.equals("DECAY"))
		{
			mressis[3]++;
			cressis[3]++;
		}
		
		Minion sacCard = this.currentHand.get(0);
		for(Minion m : this.currentHand)
		{
			if(m.cardID == cardid) sacCard = m;
		}
		
		this.currentHand.remove(sacCard);
		this.currentGrave.add(sacCard);
		
		
		
		String s = "{\"CardSacrificed\":{\"color\":\""+ Board.colorToString(p.color) +"\",\"resource\":\""+ ressource +"\"}}";
		this.messagesToWhite.add(s);
		this.messagesToBlack.add(s);
		
		s= this.getResourcesUpdateMessage();
		this.messagesToWhite.add(s);
		this.messagesToBlack.add(s);
		
		if(ressource.equals("CARDS"))
		{
			//same message as sac for ressource, just draw 2 cards...
			
			this.drawCards(this.activePlayerColor, 2);
			
		}
		else
		{
			s=this.getHandUpdateMessage(this.activePlayerColor);
			this.addMessageToPlayer(this.activePlayerColor, s);
			s= this.getCardStackUpdate(this.activePlayerColor);
		}
		
		
		
		
		this.messagesToWhite.add(s);
		this.messagesToBlack.add(s);

		
		this.addMulliganDisabledMessage();
		
		this.sendEffectMessagesToPlayers();
		
		this.hasSacrificed=true;
	}
	
	
	public static String colorToString(Color col)
	{
		if(col == Color.white) return "white";
		return "black";
	}
	
	public static String attackTypeToString(AttackType att)
	{
		if(att == AttackType.MELEE) return "MELEE";
		if(att == AttackType.BALLISTIC) return "BALLISTIC";
		if(att == AttackType.MELEE_COUNTER) return "MELEE_COUNTER";
		if(att == AttackType.RANGED) return "RANGED";
		if(att == AttackType.UNDEFINED) return "UNDEFINED";
		return "UNDEFINED";
	}
	
	public static String damageTypeToString(DamageType dt)
	{
		if(dt == DamageType.COMBAT) return "COMBAT";
		if(dt == DamageType.MAGICAL) return "MAGICAL";
		if(dt == DamageType.POISON) return "MAGICAL"; // no shit... game is doing this
		if(dt == DamageType.SUPERIOR) return "MAGICAL";// no shit... game is doing this!
		if(dt == DamageType.TERMINAL) return "TERMINAL";
		return "MAGICAL";
	}
	
	public static Color getOpposingColor(Color col)
	{
		if(col == Color.white) return Color.black;
		return Color.white;
	}
	
	public ArrayList<Minion> getMinionsFromPositions(ArrayList<Position> ps)
	{
		ArrayList<Minion> mins = new ArrayList<Minion>();
		for(Minion m : this.getAllMinionOfField())
		{
			for(Position p : ps)
			{
				if(m.position.isEqual(p))
				{
					mins.add(m);
				}
			}
		}
		return mins;
	}
	
	
	public  ArrayList<Position> getFreePositions(Color col)
	{
		ArrayList<Position> posMoves = new ArrayList<Position>();
		
		Minion[][] side = this.getPlayerField(col);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = side[i][j];
				if(m == null )
				{
					posMoves.add(new Position(col, i, j));
				}
			}
		}
		
		return posMoves;
	}
	
	
	ArrayList<Position> dfsList= new ArrayList<Position>();//shared variable for DFS
	ArrayList<Position> dfsSolutionList= new ArrayList<Position>();
	
	public  ArrayList<ArrayList<Position>> getSequentialPositions(Color col)
	{
		this.dfsList.clear();
		
		Color markCol = Board.getOpposingColor(col);
		Minion[][] side = this.getPlayerField(col);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = side[i][j];
				if(m != null )
				{
					this.dfsList.add(new Position(col, i, j));
				}
			}
		}
		Color opcol = Board.getOpposingColor(col);

		ArrayList<ArrayList<Position>> retval = new ArrayList<ArrayList<Position>>();

		for(Position p1 : this.dfsList)
		{
			if(p1.color == markCol) continue;
			this.dfsSolutionList.clear();
			
			this.deepFirstSearch(p1, markCol);
			
			ArrayList<Position>blubb = new ArrayList<Position>(this.dfsSolutionList); //we can take the elements of dfsSol.list, they are created with new
			
		}
		
		
		return retval;
	}
	
	private void deepFirstSearch(Position p, Color markCol)
	{
		//add to sol.list + mark!
		this.dfsSolutionList.add(new Position(p));
		p.color = markCol;
		
		ArrayList<Position> neightbours = p.getNeightbours();
		
		for(Position p1 : this.dfsList)
		{
			if(p1.color == markCol) continue;
			
			//test if p1 is a neightbour of p
			boolean isNeightbour= false;
			for(Position pipi : neightbours)
			{
				if(pipi.column == p1.column && pipi.row == p1.row) isNeightbour=true; //we ignore color, cause p is marked
			}
			
			if(isNeightbour)//we use colorigno, cause we use color as marker :D
			{
				this.deepFirstSearch(p1, markCol);
			}
			
		}
	}
	
	
	
	public static ArrayList<Position> getAttackPositions(Minion m)
	{
		targetArea area = m.card.trgtArea;
		ArrayList<Position> posMoves = new ArrayList<Position>();
		int attackerRow = m.position.row;
		int attackerColumn = m.position.column;
		Color col = Board.getOpposingColor(m.position.color);
		
		//only forward and radius4 are legal
		
		if(area == targetArea.FORWARD)
		{
			int rowdi = attackerRow;
			for(int i = 0; i<3; i++)
			{
				Position p = new Position(col, rowdi, i);
				posMoves.add(p);
			}
		}
		
		if(area == targetArea.RADIUS_4)
		{
			int rowdi = attackerRow;
			int columndi= attackerColumn;
			if(attackerColumn == 2) columndi=0;
			if(attackerColumn == 0) columndi=2;
			if(rowdi==1 || rowdi==3) attackerColumn--;
			
			// original attacking order:
			
			//tile after first tile
			columndi++;
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new Position(col, rowdi, columndi));
			columndi--;
			
			//first tile
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new Position(col, rowdi, columndi));
			
			//tile over first tile
			if(attackerRow==1 || attackerRow==3) columndi++;
			rowdi--;
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new Position(col, rowdi, columndi));
			if(attackerRow==1 || attackerRow==3) columndi--;
			rowdi++;
			
			//tile under first tile
			rowdi++;
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new Position(col, rowdi, columndi));
			
		}
		
		if(area == targetArea.RADIUS_7)//there is not such an unit that attacks 7 tiles!
		{
			
		}
		
		return posMoves;
	}
	
	
	
	public ArrayList<Position> getMovePositions(Color col, int row, int column)
	{
		Position pp = new Position(col, row, column);
		ArrayList<Position> posMoves = pp.getNeightbours();
		
		ArrayList<Position> posMoves2 = new ArrayList<Position>();
		Minion[][] chosenField = this.getPlayerField(col);
		
		for(Position p : posMoves)
		{
			if((chosenField[p.row][p.column]) == null)
			{
				posMoves2.add(p);
			}
		}
		
		return posMoves2;
	}
	
	
	public String getPossibleMoveString(Color col, int row, int column)
	{
		String s ="";
		//{\"color\":\"white\",\"position\":\"1,1\"},
		for(Position p : this.getMovePositions(col, row, column))
		{
			if(s.equals(""))
			{
				s = "{\"color\":\""+Board.colorToString(p.color)+"\",\"position\":\""+ p.row +"," + p.column + "\"}";
			}
			else
			{
				s += ",{\"color\":\""+Board.colorToString(p.color)+"\",\"position\":\""+ p.row +"," + p.column + "\"}";
			}
		}
		return s;
	}
	 
	
	public void getAbilityInfo(String ability, Position posi , long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		
		String s="";
		
		if(this.activePlayerColor != posi.color) 
		{
			s="{\"op\":\"ActivateAbilityInfo\",\"info\":\"Action not allowed! Invalid board side\",\"msg\":\"Fail\"}";
			p.sendMessgeToBattleServer(s);
			return;
		}
		
		if(this.activePlayerColor != p.color) 
		{
			s="{\"op\":\"ActivateAbilityInfo\",\"info\":\"Action not allowed! Not your turn\",\"msg\":\"Fail\"}";
			p.sendMessgeToBattleServer(s);
			return;
		}
		
		Minion m = this.getPlayerField(posi.color)[posi.row][posi.column];
		if(m==null) return;//throw error?
		
		String tilesets = "";
		String color = Board.colorToString(posi.color);
		
		
		boolean playable = true;
		boolean ressis = true;
		/*if(ability=="Move")
		{
			playable = m.canMove();
			ressis=true;
			
			tilesets = this.getPossibleMoveString(col, row, column);
			if(tilesets.equals("")) playable=false;
		}
		else
		{*/
			ActiveAbility va = new ActiveAbility(ability, 0, 0, 0, 0, 0);
			ArrayList<Position> pospositions = new ArrayList<Position>();
			for(ActiveAbility aa : m.card.abilitys )
			{
				if(aa.id ==  va.id)
				{
					
					ressis= aa.hasEnoughRessis(this, m);
					pospositions.addAll(aa.getPositions(this, m));
					playable = aa.isPlayAble(this, m, pospositions);
					break;
				}
			}
			
			tilesets = "";
			for(Position popo : pospositions )
			{
				if(!tilesets.equals("")) tilesets += ",";
				tilesets += popo.posToString();
			}
			
		//}
		
		
		
		String hasEnoughResources = Boolean.toString(ressis);
		String isPlayable = Boolean.toString(playable);
		s = "{\"unitPosition\":"+posi.posToString()+",\"abilityId\":\""+ability+"\",\"hasEnoughResources\":"+hasEnoughResources+",\"isPlayable\":"+isPlayable+",\"data\":{\"selectableTiles\":{\"tileSets\":[[" + tilesets +"]]},\"targetArea\":\"UNDEFINED\"},\"msg\":\"AbilityInfo\"}";

		//{\"color\":\"white\",\"position\":\"1,1\"},...
		
		
		p.sendMessgeToBattleServer(s);
		return ;
	}

	
	public void activateAbility(String ability, Position unitPos, ArrayList<Position> poses, long playerid)
	{
		if(this.GameState == 4) return; //game has ended
		//is the user allowed to use the ability
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		
		if(this.activePlayerColor != p.color) 
		{
			//TODO error 
			return;
		}
		
		Minion m = this.getPlayerField(unitPos.color)[unitPos.row][unitPos.column];
		if(m==null)
		{
			//TODO error
			return;
		}
		
		boolean playable = true;
		boolean ressis = true;
		boolean needposi = false;
		
		ActiveAbility va = new ActiveAbility(ability, 0, 0, 0, 0, 0);
		ArrayList<Position> pospositions = new ArrayList<Position>();
		for(ActiveAbility aa : m.card.abilitys )
		{
			if(aa.id ==  va.id)
			{
				va = aa;
				ressis= aa.hasEnoughRessis(this, m);
				pospositions.addAll(aa.getPositions(this, m));
				playable = aa.isPlayAble(this, m, pospositions);
				needposi = aa.needPosition(this, m);
				break;
			}
		}
		
		if(!playable || !ressis)
		{
			//TODO error
			return;
		}
		
		if(needposi && poses.size() ==0)
		{
			//TODO error (target position is needed, but not sended to us)
			return;
		}
		
		if(needposi && poses.size() >=1 )
		{
			Boolean isinList = false;
			Position tp = poses.get(0);
			for(Position ppp : pospositions)
			{
				if(tp.isEqual(ppp)) isinList=true;
			}
			
			if(!isinList) 
			{
				//TODO error (wrong position)
				return;
			}
		}
		
		//we can use the ability now!
		String s = "{\"UnitActivateAbility\":{\"unit\":"+m.position.posToString()+",\"name\":\""+ability+"\"}}";
		this.addMessageToBothPlayers(s);
		s = this.getSelectedTilesMessage(m, poses);
		this.addMessageToBothPlayers(s);
		
		if(ability.equals("Move"))
		{
			Position targ = poses.get(0);
			s = "{\"MoveUnit\":{\"from\":"+unitPos.posToString()+",\"to\":"+targ.posToString()+"}}";
			this.addMessageToBothPlayers(s);
			m.movesThisTurn++;
			this.unitChangesPlace(unitPos, targ);
		}
		else
		{
			m.card.cardSim.onAbilityIsActivated(this, m, poses);
		}
		
		//pay energy:
		//dont do this earlier!
		va.payEnergy(this, m);
		
		
		//trigger onFieldChanged!
		//doOnFieldChangedTriggers();
		doOnFieldChangedTriggers();
		this.sendEffectMessagesToPlayers();
	}
	
	//with switching, minions change place
	public void unitChangesPlace(Position ofrom, Position oto, Boolean doTrigger)
	{
		Position from  = new Position(ofrom);
		Position to  = new Position(oto);
		
		Minion m = this.getPlayerField(from.color)[from.row][from.column];
		if(m==null) return;//error?
		
		Minion otherm = this.getPlayerField(to.color)[to.row][to.column];
		if(otherm!=null)
		{
			otherm.position.color = from.color;
			otherm.position.row = from.row;
			otherm.position.column = from.column;
		}
		
		m.position.color = to.color;
		m.position.row = to.row;
		m.position.column = to.column;
		
		this.getPlayerField(to.color)[to.row][to.column] = m;
		this.getPlayerField(from.color)[from.row][from.column] = otherm;
		
		if(doTrigger)doOnFieldChangedTriggers();
	}
	
	public void unitChangesPlace(Position from, Position to)
	{
		unitChangesPlace( from,  to, true);
	}
	
	public void doOnFieldChangedTriggers()
	{
		//TODO also for enchantments? dont think so or?
		
		for(Minion m : this.getAllMinionOfField())
		{
			m.card.cardSim.onFieldChanged(this, m);
		}
		
	}
	
	private class DmgRegisterUnit
	{
		public Minion attacker;
		public Minion deffender;
		public AttackType attackType;
		public int dmgdone;
		
		public DmgRegisterUnit(Minion a, Minion d, AttackType att, int dmgd)
		{
			this.attacker=a;
			this.deffender =d;
			this.attackType=att;
			this.dmgdone = dmgd;
		}
	}
	
	private ArrayList<DmgRegisterUnit>dmgregister = new ArrayList<DmgRegisterUnit>();
	
	//dmg calculations + trigger of events#######################################################
	//if dmg == -100 use the minon.aoeDmgToDo value instead (and set it to zero)
	public int doDmg(Minion target, Minion attacker, int dmg, AttackType attackType, DamageType damageType )
	{
		ArrayList<Minion> targs = new ArrayList<Minion>();
		targs.add(target);
		return doDmg( targs,  attacker,  dmg,  attackType,  damageType );
		
	}
	
	public int doDmg(ArrayList<Minion> targets, Minion attacker, int dmg, AttackType attackType, DamageType damageType )
	{
		int overdmg = performDmg(targets, attacker, attackType, damageType, dmg);

        while (this.dmgregister.size() >= 1)
        {
        	DmgRegisterUnit pair = this.dmgregister.get(0);
            this.dmgregister.remove(0);
            performDmgTriggers(pair.attacker, pair.deffender, pair.attackType, pair.dmgdone);
        }
        
        
        //TODO diedtriggers + remove minion
        
        ArrayList<Minion> allmins = this.getAllMinionOfField();
        for(Minion m : allmins)
        {
        	
        	if(m.Hp <= 0 && !m.deadTriggersDone)
        	{
        		for(Minion mnn : allmins)
        		{
        			mnn.card.cardSim.onMinionDiedTrigger(this, mnn, m);//only onMinionDiedTriggers of other minions or illthornseed would spawn instantly in fight
        		}
        	}
        }
        
        
        return overdmg;
	}
	
	public void doDeathRattles()
	{
		int died=0;
        this.graveWhiteChanged=false;
        this.graveBlackChanged=false;
        ArrayList<Minion> allmins = this.getAllMinionOfField();
        for(Minion m : allmins)
        {
        	
        	if(m.Hp <= 0 && !m.deadTriggersDone)
        	{
        		
        		this.addMinionToGrave(m);
        		m.deadTriggersDone=true;//only to be save :D
        		
        		m.card.cardSim.onDeathrattle(this, m);
        		m.card.cardSim.onMinionLeavesBattleField(this, m);
        		
        		died++;
        	}
        }
        
        doOnFieldChangedTriggers();
        
        if(died>=1)
        {
        	if(graveWhiteChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(Color.white));
        	if(graveBlackChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(Color.black));
        }
        
        
	}
	
	private Boolean graveWhiteChanged=false;
	private Boolean graveBlackChanged=false;
	
	public void addMinionToGrave(Minion m)
	{
		//remove from field (have to do it first)
		if(m.position.row >=0 && m.position.column >=0)
		{
			this.getPlayerField(m.position.color)[m.position.row][m.position.column]=null;
		}
		
		if(m.cardType == Kind.ENCHANTMENT)//special deathrattle for enchantments!
		{
			m.card.cardSim.onDeathrattle(this, m);
		}
		
		for(Minion mnn : m.attachedCards)
		{
			//say enchantment that owner died
			mnn.card.cardSim.onMinionDiedTrigger(this, mnn, m);
			mnn.attachedCards.clear();
			if(mnn.cardID>=0)
			{
				this.getPlayerGrave(mnn.position.color).add(mnn);
				if(mnn.position.color == Color.white) 
				{
					graveWhiteChanged=true;
				}
				else
				{
					graveBlackChanged=true;
				}
			}
		}
		m.attachedCards.clear();
		this.getPlayerGrave(m.position.color).add(m);
		m.deadTriggersDone=true;//only to be save :D
		
		//remove from field
		//this.getPlayerField(m.color)[m.row][m.column]=null;
		
		if(m.position.color == Color.white) 
		{
			graveWhiteChanged=true;
		}
		else
		{
			graveBlackChanged=true;
		}
		
		//TODO perform on fieldchanged trigger?
		
	}
	
	private int performDmg(ArrayList<Minion> targets, Minion attacker, AttackType attackType, DamageType damageType, int odmg)
	{
		int overAttack2 = 0;
		int dmg = odmg;
		for(Minion target : targets)
		{
			if(odmg == -100) //aoe dmg :D
			{
				dmg = target.aoeDmgToDo;
				target.aoeDmgToDo=0;
			}
			
			int overAttack = 0;
			int oldHP = target.Hp;
			
			int newHPDefender = oldHP;
			if(damageType == DamageType.COMBAT || damageType == DamageType.PHYSICAL)
			{
					int rdmg = target.curse + dmg;
					if(rdmg >=1 && target.imuneToNextDmg) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
					if(rdmg >=2 && target.hasPotionOfResistance(this)) rdmg = 1;
					newHPDefender = Math.min(target.Hp , target.Hp + target.armor - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.MAGICAL)
			{
					int rdmg = dmg;
					if(rdmg >=1 && target.imuneToNextDmg) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
					if(rdmg >=2 && target.hasPotionOfResistance(this)) rdmg = 1;
					newHPDefender = Math.min(target.Hp , target.Hp + target.magicRessi - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.POISON)
			{
					int rdmg = target.curse + dmg;
					if(rdmg >=1 && target.imuneToNextDmg) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
					if(rdmg >=2 && target.hasPotionOfResistance(this)) rdmg = 1;
					newHPDefender = Math.min(target.Hp , target.Hp - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.SUPERIOR)
			{
					int rdmg = dmg;
					//TODO is pure dmg nilled by plating?
					//if(target.hasPotionOfResistance()) rdmg = 1;//TODO is pure-dmg affected by potionOfResistance-effects?
					newHPDefender = Math.min(target.Hp , target.Hp - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.TERMINAL)
			{
					newHPDefender = 0; //is killed
			}
			
			if(newHPDefender<0) overAttack = Math.min(dmg, -newHPDefender);
			
			int dmgdone = target.Hp - Math.max(newHPDefender,0);
			int realdmgDone = target.Hp - newHPDefender;//used in terminate msg
			
			boolean iskill=false;
			if(newHPDefender <=0 && oldHP >=1)
			{
				iskill=true;
			}
			
			overAttack2=overAttack;
			
			target.Hp = newHPDefender;
			
			this.getDmgUnitMessage(attacker, target, dmgdone, realdmgDone, attackType, damageType, iskill);
			
			this.dmgregister.add(new DmgRegisterUnit(attacker, target, attackType, dmgdone));
			
		}
		
		
		for(Minion target : targets)
		{
			if(target.isIdol || target.Hp>=1)
			{
				String s="";
				s= this.getStatusUpdateMessage(target);
				this.addMessageToBothPlayers(s);
			}
		}
		
		
		//s= this.getStatusUpdateMessage(defender);
		//this.addMessageToBothPlayers(s);
		
		
		return overAttack2;
		
	}
	
	private int performDmg(Minion target, Minion attacker, AttackType attackType, DamageType damageType, int dmg)
	{
		ArrayList<Minion> alm = new ArrayList<Minion>();
		alm.add(target);
		return performDmg(alm,  attacker,  attackType,  damageType,  dmg);
		
	}
	
	
	private void performDmgTriggers(Minion attacker, Minion deffender, AttackType attackType, int dmgdone)
    {

        int attackAP = attacker.Ap;
        int attackHP = attacker.Hp;
        int defferHP = deffender.Hp;
        int attackerAc = attacker.getAc();

        //register that the targets got dmg
        if(dmgdone>=1)deffender.numberOfDmgTaken++;
        
        
        if (attackType == AttackType.MELEE)
        {
        	//perform spiky-dmg from all sources (minion himself, enchantments + linger spells)
        	
        	int spikydmg = deffender.card.cardSim.getSpikyDamage(this, deffender);
        	if(spikydmg>=1)
        	{
        		performDmg(attacker, deffender, AttackType.MELEE_COUNTER , DamageType.COMBAT, spikydmg);
        	}
        	
        	for (Minion ench : deffender.attachedCards)
        	{
            
        		spikydmg = ench.card.cardSim.getSpikyDamage(this, ench);
            	if(spikydmg>=1)
            	{
            		performDmg(attacker, deffender, AttackType.MELEE_COUNTER , DamageType.COMBAT, spikydmg);
            	}
            }
        	
        	
        	ArrayList<Minion> lingerspells = this.getPlayerRules(deffender.position.color);
        	
        	for (Minion ench : lingerspells)
        	{
            
        		spikydmg = ench.card.cardSim.getSpikyDamage(this, ench);
            	if(spikydmg>=1)
            	{
            		performDmg(attacker, deffender, AttackType.MELEE_COUNTER , DamageType.COMBAT, spikydmg);
            	}
            }

        
        }
        


        //poison attacker if defender is poisonous
        //When creature Attacks trigger:
        if (attackHP >= 1 && (attackType == AttackType.MELEE || attackType == AttackType.MELEE_COUNTER))
        {
        	
        	if(deffender.card.cardSim.isPoisonous(this, deffender))
        	{
        		attacker.addnewPoison(this, deffender.position.color);
        	}
        	
        	for (Minion ench : deffender.attachedCards)
        	{
            
        		if(ench.card.cardSim.isPoisonous(this, deffender))
            	{
            		attacker.addnewPoison(this, deffender.position.color);
            	}
            }
        	
        }


        /*
         //not needed? (we also dont know the source of the dmg
        //perform unit got dmg triggers for attacker
        if (attackHP >= 1 && attacker.numberOfDmgTaken >= 1)
        {
            for (Minion ench : attacker.attachedCards)
            {
                
            	ench.card.cardSim.onMinionGotDmgTrigger(this, attacker, deffender);

            }
        }
        attacker.numberOfDmgTaken=0;
         */
        
        //perform unit got dmg triggers for deffender
        if (defferHP >= 1)
        {
        	//enchantments could be deleted after triggering
        	ArrayList<Minion> temp = new ArrayList<Minion> (deffender.attachedCards);
            for (Minion ench : temp)
            {
            	ench.card.cardSim.onMinionGotDmgTrigger(this, deffender, attacker, dmgdone);
            }
            
        }
        deffender.numberOfDmgTaken=0;

        //perform unit did attacking triggers (poisonous, dont know if other cards have the same trigger

        if (defferHP >= 1 && (attackType == AttackType.MELEE || attackType == AttackType.MELEE_COUNTER))
        {
        	
        	if(attacker.card.cardSim.isPoisonous(this, deffender))
        	{
        		deffender.addnewPoison(this, attacker.position.color);
        	}
        	
        	for (Minion ench : attacker.attachedCards)
        	{
            
        		if(ench.card.cardSim.isPoisonous(this, deffender))
            	{
        			deffender.addnewPoison(this, attacker.position.color);
            	}
            }
        	
        }
        
        

        //do onattck unit deals dmg
        if (attackHP >= 1)
        {
        	//enchantments could be deleted after triggering
        	ArrayList<Minion> temp = new ArrayList<Minion> (attacker.attachedCards);
        	for (Minion ench :temp)
        	{
        		ench.card.cardSim.onMinionDidDmgTrigger(this, attacker, deffender);
            }
        }
        
        
        


    }

	
	private String getUnitAttackMessage(Minion m, ArrayList<Minion> targetMins)
	{
		//{"UnitAttackIdol":{"attacker":{"color":"white","position":"1,1"},"idol":1}},
		//OR
		//{"UnitAttackTile":{"source":{"color":"white","position":"1,1"},"target":{"color":"black","position":"1,2"}}},
		//if there are more than 1 target in targets, use a unitattackIdol-msg, if a unit is among the targets
		String source=this.getSource(m);
		String targets = "";
		
		Minion tar = targetMins.get(0);
		int tarCol = tar.position.column;
		if(tar.isIdol) tarCol = 4;
		for(Minion mm : targetMins)
		{
			int tarColt = mm.position.column;
			if(mm.isIdol) tarColt = 4;
			
			if(tarColt > tarCol)
			{
				tar = mm;
				tarCol = tarColt;
			}
		}
		
		String ret = "";
		
		if(tar.isIdol)
		{
			//{"UnitAttackIdol":{"attacker":{"color":"white","position":"1,1"},"idol":1}},
			ret = "{\"UnitAttackIdol\":{\"attacker\":"+m.position.posToString()+",\"idol\":" + tar.position.row + "}}";
		}
		else
		{
			//{"UnitAttackTile":{"source":{"color":"white","position":"1,1"},"target":{"color":"black","position":"1,2"}}},
			ret ="{\"UnitAttackTile\":{" + source + ",\"target\":"+tar.position.posToString()+"}}"; 
		}
		
		return ret;
	}
	
	
	private void getDmgUnitMessage(Minion attacker, Minion defender, int dmgdone, int realdmgdone, AttackType att, DamageType dmgt, boolean kill)
	{
		String s = "";
		if(defender.isIdol)
		{
			//{"DamageIdol":{"attackType":"MELEE","idol":{"color":"black","position":0,"hp":8,"maxHp":10},"amount":2}},
			//{"IdolUpdate":{"idol":{"color":"black","position":0,"hp":8,"maxHp":10}}},
			s="{\"DamageIdol\":{\"attackType\":\"" + Board.attackTypeToString(att) + "\"," + this.getIdolInfoString(defender) + ",\"amount\":"+dmgdone+"}}";
			this.addMessageToBothPlayers(s);
			//s= this.getStatusUpdateIdolMessage(defender);
		}
		else
		{
			//{"DamageUnit":{"targetTile":{"color":"black","position":"2,1"},"amount":7,"hp":49,"kill":false,"attackType":"MELEE","damageType":"COMBAT","sourceCard":{"id":-1,"typeId":38,"tradable":true,"isToken":true,"level":0}}},
			//{"StatsUpdate":{"target":{"color":"black","position":"2,1"},"hp":49,"ap":0,"ac":2,"buffs":[]}},

			//or
			
			//these 3 are allways together, also on AOE'S
			//{"DamageUnit":{"targetTile":{"color":"black","position":"2,0"},"amount":6,"hp":-1,"kill":true,"attackType":"MELEE","damageType":"COMBAT","sourceCard":{"id":-1,"typeId":38,"tradable":true,"isToken":true,"level":0}}},
			//{"RemoveUnit":{"tile":{"color":"black","position":"2,0"},"removalType":"DESTROY"}},
			//{"TerminateUnit":{"targetTile":{"color":"black","position":"2,0"},"amount":7,"hp":-1,"attackType":"MELEE","damageType":"COMBAT","sourceCard":{"id":-1,"typeId":38,"tradable":true,"isToken":true,"level":0}}},
			
			s= "{\"DamageUnit\":{\"targetTile\":"+defender.position.posToString()+",\"amount\":" + dmgdone;
			s+= ",\"hp\":" + defender.Hp + ",\"kill\":"+ Boolean.toString(kill) +",\"attackType\":\""+Board.attackTypeToString(att)+"\",\"damageType\":\""+Board.damageTypeToString(dmgt)+"\",\"sourceCard\":{\"id\":" + attacker.cardID ;
			s+= ",\"typeId\":38,\"tradable\":true,\"isToken\":"+Boolean.toString(attacker.isToken)+",\"level\":"+attacker.lvl+"}}}";
			this.addMessageToBothPlayers(s);
			
			if(kill)
			{
				s="{\"RemoveUnit\":{\"tile\":"+defender.position.posToString()+",\"removalType\":\"DESTROY\"}}"; //removalType destroy, because it was dmg
				this.addMessageToBothPlayers(s);
				
				s= "{\"TerminateUnit\":{\"targetTile\":"+defender.position.posToString()+",\"amount\":" + realdmgdone; //this time realdmg
				s+= ",\"hp\":" + defender.Hp + ",\"attackType\":\""+Board.attackTypeToString(att)+"\",\"damageType\":\""+Board.damageTypeToString(dmgt)+"\",\"sourceCard\":{\"id\":" + attacker.cardID ;
				s+= ",\"typeId\":38,\"tradable\":true,\"isToken\":"+Boolean.toString(attacker.isToken)+",\"level\":"+attacker.lvl+"}}}";
				this.addMessageToBothPlayers(s);
				
			}
			else
			{
				//TODO do this somewhere other
				//s= this.getStatusUpdateMessage(defender);
				//this.addMessageToBothPlayers(s);
			}
			
		}
		
		return;
	}
	
	
	public String getMinionHealMessage(Minion m, int amount)
	{
		String s = "";
		Boolean heales = false;
		if(amount>=1) heales = true;
		if(m.isIdol)
		{
			//{"HealIdol":{"idol":{"color":"white","position":2,"hp":10,"maxHp":10},"amount":1}}
			s="{\"HealIdol\":{\"idol\":{\"color\":\""+Board.colorToString(m.position.color)+"\",\"position\":"+m.position.row+",\"hp\":"+m.Hp+",\"maxHp\":"+m.maxHP+"},\"amount\":"+amount+"}}";
		}
		else
		{
		//{"HealUnit":{"target":{"color":"white","position":"1,1"},"amount":2,"hp":5,"sourceCard":{"id":19839,"typeId":13,"tradable":false,"isToken":false,"level":0},"healed":true}}	
		s= "{\"HealUnit\":{\"target\":"+m.position.posToString()+",\"amount\":" + amount + ",\"hp\":" + m.Hp+",";
		s+="\"sourceCard\":{\"id\":"+m.cardID+",\"typeId\":"+m.typeId+",\"tradable\":false,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"},\"healed\":"+Boolean.toString(heales)+"}}";
		}
		
		return s;
	}
	
	public void destroyMinion(Minion m, Minion source)
	{
		int dmg = m.Hp;
		AttackType att = AttackType.UNDEFINED;
		DamageType dmgt = DamageType.TERMINAL;
		this.doDmg(m, source, dmg, att, dmgt);
	}
	
}

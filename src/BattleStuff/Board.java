package BattleStuff;

import java.awt.Color;
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
	
	public GameState gameState = GameState.Init; // 0=init, 1= we have to do end-premain, we are in battle, 2= we have to do end main, we are in premain
	public int turnNumber =0;
	public UColor activePlayerColor = UColor.white;
	
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
	
	private ArrayList<Minion> whiteRulesUpdates = new ArrayList<Minion>();//linger stuff
	private ArrayList<Minion> blackRulesUpdates = new ArrayList<Minion>();//linger stuff
	
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
	
	public ArrayList<SiftItem> whiteShiftCards = new ArrayList<SiftItem>();
	public ArrayList<SiftItem> blackShiftCards = new ArrayList<SiftItem>();
	
	//change whiteRessources only with function (changeMaxRessis)!
	private int[] whiteRessources = {0,0,0,0,0}; //GROWTH, ORDER, ENERGy, DECAY, Special
	private int[] blackRessources = {0,0,0,0,0}; 
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
	
	public int whiteQuakeTaxing=0;
	public int blackQuakeTaxing=0;
	public int whiteDamningTaxing=0;
	public int blackDamningTaxing=0;
	
	private ArrayList<String> messagesToWhite= new ArrayList<String>();
	private ArrayList<String> messagesToBlack= new ArrayList<String>();
	
	public int drawCorrodePossible = 0;
	
	public boolean doAdvantageousOutlookEffect = false;
	
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
		//Fisher�Yates shuffle
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
	
	public void addMessageToPlayer(UColor col, String msg)
	{
		if(col == UColor.white)
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
			s= makeBigEffectMessage(UColor.white);
			this.whitePlayer.sendMessgeToBattleServer(s);
		}
		if(this.messagesToBlack.size()>=1)
		{
			s= makeBigEffectMessage(UColor.black);
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
	
	public void changeCurrentRessource(ResourceName ressi, UColor color, int amount)
	{
		if(color == UColor.white)
		{
			if(ressi == ResourceName.GROWTH)
			{
				this.whitecurrentRessources[0]+= amount;
			}
			if(ressi == ResourceName.ORDER)
			{
				this.whitecurrentRessources[1]+= amount;
			}
			if(ressi == ResourceName.ENERGY)
			{
				this.whitecurrentRessources[2]+= amount;
			}
			if(ressi == ResourceName.DECAY)
			{
				this.whitecurrentRessources[3]+= amount;
			}
			if(ressi == ResourceName.WILD)
			{
				this.whitecurrentRessources[4]+= amount;
			}
		}
		else
		{
			if(ressi == ResourceName.GROWTH)
			{
				this.blackcurrentRessources[0]+= amount;
			}
			if(ressi == ResourceName.ORDER)
			{
				this.blackcurrentRessources[1]+= amount;
			}
			if(ressi == ResourceName.ENERGY)
			{
				this.blackcurrentRessources[2]+= amount;
			}
			if(ressi == ResourceName.DECAY)
			{
				this.blackcurrentRessources[3]+= amount;
			}
			if(ressi == ResourceName.WILD)
			{
				this.blackcurrentRessources[4]+= amount;
			}
		}
		
		this.addMessageToBothPlayers(this.getResourcesUpdateMessage());
	}
	
	public void changeMaxRessource(ResourceName ressi, UColor color, int amount)
	{
		if(color == UColor.white)
		{
			if(ressi == ResourceName.GROWTH)
			{
				this.whiteRessources[0]+= amount;
			}
			if(ressi == ResourceName.ORDER)
			{
				this.whiteRessources[1]+= amount;
			}
			if(ressi == ResourceName.ENERGY)
			{
				this.whiteRessources[2]+= amount;
			}
			if(ressi == ResourceName.DECAY)
			{
				this.whiteRessources[3]+= amount;
			}
			if(ressi == ResourceName.WILD)
			{
				this.whiteRessources[4]+= amount;
			}
		}
		else
		{
			if(ressi == ResourceName.GROWTH)
			{
				this.blackRessources[0]+= amount;
			}
			if(ressi == ResourceName.ORDER)
			{
				this.blackRessources[1]+= amount;
			}
			if(ressi == ResourceName.ENERGY)
			{
				this.blackRessources[2]+= amount;
			}
			if(ressi == ResourceName.DECAY)
			{
				this.blackRessources[3]+= amount;
			}
			if(ressi == ResourceName.WILD)
			{
				this.blackRessources[4]+= amount;
			}
		}
		this.addMessageToBothPlayers(this.getResourcesUpdateMessage());
		for(Minion m : this.getPlayerFieldList(color))
		{
			for(Minion ench : m.getAttachedCards())
			{
				if(ench.typeId==369) ench.card.cardSim.onEnergyChanged(this, ench, color, ressi);
			}
		}
	}
	
	public int getCurrentRessource(ResourceName ressi, UColor color)
	{
		if(color == UColor.white)
		{
			if(ressi == ResourceName.GROWTH)
			{
				return this.whitecurrentRessources[0];
			}
			if(ressi == ResourceName.ORDER)
			{
				return this.whitecurrentRessources[1];
			}
			if(ressi == ResourceName.ENERGY)
			{
				return this.whitecurrentRessources[2];
			}
			if(ressi == ResourceName.DECAY)
			{
				return this.whitecurrentRessources[3];
			}
			if(ressi == ResourceName.WILD)
			{
				return this.whitecurrentRessources[4];
			}
		}
		else
		{
			if(ressi == ResourceName.GROWTH)
			{
				return this.blackcurrentRessources[0];
			}
			if(ressi == ResourceName.ORDER)
			{
				return this.blackcurrentRessources[1];
			}
			if(ressi == ResourceName.ENERGY)
			{
				return this.blackcurrentRessources[2];
			}
			if(ressi == ResourceName.DECAY)
			{
				return this.blackcurrentRessources[3];
			}
			if(ressi == ResourceName.WILD)
			{
				return this.blackcurrentRessources[4];
			}
		}
		
		return 0;
	}
	
	public int getMaxRessource(ResourceName ressi, UColor color)
	{
		if(color == UColor.white)
		{
			if(ressi == ResourceName.GROWTH)
			{
				return this.whiteRessources[0];
			}
			if(ressi == ResourceName.ORDER)
			{
				return this.whiteRessources[1];
			}
			if(ressi == ResourceName.ENERGY)
			{
				return this.whiteRessources[2];
			}
			if(ressi == ResourceName.DECAY)
			{
				return this.whiteRessources[3];
			}
			if(ressi == ResourceName.WILD)
			{
				return this.whiteRessources[4];
			}
		}
		else
		{
			if(ressi == ResourceName.GROWTH)
			{
				return this.blackRessources[0];
			}
			if(ressi == ResourceName.ORDER)
			{
				return this.blackRessources[1];
			}
			if(ressi == ResourceName.ENERGY)
			{
				return this.blackRessources[2];
			}
			if(ressi == ResourceName.DECAY)
			{
				return this.blackRessources[3];
			}
			if(ressi == ResourceName.WILD)
			{
				return this.blackRessources[4];
			}
		}
		
		return 0;
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
		
		this.whitePlayer.color = UColor.white;
		this.blackPlayer.color = UColor.black;
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
			this.whiteIdols.add(new Minion(10, UColor.white, i));
			this.blackIdols.add(new Minion(10, UColor.black, i));
		}
		
		this.serverip = InterThreadStuff.getInstance().usedServerIp;
		
		//this.loadWhiteCards();
	}
	
	public Minion[][] getPlayerField(UColor col)
	{
		if(col == UColor.white) return this.whiteField;
		return this.blackField;
	}
	
	public ArrayList<Minion> getPlayerFieldList(UColor col)
	{
		Minion[][] mins =  getPlayerField(col);
		ArrayList<Minion> alist = new ArrayList<Minion>();
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = mins[i][j];
				if(m != null && !m.deadTriggersDone && m.Hp>=1)
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
				if(m != null && !m.deadTriggersDone&& m.Hp>=1)
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
				if(m != null && !m.deadTriggersDone&& m.Hp>=1)
				{
					alist.add(m);
				}
				
			}
		}
		
		return alist;
	}
	
	public ArrayList<Minion> getAllDeadMinionsOfField()
	{
		Minion[][] mins =  getPlayerField(this.activePlayerColor);
		ArrayList<Minion> alist = new ArrayList<Minion>();
		
		for(int j=0; j<3; j++)
		{
			for(int i=0; i<5; i++)
			{
			
				Minion m = mins[i][j];
				if(m != null && !m.deadTriggersDone && m.Hp<=0)
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
				if(m != null && !m.deadTriggersDone && m.Hp<=0)
				{
					alist.add(m);
				}
				
			}
		}
		
		return alist;
	}
	
	public ArrayList<Minion> getAllRules()
	{
		
		return getAllRulesWithColorFirst(this.activePlayerColor);
	}
	
	public ArrayList<Minion> getAllRulesWithColorFirst(UColor first)
	{
		ArrayList<Minion> alist = new ArrayList<Minion>();
		UColor c1 = first;
		UColor c2 = Board.getOpposingColor(c1);
		
		alist.addAll(this.getPlayerRules(c1));
		alist.addAll(this.getPlayerRules(c2));
		return alist;
	}
	
	public Player getPlayer(UColor col)
	{
		if(col == UColor.white) return this.whitePlayer;
		return this.blackPlayer;
	}
	
	public ArrayList<Minion> getPlayerHand(UColor col)
	{
		if(col == UColor.white) return this.whiteHand;
		return this.blackHand;
	}
	
	public ArrayList<Minion> getPlayerDeck(UColor col)
	{
		if(col == UColor.white) return this.whiteDeck;
		return this.blackDeck;
	}
	
	public ArrayList<Minion> getPlayerGrave(UColor col)
	{
		if(col == UColor.white) return this.whiteGrave;
		return this.blackGrave;
	}
	
	public ArrayList<Minion> getPlayerRules(UColor col)
	{
		ArrayList<Minion>  rules = new ArrayList<Minion>();
		if(col == UColor.white) 
		{
			for(Minion r : this.whiteRulesUpdates)
			{
				if(r.lingerDuration>=1) rules.add(r);
			}
			return rules;
		}
			
		for(Minion r : this.blackRulesUpdates)
		{
			if(r.lingerDuration>=1) rules.add(r);
		}
		return rules;
	}
	
	public ArrayList<Minion> getPlayerIdols(UColor col)
	{
		if(col == UColor.white) return this.whiteIdols;
		return this.blackIdols;
	}
	
	public Minion getPlayerIdol(UColor col, int index)
	{
		ArrayList<Minion> idols = this.whiteIdols;
		if(col == UColor.black) idols = this.blackIdols;
		for(Minion m:idols)
		{
			if(m.position.row == index) return m;
		}
		
		return idols.get(0);
	}
	
	
	public void loadWhiteCards(ArrayList<Minion> cards)
	{
		for(Minion c : cards)
		{
			//System.out.println("added to white: "+ c.typeId + " " + c.cardID);
			c.position.color = UColor.white;
			this.whiteDeck.add(c);
		}
	}
	
	public void loadBlackCards(ArrayList<Minion> cards)
	{
		for(Minion c : cards)
		{
			//System.out.println("added to black: "+ c.typeId + " " + c.cardID);
			c.position.color = UColor.black;
			this.blackDeck.add(c);
		}
	}
	
	public void drawCards(UColor col, int howMany)
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
	
	public void drawSpecialCard(UColor col, Kind cardType)
	{
		ArrayList<Minion> playerdeck = this.getPlayerDeck(col);
		ArrayList<Minion> hand = this.getPlayerHand(col);
		ArrayList<Minion> deck = new ArrayList<Minion>(playerdeck);
		Boolean found = false;
		for(int i=0; i<deck.size(); i++)
		{
			Minion m= deck.get(i);
			if(m.card.cardKind == cardType)
			{
				found=true;
				hand.add(m);
				playerdeck.remove(m);
				this.shuffleList(playerdeck);
				break;
			}
		}
		
		//look into graveyard
		if(!found)
		{
			playerdeck = this.getPlayerGrave(col);
			deck.clear();
			deck.addAll(playerdeck);
			
			for(int i=0; i<deck.size(); i++)
			{
				Minion m= deck.get(i);
				if(m.card.cardKind == cardType)
				{
					found=true;
					hand.add(m);
					playerdeck.remove(m);
					this.shuffleList(playerdeck);
					break;
				}
			}
		}
		
		if(found)
		{
			//hand+ cardstack update
			this.addMessageToPlayer(col, this.getHandUpdateMessage(col));
			this.addMessageToBothPlayers(this.getCardStackUpdate(col));
		}
		
	}
	
	
	public int getRandomNumber(int min, int max)
	{
		//random number in [min, max]
		int randomNum = randomNumberGenerator.nextInt((max - min) + 1) + min;
		
		return randomNum;
	}
	
	
	public void initGame()
	{
		this.activePlayerColor= UColor.white;
		drawCards(UColor.white, 4);
		drawCards(UColor.black, 5);
		this.messagesToWhite.clear();
		this.messagesToBlack.clear();
	}
	
	public String getPlayerColor(String playername)
	{
		if(playername == this.whitePlayer.name) return "white";
		return "black"; 
	}
	
	public String  getIdolsString(UColor col)
	{
		//creates [{"color":"white","position":0,"hp":10,"maxHp":10},{"color":"white","position":1,"hp":10,"maxHp":10},{"color":"white","position":2,"hp":10,"maxHp":10},{"color":"white","position":3,"hp":10,"maxHp":10},{"color":"white","position":4,"hp":10,"maxHp":10}]
		
		//int[] idolHP = this.blackIdolsHP;
		//int[] idolMaxHP = this.blackIdolsMaxHP;
		
		String array = "[";
		String color = "black";
		ArrayList<Minion> idols = this.blackIdols;
		if(col == UColor.white) 
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
	
	public String getTilesJson(UColor col)
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
					for(Minion mm : m.getAttachedCards())
					{
						if(buffs.equals(""))
						{
							//BUFF
							buffs = "{\"name\":\"" + mm.card.cardname +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+m.bufftype.toString()+"\"}";
						}
						else
						{
							buffs += ",{\"name\":\"" + mm.card.cardname +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+m.bufftype.toString()+"\"}";
						}
					}
					
					if(!buffs.equals(""))
					{
						buffs= ",\"buffs\":[" + buffs + "]";
					}
					
					if(s.equals(""))
					{
						s = "{\"card\":{\"id\":"+m.cardID+",\"typeId\":" + m.typeId + ",\"tradable\":true,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"},\"ap\":" + m.getAttack(this) + ",\"ac\":"+m.getAc()+",\"hp\":"+m.Hp+",\"position\":\""+ m.position.row +","+ m.position.column +"\"" + buffs + "}";
						
					}
					else
					{
						s += ",{\"card\":{\"id\":"+m.cardID+",\"typeId\":" + m.typeId + ",\"tradable\":true,\"isToken\":"+Boolean.toString(m.isToken)+",\"level\":"+m.lvl+"},\"ap\":" + m.getAttack(this) + ",\"ac\":"+m.getAc()+",\"hp\":"+m.Hp+",\"position\":\""+ m.position.row +","+ m.position.column +"\"" + buffs + "}";
						
					}
				}
			}
		}
		
		return s;
	}
	
	public String assetMessageBuilder(UColor col)
	{
		int[] curr = this.blackcurrentRessources;
		int[] maxr= this.blackRessources;
		int handsize = this.blackHand.size();
		int libsize = this.blackDeck.size();
		int gravesize = this.blackGrave.size();
		ArrayList<Minion> rules = this.blackRulesUpdates;
		if(col == UColor.white)
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
		
		String whiteTiles = getTilesJson(UColor.white);
		String blackTiles = getTilesJson(UColor.black);
		String whiteIdolss=""+this.whiteIdols.get(0).Hp+","+this.whiteIdols.get(1).Hp+","+this.whiteIdols.get(2).Hp+","+this.whiteIdols.get(3).Hp+","+this.whiteIdols.get(4).Hp;
		String blackIdolss=""+this.blackIdols.get(0).Hp+","+this.blackIdols.get(1).Hp+","+this.blackIdols.get(2).Hp+","+this.blackIdols.get(3).Hp+","+this.blackIdols.get(4).Hp;
		String whiteAssets = this.assetMessageBuilder(UColor.white);
		String blackAssets = this.assetMessageBuilder(UColor.black);
		
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
		if(this.gameState == GameState.PreMain) phse = "PreMain";
		if(this.gameState == GameState.Main) phse = "Main";
		if(this.gameState == GameState.Battle) phse = "End";
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
		
		js+="\"whiteIdols\":" + this.getIdolsString(UColor.white)+ ",";
		js+="\"blackIdols\":" + this.getIdolsString(UColor.black)+ ",";
		
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
		
		if(this.gameState == GameState.End) return; //game has ended
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
	
	private String makeBigEffectMessage(UColor col)
	{
		ArrayList<String> msgs = this.messagesToBlack;
		if(col==UColor.white)msgs = this.messagesToWhite;
		String msg="";
		for(String m : msgs)
		{
			if(!msg.equals("")) msg+=",";
			msg+=m;
		}
		msgs.clear();
		return "{\"effects\":[" + msg + "],\"msg\":\"NewEffects\"}";
	}
	
	
	public String getHandUpdateMessage(UColor col)
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
	
	public String getActiveRessourcesMessage(UColor col)
	{
		Player p = this.getPlayer(col);
		String s = "{\"types\":["+p.activeRessis+"],\"msg\":\"ActiveResources\"}";
		return s;
	}
	
	public String getTurnMessage(UColor col)
	{
		String colo = Board.colorToString(this.activePlayerColor);
		String s ="{\"TurnBegin\":{\"color\":\""+ colo +"\",\"turn\":"+this.turnNumber+",\"secondsLeft\":"+maxRoundTimerSeconds+"}}";
		return s;
	}
	
	private void allMinionsOfASideCountDown(UColor col, int ammount)
	{
		Minion[][] ms = this.getPlayerField(col);
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = ms[i][j];
				if(m != null)
				{
					
					if(m.getAc() >=1 )
					{
						int countdown = m.card.cardSim.doesCountDown(this, m);
						if(countdown!=0)
						{
							m.buffMinionWithoutMessage(0, 0, countdown, this);
							String s = getStatusUpdateMessage(m);
							this.addMessageToBothPlayers(s);
						}
					}
				}
			}
		}
				
	}
	
	
	
	
	private void allMinionsOfASideResetCountDown(UColor col)
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
							
							m.resetAcWithMessage(this);
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
		
		
		
		String s="{\"StatsUpdate\":{\"target\":"+m.position.posToString()+",\"hp\":"+ m.Hp +",\"ap\":"+ m.getAttack(this) +",\"ac\":"+ m.getAc() +",\"buffs\":[";
		String buffs="";
		for(Minion mm : m.getAttachedCards())
		{
			if(buffs.equals(""))
			{
				//BUFF
				buffs = "{\"name\":\"" + mm.buffName +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+mm.bufftype.toString()+"\"}";
			}
			else
			{
				buffs += ",{\"name\":\"" + mm.buffName +"\",\"description\":\"" + mm.buffDescription + "\",\"type\":\""+mm.bufftype.toString()+"\"}";
			}
		}
		
		s+=buffs + "]}}";
		return s;
	}
	
	//Rules are cound down
	public boolean addRule(Minion rule)
	{
		//if new rule:
		//{"RuleAdded":{"card":{"id":25869,"typeId":261,"tradable":false,"isToken":false,"level":0},"color":"white","roundsLeft":5}}
		// + {"RuleUpdate":{"card":{"id":25869,"typeId":261,"tradable":false,"isToken":false,"level":0},"color":"white","roundsLeft":5}}
		
		//if rule existed : only ruleupdate
			ArrayList<Minion> rules = this.getPlayerRules(rule.position.color);
			boolean isNewOne = true;
			Minion ruleupdated = rule;
			for(Minion rul : rules)
			{
				if(rul.typeId == rule.typeId && rul.lingerDuration>=1) 
				{
					rul.lingerDuration+=rule.card.cardSim.getLingerDuration(this, rule);
					ruleupdated = rul;
					isNewOne=false;
				}
			}
			
			String s="";
			if(isNewOne)
			{
				ruleupdated.lingerDuration = rule.card.cardSim.getLingerDuration(this, rule);
				s="{\"RuleAdded\":{\"card\":{\"id\":"+ruleupdated.cardID+",\"typeId\":"+ruleupdated.typeId+",\"tradable\":false,\"isToken\":false,\"level\":"+ruleupdated.lvl+"},\"color\":\""+ Board.colorToString(ruleupdated.position.color)+"\",\"roundsLeft\":"+ruleupdated.lingerDuration+"}}";
				this.addMessageToBothPlayers(s);
				rules.add(ruleupdated);
				s = this.getRuleMessage(ruleupdated);
				this.addMessageToBothPlayers(s);
			}
			else
			{
				
				s = this.getRuleMessage(ruleupdated);
				this.addMessageToBothPlayers(s);
				this.addMinionToGrave(rule);
			}
			
			return isNewOne;
	}
	
	public void ruleCountDown(Minion rule, int value)
	{
		ArrayList<Minion> rules = this.getPlayerRules(rule.position.color);
		Minion removerules = null;
		ArrayList<Minion> grave = this.getPlayerGrave(rule.position.color);
		for(Minion rul : rules)
		{
			if(rul.typeId != rule.typeId) continue;
			
			if(rule.lingerDuration>=1) 
			{
				rule.lingerDuration-=value;
				String s = getRuleMessage(rule);
				this.addMessageToBothPlayers(s);
			}
			if(rule.lingerDuration<=0) removerules=rule;
			break;
		}
		
		
		
		if(removerules!=null)
		{
			this.addMinionToGrave(removerules);
			
			removerules.card.cardSim.onDeathrattle(this, removerules, removerules, AttackType.UNDEFINED, DamageType.TERMINAL);
			removerules.card.cardSim.onMinionLeavesBattleField(this, removerules);
			
			//remove rule from orginal list!
			ArrayList<Minion> orules = this.whiteRulesUpdates;
			if(rule.position.color == UColor.black) orules = this.blackRulesUpdates;
			orules.remove(removerules);
		}
		
	}
	
	//all Rules are cound down
	private void rulecountdowner(UColor col)
	{
		ArrayList<Minion> rules = this.getPlayerRules(col);
		for(Minion rule : rules)
		{
			ruleCountDown(rule, 1);
		}
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
	
	
	private void refreshRessoures(UColor col)
	{
		boolean sendupdate=false;
		if(col == UColor.white)
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
		String whiteAssets = this.assetMessageBuilder(UColor.white);
		String blackAssets = this.assetMessageBuilder(UColor.black);
		String s="{\"ResourcesUpdate\":{\"whiteAssets\":"+whiteAssets+",\"blackAssets\":"+blackAssets+"}}";
		return s;
	}
	
	public String getCardStackUpdate(UColor col)
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
		
		ArrayList<Minion> temp = new ArrayList<Minion>(m.getAttachedCards());
		m.card.cardSim.onUnitIsGoingToAttack(this, m, m);
		for(Minion e : temp)
		{
			//TODO other units on field have such a trigger?
			e.card.cardSim.onUnitIsGoingToAttack(this, e, m);
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
			unitAttacking(m, defffield, m.getAttack(this), m.attackType, DamageType.COMBAT);
		}
		//unit is ready
		
		temp = new ArrayList<Minion>(m.getAttachedCards());
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
		
		ArrayList<UPosition> posis = Board.getAttackPositions(m);
		
		UColor opcol = Board.getOpposingColor(m.position.color);
		ArrayList<Minion> idols = this.getPlayerIdols(opcol);
		ArrayList<Minion> targets = m.getTargets(defffield, posis, idols, this);

		if(m.card.cardSim.hasSpecialAttackTarget())
		{
			posis = m.card.cardSim.getSpecialAttackTarget(this, m);
			targets = this.getMinionsFromPositions(posis);
		}
		
		
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
				//minions like may only attack a tile (like that energy-strutcture)-> they have no minion to target
				if(targets.size()>=1)
				{
					posis.clear();
					for(Minion minpos : targets)
					{
						posis.add(minpos.position);
					}
					s = getUnitAttackMessage(m,posis);
				}
				else
				{
					s = getUnitAttackMessage(m,posis);
				}
				this.addMessageToBothPlayers(s);
			}
			
			for(Minion tt : targets )
			{
				System.out.println("targets: " + tt.position.posToString());
			}
	
			//unit attacks (and attacks again if relentless):

			relentlessAttackvalue = this.doDmg(targets, m, attackvalue, attt, dmgt);
			
			for(Minion def : targets)
			{
				if(def.isIdol)idol=true;
			}
			
			
			if(idol) relentlessAttackvalue=0;
			
			boolean relentless = m.isRelentless(this);
			
			
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
	
	private String getSiegeAttackMessage(Minion m, ArrayList<UPosition> posis)
	{
		//"{"SiegeAttackTiles":{"source":{"color":"white","position":"1,1"},"targets":[{"color":"black","position":"1,1"},{"color":"black","position":"1,0"},{"color":"black","position":"0,1"},{"color":"black","position":"2,1"}]}}",

		String source=this.getSource(m);
		String targets = "";
		
		for(UPosition pos : posis)
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
	
	
	private void allMinionsOfASideAttacks(UColor col)
	{
		UColor otherColor = Board.getOpposingColor(col);
		
		Minion[][] attackfield = this.getPlayerField(col);
		Minion[][] defffield = this.getPlayerField(otherColor);
		
		for(Minion m : this.getPlayerFieldList(col))
		{
			if(m != null && m.getAc() == 0 && m.Hp>=1)
			{
			
				//minion is ready to attack
				this.unitAttackStarting(m, defffield);
				
			}
		}
		
		/*
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
		}*/
		
		//doDeathRattles();
		
		
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
			for(Minion e : m.getAttachedCards())
			{
				e.card.cardSim.onTurnStartTrigger(this, e, this.activePlayerColor);
				
				if((e.typeId == 5 || e.typeId == 100) && this.activePlayerColor == m.position.color) //perform poinson dmg on own turn
				{
					this.doDmg(m , e, 1, AttackType.UNDEFINED, DamageType.POISON);
				}
				
			}
		}
		
		for(Minion rule : this.getAllRules())
		{
			rule.card.cardSim.onTurnStartTrigger(this, rule, this.activePlayerColor);
		}
		
		//this.doDeathRattles();
		
		
	}
	
	public String getSiftMessage(UColor col)
	{
		Player p = this.getPlayer(col);
		//{"SiftUpdate":{"profileId":68964,"cards":[{"id":23448,"typeId":343,"tradable":false,"isToken":false,"level":0},{"id":23442,"typeId":16,"tradable":false,"isToken":false,"level":0},{"id":23446,"typeId":343,"tradable":false,"isToken":false,"level":0}],"maxScrollsForCycle":7}}
		String s ="{\"SiftUpdate\":{\"profileId\":"+p.profileId+",\"cards\":[";
		ArrayList<SiftItem> siftcards = this.whiteShiftCards;
		if(col == UColor.black) siftcards = this.blackShiftCards;
		String cards = "";
		for(SiftItem si : siftcards)
		{
			if(!cards.equals("")) cards+=",";
			cards+="{\"id\":"+si.minion.cardID+",\"typeId\":"+si.minion.typeId+",\"tradable\":false,\"isToken\":"+Boolean.toString(si.minion.isToken)+",\"level\":"+si.minion.lvl+"}";
		}
		s+= cards + "],\"maxScrollsForCycle\":"+ this.maxScrollsForCycle+"}}";
		return s;
	}
	
	public void siftCard(long cardid, long profileid)
	{
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == profileid) p = this.whitePlayer;
		
		if(p.color != this.activePlayerColor) return; //TODO error not your turn
		
		ArrayList<SiftItem> siftcards = this.whiteShiftCards;
	 	if(p.color == UColor.black) siftcards = this.blackShiftCards;
		
	 	if(siftcards.size() == 0) return; //TODO you cant sift! CHEATER!
	 	
	 	SiftItem target = siftcards.get(0);
	 	
	 	for(int i = 0 ; i < siftcards.size(); i++ )
	 	{
	 		SiftItem si = siftcards.get(i);
	 		if(si.minion.cardID == cardid)
	 		{
	 			target = si;
	 			siftcards.remove(i);
	 			break;
	 		}
	 	}
	 	
	 	boolean addedToDeck = false;
	 	boolean addedToGrave = false;
	 	ArrayList<Minion> deck = this.getPlayerDeck(p.color);
	 	ArrayList<Minion> grave = this.getPlayerGrave(p.color);
		//re-add the sifted cards...
	 	for(int i = 0 ; i < siftcards.size(); i++ )
	 	{
	 		SiftItem si = siftcards.get(i);
	 		if(si.minion.cardID != cardid)
	 		{
	 			if(si.from == SiftPlace.LIBRARY)
	 			{
	 				addedToDeck=true;
	 				deck.add(si.minion);
	 			}
	 			
	 			if(si.from == SiftPlace.GRAVEYARD)
	 			{
	 				addedToDeck=true;
	 				grave.add(si.minion);
	 			}
	 		}
	 	}
	 	
	 	siftcards.clear();//remove all items from siftcards
	 	
	 	if(addedToDeck) this.shuffleList(deck);
	 	if(addedToDeck) this.shuffleList(grave);
	 	
		
		String s = "{\"SiftClose\":{}}";
		this.addMessageToBothPlayers(s);
		//resource update
		s= this.getResourcesUpdateMessage();
		this.addMessageToBothPlayers(s);
		
		//add the sifted target to hand...
		if(target.whereToAdd==SiftPlace.HAND)
		{
			this.getPlayerHand(p.color).add(target.minion);
		
			s=this.getHandUpdateMessage(p.color);
			this.addMessageToPlayer(p.color, s);
		
			s=this.getCardStackUpdate(p.color);
			this.addMessageToBothPlayers(s);
		}
		
		if(target.whereToAdd==SiftPlace.LIBRARY)
		{
			this.getPlayerDeck(p.color).add(0,target.minion);//ADD TO FIRST PLACE!

			s=this.getCardStackUpdate(p.color);
			this.addMessageToBothPlayers(s);
		}
		
		if(this.doAdvantageousOutlookEffect && target.minion.cardType == Kind.SPELL)
		{
			Card card = CardDB.getInstance().cardId2Card.get(163);
			for(Minion m:this.getPlayerFieldList(this.activePlayerColor))
			{
				if(m.getSubTypes().contains(SubType.Knight))
				{
					m.buffMinionWithoutMessage(2, 0, 0, this);
					m.addnewEnchantments("BUFF", "Advantageous Outlook", "Knights you control get +2 Attack until end of turn.", card, this, this.activePlayerColor);
				}
			}
			
			
		}
		this.doAdvantageousOutlookEffect=false;
		
		this.sendEffectMessagesToPlayers();
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
		
		for(Minion rule : this.getAllRules())
		{
			rule.card.cardSim.onTurnEndsTrigger(this, rule, this.activePlayerColor);
		}
		
		//this.doDeathRattles();
		
	}
	
	
	//is called from BattleThread (we got from player x {"phase" : "Init","msg" : "EndPhase"})
	public void doPhase (String phse, long playerid)
	{
		//TODO end phase (except of "Init") automatically after some time
		
		if(this.gameState == GameState.End) return; //game has ended
		
		//NOTE: gamestate is changed from 1 -> 2 and 2->1  AT THE START of the Phase-change, so, if we ask if we are in fight, then gamestate = 1 and not 2.
		//Gamestate = 0 init
		//gamestate = 2 main
		//gamestate = 1 battle
		
		//phase client want to end can only have 3 states
		GameState phase = GameState.Init;
		if(phse.equals("PreMain"))phase = GameState.PreMain;
		if(phse.equals("Main"))phase = GameState.Main;
		
		Player p = this.blackPlayer;
		if(this.whitePlayer.profileId == playerid) p = this.whitePlayer;
		System.out.println("endphase: "+ phase.toString() + " curretn:" + this.gameState.toString());
		if(phase == this.gameState)
		{
			
			if(phase==GameState.Init)
			{
				int rdyplayers = initPlayer(p);
				
				if(rdyplayers==2)
				{
					this.gameState = GameState.PreMain;
					//init phase:
				
					this.initGame();//draw 4/5 cards
					
					//send gamestate
					this.whitePlayer.sendMessgeToBattleServer(this.getGameStateMessage());
					this.blackPlayer.sendMessgeToBattleServer(this.getGameStateMessage());
					
					//send handupdate
					this.whitePlayer.sendMessgeToBattleServer(this.makeEffectMessage(this.getHandUpdateMessage(UColor.white)));
					this.blackPlayer.sendMessgeToBattleServer(this.makeEffectMessage(this.getHandUpdateMessage(UColor.black)));
					
				
					//send acctive ressis
					this.whitePlayer.sendMessgeToBattleServer(this.getActiveRessourcesMessage(UColor.white));
					this.blackPlayer.sendMessgeToBattleServer(this.getActiveRessourcesMessage(UColor.black));
					
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
			
			if(this.gameState == GameState.Main) 
			{
				this.gameState = GameState.Battle;
			}
			else
			{
				if(this.gameState == GameState.PreMain) 
				{
					this.gameState = GameState.TurnStart;
				}
			}
			 
			
			if(phase==GameState.PreMain)//do turn starting stuff
			{
			
				//ruleupdate -> resources -> starturn-> 
				//lower AC
				this.rulecountdowner(this.activePlayerColor);//count down before linger can act (tested)
				
				this.refreshRessoures(this.activePlayerColor);//first refresh resources (or topReaverTheas buff wont work)

				this.doTurnStartsTriggers();//before a card is drawn! (tested with halls of oum lasa)
				
				
				
				//to know that death of vaettr will reduce current growth
				for(Minion m : this.getPlayerFieldList(this.activePlayerColor))
				{
					m.turnsInplay++;
				}
				
				
				
				this.allMinionsOfASideCountDown(this.activePlayerColor, 1);
				
				//draw a card
				this.drawCards(this.activePlayerColor, 1);
				
				//String s = this.getResourcesUpdateMessage(); //automatically
				//this.addMessageToBothPlayers(s);
				//this.addMessageToPlayer(this.activePlayerColor, this.getHandUpdateMessage(this.activePlayerColor));
				
				//send handupdate to the active player
				
				
				
				//send cardstackupdates
				//s = getCardStackUpdate(this.activePlayerColor);
				//this.addMessageToBothPlayers(s);
				
				//send effect Messages!
				this.gameState = GameState.Main;
				
				this.sendEffectMessagesToPlayers();
				return;

			}
			
			
			if(phase==GameState.Main)//do end-turn stuff
			{
				//this.gameState = GameState.Battle;//is done above
				String s ="";
				//TODO do attacking and endgame effects (like crimson bull)
				//--- and do this first---...............................................
				
				allMinionsOfASideAttacks(this.activePlayerColor);
				
				this.sendEffectMessagesToPlayers();

				//reset Ac (=current count down) of minions with Ac<=0-----------------------------------------
				this.gameState = GameState.TurnEnd;//now we are in turn-end-phase
				
				this.allMinionsOfASideResetCountDown(this.activePlayerColor);
				this.doTurnEndsTriggers();
				this.sendEffectMessagesToPlayers();
				
				//the end:
				//switch active player, raise turn and send turnmessage to players
				this.activePlayerColor = Board.getOpposingColor(this.activePlayerColor);
				
				//send start new turn
				this.turnNumber++;
				this.hasSacrificed=false;
				
				this.gameState = GameState.PreMain;//now we are in premain-phase again (but the other guy :D)
				
				String trnm = this.makeEffectMessage(this.getTurnMessage(this.activePlayerColor));
				this.setActivePlayerStuff();
				
				
				
				this.whitePlayer.sendMessgeToBattleServer(trnm);
				this.blackPlayer.sendMessgeToBattleServer(trnm);
				
				
				return;
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
		UColor oppc = this.getOpposingColor(this.activePlayerColor);
		this.opponentPlayer = this.getPlayer(oppc);
		this.drawCorrodePossible = 0;
	}

	//return minion on specific position (null if not existing)
	public Minion getMinionOnPosition(UPosition pos)
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
	public void playCard(long cardid, ArrayList<UPosition> positions, long playerid)
	{
		if(this.gameState == GameState.End) return; //game has ended
		
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
		ArrayList<UPosition> allowedPosses = new ArrayList<UPosition>();
		
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
				allowedPosses.addAll(getallLegalTargets(fts, card.position.color, card));
			}
			
		}
		
		//test if target position is allwed
		for(UPosition poo : positions)
		{
			boolean found = false;
			for(UPosition pooo : allowedPosses)
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
		if(p.color == UColor.white) 
		{
			cressis=this.whitecurrentRessources;
		}
		
		int neededWild = 0;
		
		if(cressis[0] < card.card.cardSim.getGrowthCost(this, card) )
		{
			neededWild = card.card.cardSim.getGrowthCost(this, card) - cressis[0];
		}
		if(cressis[1] < card.card.cardSim.getOrderCost(this, card) )
		{
			neededWild = card.card.cardSim.getOrderCost(this, card) - cressis[1];
		}
		if(cressis[2] < card.card.cardSim.getEnergyCost(this, card) )
		{
			neededWild = card.card.cardSim.getEnergyCost(this, card) - cressis[2];
		}
		if(cressis[3] < card.card.cardSim.getDecayCost(this, card) )
		{
			neededWild = card.card.cardSim.getDecayCost(this, card) - cressis[3];
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
		System.out.println("remove card from hand " + Board.colorToString(card.position.color));
		this.currentHand.remove(card);
		
		//remove mana
		cressis[4]-=neededWild;
		cressis[0]-=card.card.cardSim.getGrowthCost(this, card);
		cressis[1]-=card.card.cardSim.getOrderCost(this, card);
		cressis[2]-=card.card.cardSim.getEnergyCost(this, card);
		cressis[3]-=card.card.cardSim.getDecayCost(this, card);
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
			UPosition posOfNewUnit = positions.get(0);
			this.summonUnitOnPosition(posOfNewUnit, card);
			
		}
		else
		{
			System.out.println("card is played");
			//TODO TargetTiles message
			card.card.cardSim.onCardPlay(this, this.activePlayerColor, positions, card);
		}

		if(card.cardType == Kind.SPELL )
		{
			//System.out.println("add to grave");
			//only add to grave when its not a linger spell (adding to grave it then done automatically by addingRule...
			if(card.card.cardSim.getLingerDuration(this,card) == 0) this.addMinionToGrave(card);
			//trigger effects
			for(Minion m : this.getAllMinionOfField())
			{
				m.card.cardSim.onPlayerPlayASpell(this, m, card);
				for(Minion e : m.getAttachedCards())
				{
					e.card.cardSim.onPlayerPlayASpell(this, e, card);
				}
			}
			
		}
		
		if(card.cardType == Kind.SPELL || card.cardType == Kind.ENCHANTMENT) //woodland memorial need this effect!
		{
			for(Minion m : this.getAllMinionOfField())
			{
				m.card.cardSim.onPlayerPlayASpellOrEnchantment(this, m, card);
				/*for(Minion e : m.attachedCards)
				{
					e.card.cardSim.onPlayerPlayASpell(this, e, card);
				}*/
				
				//TODO did i miss a spell-cathegory? (tested thundersurge destroy him, when it is targeted, frostwind not)
				if(m.typeId==366 && positions.size()>=1 && ( card.cardType == Kind.ENCHANTMENT || ((card.card.trgtArea != targetArea.TILE || card.card.trgtArea == targetArea.UNDEFINED || card.card.trgtArea == targetArea.SEQUENTIAL)) ) )
				{
					boolean targetIt = false;
					for(UPosition up : positions)
					{
						if(up.isEqual(m.position))
						{
							targetIt=true;
						}
					}
					if(targetIt) this.destroyMinion(m, m);
				}
				
				
			}
			
		}
		
		//TODO do trigger onCardPlayedTrigger and stuff
		
		//this.doDeathRattles();
		
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
	
	private String getSelectedTilesMessage(Minion card, ArrayList<UPosition> posis)
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
		for(UPosition pos : posis)
		{
			if(!tiles.equals("")) tiles+=",";
			//{"color":"white","position":"0,0"}
			tiles += pos.posToString();
		}
		
		
		String s="{\"SelectedTiles\":{\"card\":{\"id\":" + card.cardID + ",\"typeId\":" + card.typeId + ",\"tradable\":false,\"isToken\":" + Boolean.toString(card.isToken) + ",\"level\":" + card.lvl + "}";
		s+=",\"tiles\":["+tiles+"],\"area\":\""+area+ "\",\"color\":\""+Board.colorToString(card.position.color)+"\"}}";
		return s;
	}
	
	public boolean summonUnitOnPosition(UPosition pos, Minion m)
	{
		return summonUnitOnPosition( pos, m, true);
	}
	
	
	public boolean summonUnitOnPosition(UPosition pos, Minion m, Boolean dotriggers)
	{
		m.reset();
		Minion before = this.getPlayerField(pos.color)[pos.row][pos.column];
		
		if(before != null && before.Hp>=1) 
		{
			System.out.println("summon error, field full " + pos.posToString());
			return false; //we also allow to add the minino if it has hp<=0 (maybe throud deathrattles it is replaced but not jet removed)
		}
		
		m.position.color = pos.color;
		m.position.row = pos.row;
		m.position.column = pos.column;
		
		m.setAc(m.card.ac);
		m.setAttack(m.card.ap);
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
		m.setDefaultValues(this);
		m.card.cardSim.getBattlecryEffect(this, m, null);
		
		//apply new effects to new minion
		if(dotriggers)
		{
			for(Minion mins : this.getAllMinionOfField())
			{
				mins.card.cardSim.onMinionIsSummoned(this, mins, m);
			}
			
			for(Minion rule : this.getAllRules())
			{
				rule.card.cardSim.onMinionIsSummoned(this, rule, m);
			}
		}
		
		//TODO add effects of rules-updates-scrolls and enchantments? (are there such enchantments?)
		
		//TODO add status updates if minion was changed? (should be done in the summon-triggers)
		
		
		
		//add minion to battlefield!
		this.getPlayerField(m.position.color)[m.position.row][m.position.column] = m;
		return true;
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
		if(this.gameState == GameState.End) return; //game has ended
		
		this.gameState = GameState.End;
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
		
		UColor winnercolor = Board.getOpposingColor(p.color);
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
		if(this.gameState == GameState.End) return; //game has ended
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
		if(p.color == UColor.white) 
		{
			cressis=this.whitecurrentRessources;
		}
		
		int neededWild = 0;
		
		if(cressis[0] < card.card.cardSim.getGrowthCost(this, card) )
		{
			neededWild = card.card.cardSim.getGrowthCost(this, card) - cressis[0];
		}
		if(cressis[1] < card.card.cardSim.getOrderCost(this, card) )
		{
			neededWild = card.card.cardSim.getOrderCost(this, card) - cressis[1];
		}
		if(cressis[2] < card.card.cardSim.getEnergyCost(this, card) )
		{
			neededWild = card.card.cardSim.getEnergyCost(this, card) - cressis[2];
		}
		if(cressis[3] < card.card.cardSim.getDecayCost(this, card) )
		{
			neededWild = card.card.cardSim.getDecayCost(this, card) - cressis[3];
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
			ArrayList<UPosition> posis = this.getFreePositions(p.color);
			
			for(UPosition pos : posis)
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
				ArrayList<UPosition> tiles1 = getallLegalTargets(fts, card.position.color, card);
				
				String ts1="";
				for(UPosition pos : tiles1)
				{
					if(!ts1.equals("")) ts1+=",";
					ts1 += pos.posToString();
				}
				tilesets = "["+ ts1 + "]";
				
				tileSelector sts = card.card.cardSim.getTileSelectorForSecondSelection();
				if(sts!=tileSelector.None)
				{
					ArrayList<UPosition> tiles2 = getallLegalTargets(sts, card.position.color, card);
					String ts2="";
					for(UPosition pos : tiles2)
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
				
				ArrayList<ArrayList<UPosition>> sequentials = getSequentialPositions(UColor.white, card.card.cardSim.getTargetAreaGroup());
				for(ArrayList<UPosition> sequals : sequentials)
				{
					String sequal = "";
					for(UPosition po : sequals)
					{
						if(!sequal.equals("")) sequal+=",";
						sequal+=po.posToString();
					}
					
					if(!targetAreaGroups.equals("")) targetAreaGroups+="],[";
					targetAreaGroups+=sequal;
				}
				
				sequentials.clear();
				sequentials = getSequentialPositions(UColor.black, card.card.cardSim.getTargetAreaGroup());
				for(ArrayList<UPosition> sequals : sequentials)
				{
					String sequal = "";
					for(UPosition po : sequals)
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
	
	private ArrayList<UPosition> getallLegalTargets(tileSelector ts, UColor ownColor, Minion card)
	{
		ArrayList<UPosition> ret = new ArrayList<UPosition>();
		
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
					ret.add(new UPosition(UColor.white, i, j));
					ret.add(new UPosition(UColor.black, i, j));
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
							ret.add(new UPosition(UColor.white, i, j));	
						}

					}
					
					m = this.blackField[i][j];
					if(m!=null )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
							ret.add(new UPosition(UColor.black, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && m.attackType == AttackType.MELEE )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.attackType == AttackType.RANGED || m.attackType == AttackType.BALLISTIC) )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_ranged_creatures)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && (m.attackType == AttackType.RANGED) )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.attackType == AttackType.RANGED) )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && m.maxAc>=1 )
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_units_with_enchantments)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null)
					{
						boolean addit = false;
						for(Minion e: m.getAttachedCards())
						{
							if(e.cardID>=0) addit=true;
						}
						if(addit) 
						{
							if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
							{
								ret.add(new UPosition(UColor.white, i, j));
							}
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null)
					{
						boolean addit = false;
						for(Minion e: m.getAttachedCards())
						{
							if(e.cardID>=0) addit=true;
						}
						if(addit) 
						{
							if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
							{
								ret.add(new UPosition(UColor.black, i, j));
							}
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE))
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.all_creatures_with_hp_less_or_equal_2)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = this.whiteField[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE) && m.Hp <=2)
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE) && m.Hp <=2)
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.white, i, j));
						}
					}
					
					m = this.blackField[i][j];
					if(m!=null && (m.card.cardKind == Kind.STRUCTURE))
					{
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(UColor.black, i, j));
						}
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
						ret.add(new UPosition(UColor.white, i, j));
					}
					
					m = this.blackField[i][j];
					if(m==null )
					{
						ret.add(new UPosition(UColor.black, i, j));
					}
				}
			}
			return ret;
		}
		
		// opponent stuff----------------------------------------------
		UColor oppCol= Board.getOpposingColor(ownColor);
		
		Minion[][] field = this.getPlayerField(oppCol);
		
		if(ts == tileSelector.opp_all)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					ret.add(new UPosition(oppCol, i, j));
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(oppCol, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(oppCol, i, j));
						}
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
						if(!((m.cardType == Kind.ENCHANTMENT || m.cardType == Kind.SPELL) && m.hasWard(this) && m.position.color != ownColor))
						{
						ret.add(new UPosition(oppCol, i, j));
						}
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
						ret.add(new UPosition(oppCol, i, j));
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
					ret.add(new UPosition(oppCol, i, j));
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
						ret.add(new UPosition(oppCol, i, j));
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
						ret.add(new UPosition(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		if(ts == tileSelector.own_beasts)
		{
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					Minion m = field[i][j];
					if(m!=null && (m.card.cardKind == Kind.CREATURE) && m.getSubTypes().contains(SubType.Beast))
					{
						ret.add(new UPosition(oppCol, i, j));
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
						ret.add(new UPosition(oppCol, i, j));
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
						ret.add(new UPosition(oppCol, i, j));
					}
				}
			}
			return ret;
		}
		
		return ret;
	}
	
	
	public void sacrificeCard(long cardid, String ressource, long playerid)
	{
		if(this.gameState == GameState.End) return; //game has ended
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
		if(p.color == UColor.white) 
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
			
			this.changeCurrentRessource(ResourceName.WILD, p.color, 1);
			this.changeMaxRessource(ResourceName.WILD, p.color, 1);
			
		}
		
		if(ressource.equals("GROWTH"))
		{
			this.changeCurrentRessource(ResourceName.GROWTH, p.color, 1);
			this.changeMaxRessource(ResourceName.GROWTH, p.color, 1);
		}
		
		if(ressource.equals("ORDER"))
		{
			this.changeCurrentRessource(ResourceName.ORDER, p.color, 1);
			this.changeMaxRessource(ResourceName.ORDER, p.color, 1);
		}
		
		if(ressource.equals("ENERGY"))
		{
			this.changeCurrentRessource(ResourceName.ENERGY, p.color, 1);
			this.changeMaxRessource(ResourceName.ENERGY, p.color, 1);
		}
		
		if(ressource.equals("DECAY"))
		{
			this.changeCurrentRessource(ResourceName.DECAY, p.color, 1);
			this.changeMaxRessource(ResourceName.DECAY, p.color, 1);
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
	
	
	public static String colorToString(UColor col)
	{
		if(col == UColor.white) return "white";
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
	
	public static UColor getOpposingColor(UColor col)
	{
		if(col == UColor.white) return UColor.black;
		return UColor.white;
	}
	
	public ArrayList<Minion> getMinionsFromPositions(ArrayList<UPosition> ps)
	{
		ArrayList<Minion> mins = new ArrayList<Minion>();
		for(Minion m : this.getAllMinionOfField())
		{
			for(UPosition p : ps)
			{
				if(m.position.isEqual(p))
				{
					mins.add(m);
				}
			}
		}
		return mins;
	}
	
	
	public  ArrayList<UPosition> getFreePositionsFromPosition(ArrayList<UPosition> tiles)
	{
		ArrayList<UPosition> posMoves = new ArrayList<UPosition>();
		
		for(UPosition p : tiles)
		{
			Minion m = this.whiteField[p.row][p.column];
			if(p.color == UColor.black) m = this.blackField[p.row][p.column];
				
				if(m == null )
				{
					posMoves.add(new UPosition(p));
				}
		}
		
		return posMoves;
	}
	
	public  ArrayList<UPosition> getFreePositions(UColor col)
	{
		ArrayList<UPosition> posMoves = new ArrayList<UPosition>();
		
		Minion[][] side = this.getPlayerField(col);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = side[i][j];
				if(m == null )
				{
					posMoves.add(new UPosition(col, i, j));
				}
			}
		}
		
		return posMoves;
	}
	
	
	ArrayList<UPosition> dfsList= new ArrayList<UPosition>();//shared variable for DFS
	ArrayList<UPosition> dfsSolutionList= new ArrayList<UPosition>();
	
	public  ArrayList<ArrayList<UPosition>> getSequentialPositions(UColor col, TargetAreaGroup tag)
	{
		this.dfsList.clear();
		
		if(tag==TargetAreaGroup.own_creatures && col != this.activePlayerColor) return new ArrayList<ArrayList<UPosition>>();
		
		UColor markCol = Board.getOpposingColor(col);
		Minion[][] side = this.getPlayerField(col);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<3; j++)
			{
				Minion m = side[i][j];
				if(m != null )
				{
					if(tag==TargetAreaGroup.own_creatures && m.cardType == Kind.CREATURE) this.dfsList.add(new UPosition(col, i, j));
					
					if(tag==TargetAreaGroup.all_units) this.dfsList.add(new UPosition(col, i, j));
					
				}
			}
		}
		UColor opcol = Board.getOpposingColor(col);

		ArrayList<ArrayList<UPosition>> retval = new ArrayList<ArrayList<UPosition>>();

		for(UPosition p1 : this.dfsList)
		{
			if(p1.color == markCol) continue;
			this.dfsSolutionList.clear();
			
			this.deepFirstSearch(p1, markCol, tag);
			
			ArrayList<UPosition>blubb = new ArrayList<UPosition>(this.dfsSolutionList); //we can take the elements of dfsSol.list, they are created with new
			retval.add(blubb);
		}
		
		
		return retval;
	}
	
	private void deepFirstSearch(UPosition p, UColor markCol, TargetAreaGroup tag)
	{
		//add to sol.list + mark!
		this.dfsSolutionList.add(new UPosition(p));
		p.color = markCol;
		
		ArrayList<UPosition> neightbours = p.getNeightbours();
		
		for(UPosition p1 : this.dfsList)
		{
			if(p1.color == markCol) continue;
			
			//test if p1 is a neightbour of p
			boolean isNeightbour= false;
			for(UPosition pipi : neightbours)
			{
				if(pipi.column == p1.column && pipi.row == p1.row) isNeightbour=true; //we ignore color, cause p is marked
			}
			
			if(isNeightbour)//we use colorigno, cause we use color as marker :D
			{
				this.deepFirstSearch(p1, markCol, tag);
			}
			
		}
	}
	
	
	
	public static ArrayList<UPosition> getAttackPositions(Minion m)
	{
		targetArea area = m.card.trgtArea;
		ArrayList<UPosition> posMoves = new ArrayList<UPosition>();
		int attackerRow = m.position.row;
		int attackerColumn = m.position.column;
		UColor col = Board.getOpposingColor(m.position.color);
		
		//only forward and radius4 are legal
		
		if(area == targetArea.FORWARD)
		{
			int rowdi = attackerRow;
			for(int i = 0; i<3; i++)
			{
				UPosition p = new UPosition(col, rowdi, i);
				posMoves.add(p);
			}
			UPosition p = new UPosition(col, rowdi, 4);
			posMoves.add(p);
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
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new UPosition(col, rowdi, columndi));
			columndi--;
			
			//first tile
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new UPosition(col, rowdi, columndi));
			
			//tile over first tile
			if(attackerRow==1 || attackerRow==3) columndi++;
			rowdi--;
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new UPosition(col, rowdi, columndi));
			if(attackerRow==1 || attackerRow==3) columndi--;
			rowdi++;
			
			//tile under first tile
			rowdi++;
			if(rowdi >=0 && rowdi <= 4 && columndi>=0 && columndi <=2) posMoves.add(new UPosition(col, rowdi, columndi));
			
		}
		
		if(area == targetArea.RADIUS_7)//there is not such an unit that attacks 7 tiles!
		{
			
		}
		
		return posMoves;
	}
	
	
	
	public ArrayList<UPosition> getMovePositions(UColor col, int row, int column)
	{
		UPosition pp = new UPosition(col, row, column);
		ArrayList<UPosition> posMoves = pp.getNeightbours();
		
		ArrayList<UPosition> posMoves2 = new ArrayList<UPosition>();
		Minion[][] chosenField = this.getPlayerField(col);
		
		for(UPosition p : posMoves)
		{
			if((chosenField[p.row][p.column]) == null)
			{
				posMoves2.add(p);
			}
		}
		
		return posMoves2;
	}
	
	
	public String getPossibleMoveString(UColor col, int row, int column)
	{
		String s ="";
		//{\"color\":\"white\",\"position\":\"1,1\"},
		for(UPosition p : this.getMovePositions(col, row, column))
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
	 
	
	public void getAbilityInfo(String ability, UPosition posi , long playerid)
	{
		if(this.gameState == GameState.End) return; //game has ended
		
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
			ArrayList<UPosition> pospositions = new ArrayList<UPosition>();
			ArrayList<UPosition> pospositions2 = new ArrayList<UPosition>();
			boolean needposi2 = false;
			for(ActiveAbility aa : m.card.abilitys )
			{
				if(aa.id ==  va.id)
				{
					
					ressis= aa.hasEnoughRessis(this, m);
					pospositions.addAll(aa.getPositions(this, m));
					pospositions2.addAll(aa.getSecondPositions(this, m));
					playable = aa.isPlayAble(this, m, pospositions, pospositions2);
					needposi2 = aa.needPosition2(this, m);
					break;
				}
			}
			
			tilesets = "";
			for(UPosition popo : pospositions )
			{
				if(!tilesets.equals("")) tilesets += ",";
				tilesets += popo.posToString();
			}
			
			if(needposi2)
			{
				tilesets+="],[";
				String tilesets2 = "";
				for(UPosition popo : pospositions2 )
				{
					if(!tilesets2.equals("")) tilesets2 += ",";
					tilesets2 += popo.posToString();
				}
				tilesets+=tilesets2;
			}
			
		//}
		
		
		
		String hasEnoughResources = Boolean.toString(ressis);
		String isPlayable = Boolean.toString(playable);
		if(!tilesets.equals(""))
		{
			tilesets = "["+tilesets+"]";
		}
		s = "{\"unitPosition\":"+posi.posToString()+",\"abilityId\":\""+ability+"\",\"hasEnoughResources\":"+hasEnoughResources+",\"isPlayable\":"+isPlayable+",\"data\":{\"selectableTiles\":{\"tileSets\":[" + tilesets +"]},\"targetArea\":\"UNDEFINED\"},\"msg\":\"AbilityInfo\"}";

		//{\"color\":\"white\",\"position\":\"1,1\"},...
		
		
		p.sendMessgeToBattleServer(s);
		return ;
	}

	
	public void activateAbility(String ability, UPosition unitPos, ArrayList<UPosition> poses, long playerid)
	{
		if(this.gameState == GameState.End) return; //game has ended
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
		boolean needposi2 = false;
		
		ActiveAbility va = new ActiveAbility(ability, 0, 0, 0, 0, 0);
		ArrayList<UPosition> pospositions = new ArrayList<UPosition>();
		ArrayList<UPosition> pospositions2 = new ArrayList<UPosition>();
		for(ActiveAbility aa : m.card.abilitys )
		{
			if(aa.id ==  va.id)
			{
				va = aa;
				ressis= aa.hasEnoughRessis(this, m);
				pospositions.addAll(aa.getPositions(this, m));
				pospositions2.addAll(aa.getSecondPositions(this, m));
				playable = aa.isPlayAble(this, m, pospositions, pospositions2);
				
				needposi = aa.needPosition(this, m);
				needposi2 = aa.needPosition2(this, m);
				break;
			}
		}
		
		if(!playable || !ressis)
		{
			//TODO error
			return;
		}
		
		if((needposi && poses.size() ==0) || (needposi2 && poses.size() <=1) )
		{
			//TODO error (target position is needed, but not sended to us)
			return;
		}
		
		if(needposi && poses.size() >=1 )
		{
			Boolean isinList = false;
			UPosition tp = poses.get(0);
			for(UPosition ppp : pospositions)
			{
				if(tp.isEqual(ppp)) isinList=true;
			}
			
			if(!isinList) 
			{
				//TODO error (wrong position)
				return;
			}
		}
		
		if(needposi2 && poses.size() >=2 )
		{
			Boolean isinList = false;
			UPosition tp = poses.get(1);
			for(UPosition ppp : pospositions2)
			{
				if(tp.isEqual(ppp)) isinList=true;
			}
			
			if(!isinList) 
			{
				//TODO error (wrong position2)
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
			UPosition targ = poses.get(0);
			
			m.movesThisTurn++;
			this.unitChangesPlace(unitPos, targ);
			
			//TODO trigger these effects also pother and equal spells?
			
			
			
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
	public void unitChangesPlace(UPosition ofrom, UPosition oto, Boolean doTrigger, Boolean isMove)
	{
		
		
		if(isMove)
		{
			String s = "{\"MoveUnit\":{\"from\":"+ofrom.posToString()+",\"to\":"+oto.posToString()+"}}";
			this.addMessageToBothPlayers(s);
		}
		
		UPosition from  = new UPosition(ofrom);
		UPosition to  = new UPosition(oto);
		
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
		
		if(isMove)
		{
			for(Minion mnn : this.getAllMinionOfField())
			{
				mnn.card.cardSim.onMinionMoved(this, mnn, m);
			}
			
			for(Minion e : m.getAttachedCards())
			{
				e.card.cardSim.onMinionMoved(this, e, m);
			}
			
			for(Minion mnn : this.getAllRulesWithColorFirst(m.position.color))
			{
				mnn.card.cardSim.onMinionMoved(this, mnn, m);
			}
		}
		
		if(doTrigger)doOnFieldChangedTriggers();
	}
	
	public void unitChangesPlace(UPosition from, UPosition to)
	{
		unitChangesPlace( from,  to, true, true);
	}
	
	public void doOnFieldChangedTriggers()
	{
		//TODO also for enchantments? dont think so or?
		
		for(Minion m : this.getAllMinionOfField())
		{
			m.card.cardSim.onFieldChanged(this, m);
		}
		
		for(Minion rule : this.getAllRules())
		{
			rule.card.cardSim.onFieldChanged(this, rule);
		}
		
	}
	
	private class DmgRegisterUnit
	{
		public Minion attacker;
		public Minion deffender;
		public AttackType attackType;
		public DamageType damageType;
		public int dmgdone;
		public int newhp;
		
		public DmgRegisterUnit(Minion a, Minion d, AttackType att, DamageType dt, int dmgd, int hp)
		{
			this.attacker=a;
			this.deffender =d;
			this.attackType=att;
			this.dmgdone = dmgd;
			this.newhp = hp;
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
            performDmgTriggers(pair.attacker, pair.deffender, pair.attackType, pair.damageType, pair.dmgdone, pair.newhp);
        }
        
        
        //TODO diedtriggers + remove minion
        
        doDeathRattles2(attacker, overdmg, attackType, damageType);
        
        
        return overdmg;
	}
	
	
	public class SummonItem
	{
		public Minion minion;
		public UPosition pos;
		public int flags=0;
		
		public SummonItem(Minion a, UPosition p)
		{
			this.minion=a;
			this.pos = p;
			this.flags=0;
		}
		
		public SummonItem(Minion a, UPosition p, int flag)
		{
			this.minion=a;
			this.pos = p;
			this.flags=flag;
		}
	}
	
	public boolean isDominionActive(UColor mySide)
	{
		for(Minion rule : this.getAllRules())
		{
			if(rule.typeId == 338) return true;
		}
		
		for(Minion idol : this.getPlayerIdols(Board.getOpposingColor(mySide)))
		{
			if(idol.Hp<=0) return true;
		}
		
		return false;
	}
	
	private ArrayList<SummonItem> summonList = new ArrayList<SummonItem>(); 
	
	public void doDeathRattles2(Minion attacker, int overdmg, AttackType attacktype, DamageType dmgtype )
	{
		
		int died=0;
        this.graveWhiteChanged=false;
        this.graveBlackChanged=false;
        ArrayList<Minion> allmins = this.getAllDeadMinionsOfField();
        ArrayList<Minion> allminsallive = this.getAllMinionOfField();
        for(Minion m : allmins)
        {
        	
        	if(m.Hp <= 0 && !m.deadTriggersDone)
        	{
        		m.deadTriggersDone=true;//only to be save :D
        		for(Minion mnn : allminsallive)
        		{
        			if(mnn.Hp <= 0) continue; //only the living effects are triggered
        				
        			mnn.card.cardSim.onMinionDiedTrigger(this, mnn, m, attacker, attacktype, dmgtype);
        			
        			for(Minion e : mnn.getAttachedCards())
            		{
        				e.card.cardSim.onMinionDiedTrigger(this, e, m, attacker, attacktype, dmgtype);
            		}
            		
        		}
        		
        		//do triggers for linger (they are triggered BEFORE enchantments (tested aescalon spires VS ilthorn seed, aescalon wins always (regardless of order of play))
        		for(Minion rule : this.getAllRulesWithColorFirst(m.position.color))
    			{
        			rule.card.cardSim.onMinionDiedTrigger(this, rule, m, attacker, attacktype, dmgtype);
    			}

        		//do triggers for enchantments
        		for(Minion e : m.getAttachedCards())
        		{
        			//say enchantment that owner died
        			
        			e.card.cardSim.onMinionDiedTrigger(this, e, m, attacker, attacktype, dmgtype);
        			e.owner=null;
        			if(e.cardID>=0)
        			{
        				this.getPlayerGrave(e.position.color).add(e);
        				if(e.position.color == UColor.white) 
        				{
        					graveWhiteChanged=true;
        				}
        				else
        				{
        					graveBlackChanged=true;
        				}
        			}
        			m.removeEnchantment(e, false, this);
        		}
        		//m.attachedCards.clear();
        		
        		
        		
        		//for setting addToHandAfterDead to true for replicaton :D maybe we change this to true at battlecry?
        		if(m.typeId == 287)
        		{
        			m.addToHandAfterDead=false;
        			if(this.getCurrentRessource(ResourceName.ENERGY, m.position.color)>=2)
        			{
        				m.addToHandAfterDead=true;
        			}
        		}
        		
        		this.addMinionToGrave(m);//with triggers of enchantments
        		//ondeathrattle have summon effects
        		m.card.cardSim.onDeathrattle(this, m, attacker, attacktype, dmgtype);
        		m.card.cardSim.onMinionLeavesBattleField(this, m);
        		
        		died++;
        	
        	}
        }
        
        if(died>=1) doOnFieldChangedTriggers();//TODO do it allways?
        
        //TODO consider conenctrate fire for summon items!
        
        //summon units
        if(!attacker.isRelentless(this) || overdmg == 0 || attacktype !=AttackType.MELEE || attacker.Hp <=0 || dmgtype != DamageType.COMBAT) //dont do spawn a minion if a relentless minion attacks and he can further attack!
		{
        	//do onDestroy Triggers
        	for(SummonItem si : this.summonList)
        	{
        		Minion before = this.getPlayerField(si.pos.color)[si.pos.row][si.pos.column];
        		if(before==null || before.Hp<=0)
        		{
        			boolean summoned = this.summonUnitOnPosition(si.pos, si.minion);
        			if(summoned && si.flags == 1)
        			{
        				Minion after = this.getPlayerField(si.pos.color)[si.pos.row][si.pos.column];
        				int buff = 1-after.getAc();
        				after.buffMinion(0, 0, buff, this);
        			}
        			
        			if(summoned && si.minion.cardID>=0)
        			{
        				//it has a cardId-> it is resummoned!
        				
        			}
        		}
        	}
        	this.summonList.clear();
		}

        
        if(died>=1)
        {
        	if(graveWhiteChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.white));
        	if(graveBlackChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.black));
        }
        
        
	}
	
	public boolean addItemToSummonList(SummonItem si)
	{
		for(SummonItem s : this.summonList)
		{
			if(s.pos.isEqual(si.pos)) return false;
		}
		this.summonList.add(si);
		return true;
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
		
		//done in remove enchantments
		/*if(m.cardType == Kind.ENCHANTMENT)//special deathrattle for enchantments!
		{
			m.card.cardSim.onDeathrattle(this, m, m, AttackType.UNDEFINED, DamageType.TERMINAL);
		}*/
		
		if(m.addToHandAfterDead)
		{
			this.getPlayerHand(m.position.color).add(m);
			this.addMessageToPlayer(m.position.color, this.getHandUpdateMessage(m.position.color));
		}
		else
		{
			this.getPlayerGrave(m.position.color).add(m);
			if(m.position.color == UColor.white) 
			{
				graveWhiteChanged=true;
			}
			else
			{
				graveBlackChanged=true;
			}
		}
		m.deadTriggersDone=true;//only to be save :D
		m.addToHandAfterDead=false;//only to be save :D
		
		//remove from field
		//this.getPlayerField(m.color)[m.row][m.column]=null;
		
		
		
		//TODO perform on fieldchanged trigger?
		
	}
	
	private int performDmg(ArrayList<Minion> targets, Minion attacker, AttackType attackType, DamageType damageType, int odmg)
	{
		int overAttack2 = 0;
		int dmg = odmg;
		for(Minion target : targets)
		{
			dmg = odmg;
			if(odmg == -100) //aoe dmg :D
			{
				dmg = target.aoeDmgToDo;
				target.aoeDmgToDo=0;
			}
			
			//is target immune to damage?
			boolean immuneToDmg = false;
			for(Minion ench : target.getAttachedCards())
			{
				immuneToDmg = immuneToDmg || ench.card.cardSim.isImuneToDmg(this, ench, target, attacker, attackType, damageType);
			}
			if(immuneToDmg) dmg = 0;
			
			if(target.isIdol && dmg >=1)
			{
				for(Minion rule : this.getAllRules())//get bonus form lingering spells
				{
					dmg += rule.card.cardSim.getIdolDamageBonus(this, rule, attackType, damageType);
				}
			}
			
			int overAttack = 0;
			int oldHP = target.Hp;
			
			int newHPDefender = oldHP;
			if(damageType == DamageType.COMBAT || damageType == DamageType.PHYSICAL)
			{
					int rdmg = target.curse + dmg;
					if(rdmg >=1 && (target.imuneToNextDmg || target.imuneToDmg)) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
					if(rdmg >=2 && target.hasPotionOfResistance(this)) rdmg = 1;
					newHPDefender = Math.min(target.Hp , target.Hp + target.getArmor(this) - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.MAGICAL)
			{
					int rdmg = dmg;
					if(rdmg >=1 && (target.imuneToNextDmg || target.imuneToDmg)) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
					if(rdmg >=2 && target.hasPotionOfResistance(this)) rdmg = 1;
					newHPDefender = Math.min(target.Hp , target.Hp + target.getMagicRessi(this) - rdmg); //defender is not healed if Armor > attack :D
			}
			
			if(damageType == DamageType.POISON)
			{
					int rdmg = target.curse + dmg;
					
					for(Minion rule : this.getAllRules())//get bonus form lingering spells
					{
						rdmg += rule.card.cardSim.getPoisonBonus(this, rule);
					}
					
					if(rdmg >=1 && (target.imuneToNextDmg || target.imuneToDmg)) 
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
					//TODO is pure dmg nilled by plating?--- yes!
					if(rdmg >=1 && (target.imuneToNextDmg || target.imuneToDmg)) 
					{
						target.imuneToNextDmg=false;
						rdmg = 0;
					}
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
			
			
			
			if((damageType == DamageType.COMBAT || damageType == DamageType.PHYSICAL) && target.typeId == 332)
			{
				
				overAttack2=0;
				//dont do dmg to voidgate
				this.getDmgUnitMessage(attacker, target, 0, 0, attackType, damageType, false);
				this.dmgregister.add(new DmgRegisterUnit(attacker, target, attackType, damageType, 0, newHPDefender));
				
				//lead dmg to idol
				Minion idol = this.getPlayerIdol(target.position.color, target.position.row);
				this.doDmg(idol, attacker, realdmgDone, attackType, damageType);
			}
			else
			{
				target.Hp = newHPDefender;
				this.getDmgUnitMessage(attacker, target, dmgdone, realdmgDone, attackType, damageType, iskill);
				this.dmgregister.add(new DmgRegisterUnit(attacker, target, attackType, damageType, dmgdone, newHPDefender));
			}
			
		}
		
		
		for(Minion target : targets)
		{
			if(target.isIdol || target.Hp>=1)
			{
				String s="";
				s= this.getStatusUpdateMessage(target);
				this.addMessageToBothPlayers(s);
			}
			
			if(target.isIdol )
			{
				overAttack2=0;
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
	
	
	private void performDmgTriggers(Minion attacker, Minion deffender, AttackType attackType, DamageType damageType , int dmgdone, int newHp)
    {

        int attackAP = attacker.getAttack(this);
        int attackHP = attacker.Hp;
        int defferHP = deffender.Hp;
        int attackerAc = attacker.getAc();

        //register that the targets got dmg
        if(dmgdone>=1)deffender.numberOfDmgTaken++;
        
        
        if (attackType == AttackType.MELEE)
        {
        	//perform proximityDMG
        	if(newHp <=0 && deffender.cardID == 90)
        	{
        		performDmg(attacker, deffender, AttackType.UNDEFINED , DamageType.COMBAT, 4);
        	}
        	
        	//perform spiky-dmg from all sources (minion himself, enchantments + linger spells)
        	int spikydmg = deffender.card.cardSim.getSpikyDamage(this, deffender, deffender);
        	if(spikydmg>=1)
        	{
        		performDmg(attacker, deffender, AttackType.MELEE_COUNTER , DamageType.COMBAT, spikydmg);
        	}
        	
        	for (Minion ench : deffender.getAttachedCards())
        	{
            
        		spikydmg = ench.card.cardSim.getSpikyDamage(this, ench, deffender);
            	if(spikydmg>=1)
            	{
            		performDmg(attacker, deffender, AttackType.MELEE_COUNTER , DamageType.COMBAT, spikydmg);
            	}
            }
        	
        	
        	for (Minion rule : this.getAllRulesWithColorFirst(deffender.position.color))
        	{
            
        		spikydmg = rule.card.cardSim.getSpikyDamage(this, rule, deffender);
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
        	
        	for (Minion ench : deffender.getAttachedCards())
        	{
            
        		if(ench.card.cardSim.isPoisonous(this, deffender))
            	{
            		attacker.addnewPoison(this, deffender.position.color);
            	}
            }
        	//TODO linger?
        	
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
        	deffender.card.cardSim.onMinionGotDmgTrigger(this, deffender, deffender, dmgdone, attacker);
        	
            for (Minion ench : deffender.getAttachedCards())
            {
            	ench.card.cardSim.onMinionGotDmgTrigger(this, ench, deffender, dmgdone, attacker);
            }
            
            for (Minion rule : this.getAllRulesWithColorFirst(deffender.position.color))
        	{
            	rule.card.cardSim.onMinionGotDmgTrigger(this, rule, deffender, dmgdone, attacker);
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
        	
        	for (Minion ench : attacker.getAttachedCards())
        	{
            
        		if(ench.card.cardSim.isPoisonous(this, deffender))
            	{
        			deffender.addnewPoison(this, attacker.position.color);
            	}
            }
        	//TODO linger?
        }
        
        

        //do onattck unit deals dmg
        //TODO are all effects triggered, when dmgdone =0?
        if (attackHP >= 1)
        {
        	attacker.card.cardSim.onMinionDidDmgTrigger(this, attacker, deffender, attacker, dmgdone, attackType, damageType);

        	for (Minion ench :attacker.getAttachedCards())
        	{
        		ench.card.cardSim.onMinionDidDmgTrigger(this, ench, deffender, attacker, dmgdone, attackType, damageType);
            }
        	
        	for (Minion mnn : this.getAllMinionOfField())
        	{
        		if(mnn != attacker)
        		{
        			mnn.card.cardSim.onMinionDidDmgTrigger(this, mnn, deffender, attacker, dmgdone, attackType, damageType);
        		}
            }
        	
        	for (Minion rule : this.getAllRulesWithColorFirst(attacker.position.color))
        	{
        		rule.card.cardSim.onMinionDidDmgTrigger(this, rule, deffender, attacker, dmgdone, attackType, damageType);
            }
        }
        
        
        


    }

	
	private String getUnitAttackMessage(Minion m, ArrayList<UPosition> target)
	{
		//{"UnitAttackIdol":{"attacker":{"color":"white","position":"1,1"},"idol":1}},
		//OR
		//{"UnitAttackTile":{"source":{"color":"white","position":"1,1"},"target":{"color":"black","position":"1,2"}}},
		//if there are more than 1 target in targets, use a unitattackIdol-msg, if a unit is among the targets
		String source=this.getSource(m);
		String targets = "";
		
		UPosition tar = target.get(0);
		int tarCol = tar.column;
		for(UPosition mm : target)
		{
			int tarColt = mm.column;
			
			if(tarColt > tarCol)
			{
				tar = mm;
				tarCol = tarColt;
			}
		}
		
		String ret = "";
		
		if(tar.column == 4)
		{
			//{"UnitAttackIdol":{"attacker":{"color":"white","position":"1,1"},"idol":1}},
			ret = "{\"UnitAttackIdol\":{\"attacker\":"+m.position.posToString()+",\"idol\":" + tar.row + "}}";
		}
		else
		{
			//{"UnitAttackTile":{"source":{"color":"white","position":"1,1"},"target":{"color":"black","position":"1,2"}}},
			ret ="{\"UnitAttackTile\":{" + source + ",\"target\":"+tar.posToString()+"}}"; 
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
			
			
			if(dmgt == DamageType.TERMINAL)
			{
				//no damageunit message, and terminal + remove are swapped (or client will have a null-pointer exception)
				
				s= "{\"TerminateUnit\":{\"targetTile\":"+defender.position.posToString()+",\"amount\":" + realdmgdone; //this time realdmg
				s+= ",\"hp\":" + defender.Hp + ",\"attackType\":\""+Board.attackTypeToString(att)+"\",\"damageType\":\""+Board.damageTypeToString(dmgt)+"\",\"sourceCard\":{\"id\":" + attacker.cardID ;
				s+= ",\"typeId\":38,\"tradable\":true,\"isToken\":"+Boolean.toString(attacker.isToken)+",\"level\":"+attacker.lvl+"}}}";
				this.addMessageToBothPlayers(s);
				
				s="{\"RemoveUnit\":{\"tile\":"+defender.position.posToString()+",\"removalType\":\"DESTROY\"}}"; //removalType destroy, because it was dmg
				this.addMessageToBothPlayers(s);
				
				return;
			}

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
	
	public void removeMinionToHand(Minion m)
	{
		
		this.graveWhiteChanged=false;
        this.graveBlackChanged=false;
		
		String s = "{\"UnsummonUnit\":{\"target\":"+m.position.posToString()+"}}";
		this.addMessageToBothPlayers(s);
		if(m.position.row >=0 && m.position.column >=0)
		{
			this.getPlayerField(m.position.color)[m.position.row][m.position.column]=null;
		}
		
		for(Minion e : m.getAttachedCards())
		{
			if(e.cardID>=0)
			{
				if(e.position.color == UColor.white) 
				{
					graveWhiteChanged=true;
				}
				else
				{
					graveBlackChanged=true;
				}
			}
			m.removeEnchantment(e, false, this);
		}
		//m.attachedCards.clear();
        
		m.position.row=-1;
		m.position.column=-1;
		
        doOnFieldChangedTriggers();
        this.getPlayerHand(m.position.color).add(m);
        this.addMessageToPlayer(m.position.color, this.getHandUpdateMessage(m.position.color));
        if(graveWhiteChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.white));
    	if(graveBlackChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.black));
        
	}
	
	public void addMinionFromFieldToDeck(Minion m)
	{
		
		this.graveWhiteChanged=false;
        this.graveBlackChanged=false;
		
		String s = "{\"UnsummonUnit\":{\"target\":"+m.position.posToString()+"}}";
		this.addMessageToBothPlayers(s);
		if(m.position.row >=0 && m.position.column >=0)
		{
			this.getPlayerField(m.position.color)[m.position.row][m.position.column]=null;
		}
		
		for(Minion e : m.getAttachedCards())
		{
			if(e.cardID>=0)
			{
				if(e.position.color == UColor.white) 
				{
					graveWhiteChanged=true;
				}
				else
				{
					graveBlackChanged=true;
				}
			}
			m.removeEnchantment(e, false, this);
		}
		//m.attachedCards.clear();
        
		m.position.row=-1;
		m.position.column=-1;
		
        doOnFieldChangedTriggers();
        this.getPlayerDeck(m.position.color).add(0, m);
        if(m.position.color == UColor.white) 
        {
        	graveWhiteChanged=true;
        }
        else
        {
        	graveBlackChanged=false;
        }
        if(graveWhiteChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.white));
    	if(graveBlackChanged) this.addMessageToBothPlayers(this.getCardStackUpdate(UColor.black));
        
	}
	
	public void destroyMinion(Minion m, Minion source)
	{
		int dmg = m.Hp;
		AttackType att = AttackType.UNDEFINED;
		DamageType dmgt = DamageType.TERMINAL;
		this.doDmg(m, source, dmg, att, dmgt);
	}
	
}

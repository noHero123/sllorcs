package ServerStuff;
import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import BattleStuff.CardDB;
import BattleStuff.UColor;
import BattleStuff.Deck;
import BattleStuff.Minion;
import BattleStuff.Player;

public class MyLittleDatabase {
	
	
    private static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "scrollsdb.db"; 
	
	private static MyLittleDatabase instance;
	
	static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("error while loading the JDBC-driver");
            e.printStackTrace();
        }
    } 
	
	public static MyLittleDatabase getInstance()
    {
		if (instance == null)
        {
            instance = new MyLittleDatabase();
        }
        return instance;
    }
	
	private MyLittleDatabase()
	{
		this.initDBConnection();
	}

	 private void initDBConnection() 
	 {
	      try 
	      {
	            if (connection != null)
	                return;
	            
	            System.out.println("Creating Connection to Database...");
	            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
	            
	            if (!connection.isClosed())
	                System.out.println("...Connection established");
	            
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run() {
	                try {
	                    if (!connection.isClosed() && connection != null) {
	                        connection.close();
	                        if (connection.isClosed())
	                            System.out.println("Connection to Database closed");
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        
	        
	        String sql = "create table if not exists players (name string, gold int, shards int, admin int, featuretype int, spectate int, trade int, challenge int, rating int, head int, body int, leg int, armback int, armfront int, idoltype string,  idol1 int,  idol2 int,  idol3 int,  idol4 int,  idol5 int);";
	        try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
				sql = "CREATE INDEX IF NOT EXISTS nameidx ON players (name);";
				stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
	        
	        sql = "create table if not exists cards ( typeId int, owner int, tradeable int, level int);";
	        try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
				sql = "CREATE INDEX IF NOT EXISTS cardowneridx ON cards (owner);";
				stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
	        
	        sql = "create table if not exists decks ( name string, owner int, resources string, valid int, timestamp int, cardids string);";
	        try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
				sql = "CREATE INDEX IF NOT EXISTS deckowneridx ON decks (owner);";
				stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
	 }

	 public synchronized long getFreeIDPlayer()
	 {
		 //dont use that!
		 String sql = "SELECT rowid FROM players;";
		 long i=-1;
		 try 
			{
			 	Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				long max = 1;
				while(rs.next())
			    {
					long m = rs.getLong(1);
					if(max<=m) max = m+1;
			    }
			    i=max;
			    stmt.close();
			    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		 return i;
	 }
	 
	 
	 public long insertPlayer(String name)
	 {
		 long id=-1;
		 String sql = "INSERT INTO players (  name,   gold,  shards, admin , featuretype , spectate , trade , challenge , rating ,  head,  body,  leg,  armback,  armfront, idoltype, idol1, idol2, idol3, idol4, idol5) " +
                                   "VALUES ( '"+ name +"', 2000, 0, 0, 0, 1, 1, 1, 1000, 37, 11, 40, 1, 17, 'DEFAULT', 2, 2, 2, 2, 2);";
		 
		 try {
			 	connection.setAutoCommit(false);// Starts transaction.
			 	Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
			    rs.next();
			    id = rs.getLong(1);
				stmt.close();
				connection.commit(); 
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 
		 if(id!=-1)
		 {
			 addDefaultDecks(id);
		 }
		 return id;
	 }
	 
	 private void addDefaultDecks(long playerid)
	 {
		 ArrayList<String> toadd = new ArrayList<String>();
		 String deck1 = "{\"deck\":\"Growth Preconstructed\",\"author\":\"Mojang\",\"types\":[56,91,40,100,115,44,100,45,44,49,94,84,89,38,78,75,49,22,42,156,53,156,88,88,84,63,63,18,89,18,17,40,21,21,22,65,91,40,17,26,26,16,16,75,41,115,13,53,114,13]}";
		 toadd.add(deck1);
		 deck1 = "{\"deck\":\"Energy Preconstructed\",\"author\":\"Mojang\",\"types\":[152,27,152,151,143,39,77,2,151,87,96,96,98,98,139,96,141,98,141,141,2,139,130,80,80,24,107,35,68,82,77,24,149,27,118,83,107,24,35,68,1,67,149,67,150,83,87,66,66,32]}";
		 toadd.add(deck1);
		 deck1 = "{\"deck\":\"Order Preconstructed\",\"author\":\"Mojang\",\"types\":[97,124,95,20,125,73,125,127,124,71,86,123,126,54,123,122,120,64,50,50,37,74,97,105,71,122,86,73,126,36,120,99,99,128,128,23,101,95,61,52,124,15,121,52,48,123,127,50,20,23]}";
		 toadd.add(deck1);
		 deck1 = "{\"deck\":\"Decay Preconstructed\",\"author\":\"Mojang\",\"types\":[3,3,59,69,69,102,102,162,163,163,164,165,165,166,170,170,174,175,176,176,176,179,179,179,180,184,184,185,186,187,191,191,191,192,192,192,194,195,195,198,198,198,200,200,201,201,202,202,239,239]}";
		 toadd.add(deck1);
		 
		 
		 deck1 = "{\"deck\":\"EnergySet1and2\",\"author\":\"Mojang\",\"types\":[24,27,35,39,47,66,68,70,79,80,83,85,87,90,96,98,108,110,118,140,141,142,143,147,148,149,150,151,152,153]}";
		 toadd.add(deck1);

		 deck1 = "{\"deck\":\"GrowthSet1and2\",\"author\":\"Mojang\",\"types\":[4,13,16,17,18,29,34,38,40,41,43,44,49,56,60,63,76,84,88,89,91,94,100,112,113,114,115,129,154,189]}";
		 toadd.add(deck1);

		 deck1 = "{\"deck\":\"OrderSet1and2\",\"author\":\"Mojang\",\"types\":[15,19,20,23,36,37,48,52,54,55,57,61,71,73,86,93,97,99,101,103,109,111,119,120,124,125,126,127,128,158]}";
		 toadd.add(deck1);

		 deck1 = "{\"deck\":\"DecaySet1and2\",\"author\":\"Mojang\",\"types\":[59,69,102,131,137,159,162,164,165,168,169,170,171,172,174,176,177,180,181,182,184,185,190,191,194,195,197,198,199,202]}";
		 toadd.add(deck1);
		 
		 
		 
		 deck1 = "{\"deck\":\"Set3\",\"author\":\"Mojang\",\"types\":[1,2,25,32,67,77,81,82,107,130,138,139,145,146,160,21,22,26,30,33,42,45,53,58,65,75,78,104,117,156,50,51,62,64,74,92,95,105,116,121,122,123,155,157,193,3,161,163,166,173,175,179,183,186,187,188,192,196,200,201]}"	;
		 toadd.add(deck1);
		
		 deck1 = "{\"deck\":\"Set4\",\"author\":\"Mojang\",\"types\":[135,203,208,211,212,213,214,215,216,217,218,219,221,222,257,134,178,206,209,228,229,230,231,232,234,237,238,240,254,133,204,205,207,210,223,224,225,226,227,235,236,241,256,258,239,242,243,244,245,246,247,248,249,250,251,252,253,255,259]}";
		 toadd.add(deck1);
		 
		 deck1 = "{\"deck\":\"Set5\",\"author\":\"Mojang\",\"types\":[261,289,290,291,292,293,294,295,296,297,302,303,304,307,310,263,273,274,275,280,298,305,306,308,311,312,313,314,315,316,260,264,265,266,267,268,269,270,271,272,276,278,279,309,319,262,277,281,282,283,284,285,286,287,288,299,300,301,317,318]}";
		 toadd.add(deck1);
		 
		 deck1 = "{\"deck\":\"Set6\",\"author\":\"Mojang\",\"types\":[332,342,343,344,345,349,350,351,352,353,362,380,381,382,540,322,339,340,341,347,363,364,365,366,367,368,369,379,383,385,320,333,337,338,346,354,355,356,357,358,359,360,361,384,541,321,334,335,336,348,370,371,372,373,374,375,376,377,378,542]}";
		 toadd.add(deck1);
		 
		 
		 //test area targeting!
		 //deck1 = "{\"deck\":\"testing\",\"author\":\"Mojang\",\"types\":[25,246,244,244,245,343,343,343,343,343,343,343]}";
		 //toadd.add(deck1);
		 
		 
		 for(String s : toadd)
		 {
			 addDeckANDCards(s, playerid);
		 }
	 
	 }
	 
	 public void addDeck(String deckname, long playerid, String cardids)
	 {
		 //TODO: insert correct ressources :D
		 Long l = System.currentTimeMillis()/1000L;
		 String sql = "INSERT OR IGNORE INTO decks ( name , owner , resources , valid , timestamp , cardids  ) " +
				      "VALUES ('"+ deckname +"', " + playerid + ", 'GROWTH', 1," + l.intValue() +",'"+cardids+"');";
		 sql+=" UPDATE decks SET resources = 'GROWTH', valid = 1, timestamp = " + l.intValue() + ", cardids = '" + cardids +"'";
		 sql+=" WHERE name ='" + deckname + "' AND owner = " + playerid;
		 System.out.println(sql);
		 Statement stmt;
		 try {
			 	connection.setAutoCommit(false);// Starts transaction.
			 	stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				connection.commit(); 
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 
	 }
	 
	 private void addDeckANDCards(String deckstring, long playerid)
	 {
		 JSONObject jo = new JSONObject(deckstring);
		 String deckname = jo.getString("deck");
		 JSONArray arrr = jo.getJSONArray("types");
		 String deckids = "";

		 
		 ArrayList<Integer> toadd = new ArrayList<Integer>();
		 int j=0;
		 Statement stmt;
		 try 
		 {
			connection.setAutoCommit(false);// ONLY FOR addNewCardNoClose
			stmt = connection.createStatement();// ONLY FOR addNewCardNoClose
		
		 
		 
			for(int i=0; i< arrr.length();i++)
			{
				int typeid = arrr.getInt(i);
				
				/*
				toadd.add(typeid);
				j++;
				if(j>=100 || i == arrr.length()-1 )
				{
					ArrayList<Integer> cardids = addNewCards(toadd, playerid);
					
					for(int iii : cardids)
					{
						if(!deckids.equals(""))deckids += ",";
						deckids += iii +"";
						System.out.print(iii);
						System.out.print(",");
					}
					toadd.clear();
					j=0;
				}*/
				
				if(!deckids.equals(""))deckids += ",";
				deckids += addNewCardNoClose(typeid, playerid, stmt) +"";
				
			}
				stmt.close();// ONLY FOR addNewCardNoClose
				connection.commit();// ONLY FOR addNewCardNoClose
		 } 
			 catch (SQLException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		//now we have to add the deck
			Long l = System.currentTimeMillis()/1000L;
			
		String sql = "INSERT INTO decks ( name , owner , resources , valid , timestamp , cardids  ) " +
                    "VALUES ('"+ deckname +"', " + playerid + ", 'GROWTH', 1," + l.intValue() +",'"+deckids+"');";
		int deckid=-1;
		 try {
			 	connection.setAutoCommit(false);// Starts transaction.
			 	stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				/*ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
			    rs.next();
			    deckid = rs.getInt(1);*/
				stmt.close();
				connection.commit(); 
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 
	 }

	 public long addNewCardNoClose(int typeid, long owner, Statement stmt)
	 {
		 long id=-1;
		 String sql = "INSERT INTO cards ( typeId , owner , tradeable , level ) " +
                                   "VALUES ("+ typeid +", " + owner + ", 0, 0);";
		 
		 try {
			 
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
			    rs.next();
			    id = rs.getLong(1);
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 return id;
	 }
	 
	 public long addNewCard(int typeid, int owner)
	 {
		 long id=-1;
		 String sql = "INSERT INTO cards ( typeId , owner , tradeable , level ) " +
                                   "VALUES ("+ typeid +", " + owner + ", 0, 0);";
		 
		 try {
				/*Statement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.getGeneratedKeys();
			    rs.next();
			    id = rs.getInt(1);
				stmt.close();*/
			 
			 	connection.setAutoCommit(false);// Starts transaction.
			 	Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
			    rs.next();
			    id = rs.getLong(1);
				stmt.close();
				connection.commit(); 
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 return id;
	 }
	 
	 public ArrayList<Long> addNewCards(ArrayList<Integer> typeids, int owner)
	 {
		 //doesnt work properly...
		 long id=-1;
		 String sql = "INSERT INTO cards ( typeId , owner , tradeable , level ) VALUES ";
		 boolean added= false;
		 for(Integer iii : typeids)
		 {
			 if(added) sql+=",";
			 sql += "("+ iii +", " + owner + ", 0, 0)";
			 added=true;
		 }
		 System.out.println(sql);
		 ArrayList<Long> cardids = new ArrayList<Long>();
		 
		 try {
				/*Statement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.getGeneratedKeys();
			    rs.next();
			    id = rs.getInt(1);
				stmt.close();*/
			 
			 	connection.setAutoCommit(false);// Starts transaction.
			 	Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
				while(rs.next())
				{
					id = rs.getLong(1);
					cardids.add(id);
				}
				stmt.close();
				connection.commit(); 
				
			} 
	        catch (SQLException e) {
				e.printStackTrace();
			}
		 return cardids;
	 }
	 
	 
	 
 	 public Player getPlayerbyName(String name)
	 {
		 Player p = new Player();
		 Statement stmt;
		 boolean added=false;
		try 
		{
			stmt = connection.createStatement();
			String sql = "SELECT rowid,* FROM players Where name= '"+name+"' ;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
		    {
				System.out.println("in db" + rs.getLong(1) + " "  + rs.getString(2));
				added=true;
				p.profileId = rs.getLong(1);
				p.name = rs.getString(2);
				p.gold = rs.getInt(3);
				p.shards = rs.getInt(4);
				p.admin = rs.getInt(5);
				p.featureType = rs.getInt(6);
				p.spectatePermission = rs.getInt(7);
				p.acceptTrades = rs.getInt(8);
				p.acceptChallenges = rs.getInt(9);
				p.rating = rs.getInt(10);
				p.head = rs.getInt(11);
				p.body = rs.getInt(12);
				p.leg = rs.getInt(13);
				p.armBack = rs.getInt(14);
				p.armFront = rs.getInt(15);
				p.idolType = rs.getString(16);
				p.idol1 = rs.getInt(17);
				p.idol2 = rs.getInt(18);
				p.idol3 = rs.getInt(19);
				p.idol4 = rs.getInt(20);
				p.idol5 = rs.getInt(21);
				
				break;
		    }
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(added==false)
		 {
			 long id = insertPlayer(name);
			 p.name = name;
			 p.profileId = id;
			 System.out.println("new player was added: " + id);
		 }
		 return p;
	 }
	
	 
 	public ArrayList<Deck> getDecksFromPlayerID(long id)
	 {
 		ArrayList<Deck> ds = new ArrayList<Deck>();
 		String retval ="";
		Statement stmt;
		try 
		{
			stmt = connection.createStatement();
			String sql = "SELECT rowid,* FROM decks Where owner= "+id+";";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
		    {
				Deck d = new Deck();
				d.deckname = rs.getString(2);
				d.playerowner = rs.getLong(3);
				d.ressis = rs.getString(4);
				d.valid = rs.getInt(5);
				d.timestamp = rs.getLong(6);
				String cs = rs.getString(7);
				for(String c : cs.split(","))
				{
					try 
					{
						d.cardIds.add(Integer.parseInt(c));
					}
					catch(NumberFormatException nfe)
					{
						
					}
				}
				ds.add(d);
		    }
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return ds;
	 }
	
 	public ArrayList<SmallCard> getCardsFromPlayerID(long id)
	 {
		ArrayList<SmallCard> ds = new ArrayList<SmallCard>();
		String retval ="";
		Statement stmt;
		try 
		{
			stmt = connection.createStatement();
			String sql = "SELECT rowid,* FROM cards Where owner= "+id+";";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
		    {
				SmallCard d = new SmallCard();
				//( typeId int, owner int, tradeable int, level int)
				d.cardid = rs.getLong(1);
				d.typeid = rs.getInt(2);
				d.owner = rs.getLong(3);
				d.tradeable = rs.getInt(4);
				d.level = rs.getInt(5);
				ds.add(d);
		    }
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return ds;
	 }
	
 	public ArrayList<Minion> getDeckFromPlayer(String deckname, long playerid, boolean iswhite)
 	{
 		ArrayList<Minion> deck = new ArrayList<Minion>();
 		Statement stmt;
 		ArrayList<SmallCard> cards = new ArrayList<SmallCard>();
 		try 
		{
			stmt = connection.createStatement();
			String sql = "SELECT * FROM decks Where owner= "+playerid+" AND name = '" + deckname + "';";
			ResultSet rs = stmt.executeQuery(sql);
			String cs="";
			if(rs.next())
			{
				cs = rs.getString(6);
			}
			String inlist = "(";
			boolean added=false;
			for(String c : cs.split(","))
			{
				if(added)inlist+=",";
				inlist+=c;
				added=true;
			}
			inlist += ")";
			
			sql = "SELECT rowid,* FROM cards Where owner= "+playerid+" AND rowid IN " + inlist + ";";
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
		    {
				SmallCard d = new SmallCard();
				//( typeId int, owner int, tradeable int, level int)
				d.cardid = rs.getLong(1);
				d.typeid = rs.getInt(2);
				d.owner = rs.getLong(3);
				d.tradeable = rs.getInt(4);
				d.level = rs.getInt(5);
				cards.add(d);
		    }
			stmt.close();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		CardDB cdb = CardDB.getInstance();
 		
 		UColor colw = UColor.black;
 		if(iswhite)colw = UColor.white;
 		for(SmallCard sc : cards)
 		{
 			Minion m = new Minion(cdb.cardId2Card.get(sc.typeid), sc.cardid, colw);
 			m.lvl = sc.level;
 			deck.add(m);
 		}
 		
 		return deck;
 	}
	 //String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
}

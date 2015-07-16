package ServerStuff;
import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import BattleStuff.CardDB;
import BattleStuff.Color;
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
		 String deck1 = "{\"deck\":\"Growth Preconstructed\",\"author\":\"ueHero\",\"types\":[56,91,40,100,115,44,100,45,44,49,94,84,89,38,78,75,49,22,42,156,53,156,88,88,84,63,63,18,89,18,17,40,21,21,22,65,91,40,17,26,26,16,16,75,41,115,13,53,114,13]}";
		 ArrayList<String> toadd = new ArrayList<String>();
		 toadd.add(deck1);
		 //deck1 = "{\"deck\":\"Energy Starter\",\"author\":\"ueHero\",\"types\":[68,67,67,68,223,70,283,283,32,32,27,282,79,205,83,256,285,285,35,85,227,85,153,226,39,90,151,151,96,152,149,96,98,98,149,96,142,141,98,142,107,235,141,141,107,66,207,66,66,210]}";
		 //toadd.add(deck1);
		 
		 //deck1 = "{\"deck\":\"Order Starter\",\"author\":\"ueHero\",\"types\":[95,125,125,48,237,237,237,123,95,128,92,92,121,48,122,50,126,126,127,127,86,126,157,125,71,74,73,71,86,128,36,37,64,15,20,311,57,234,105,105,232,311,50,101,51,51,55,54,99,15]}";
		 //toadd.add(deck1);
		 
		 //deck1 = "{\"deck\":\"Decay Starter\",\"author\":\"ueHero\",\"types\":[196,194,195,195,185,190,188,181,179,179,179,242,242,242,268,268,163,163,163,164,164,164,165,248,69,166,102,69,197,197,194,199,162,244,162,161,244,244,271,271,172,269,269,250,265,173,267,268,309,177]}";
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
 		
 		Color colw = Color.black;
 		if(iswhite)colw = Color.white;
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

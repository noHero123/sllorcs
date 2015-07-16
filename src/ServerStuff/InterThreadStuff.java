package ServerStuff;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import BattleStuff.Board;
import BattleStuff.Player;


public class InterThreadStuff 
{
	public String usedServerIp = "127.0.0.1";
	public int lookupPort = 8082;
	public int lobbyPort = 8083;
	public int battlePort = 8084;
	List<Player> currentPlayers = Collections.synchronizedList(new ArrayList<Player>());
	List<Board> currentBattles = Collections.synchronizedList(new ArrayList<Board>());
	
	private static InterThreadStuff instance;
	public static InterThreadStuff getInstance()
    {
		if (instance == null)
        {
            instance = new InterThreadStuff();
        }
        return instance;
    }

	private InterThreadStuff()
	{

	}

	//http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#synchronizedList%28java.util.List%29
	public synchronized Player addPlayerToLobby(Player p)
	{
		java.util.Iterator<Player> i = currentPlayers.iterator();
		while (i.hasNext())
		{
			Player pp = i.next();
			if(pp.profileId == p.profileId)
			{
				return pp;
			}
		}
		
		currentPlayers.add(p);
		return p;
	}
	
	public synchronized boolean addBoard(Board b)
	{
		//returns true if board was added!
		if(getBoardById(b.whitePlayer.profileId)!=null) return false;
		if(getBoardById(b.blackPlayer.profileId)!=null) return false;
		this.currentBattles.add(b);
		return true;
	}
	
	public synchronized void removeBoard(Board b)
	{
		currentBattles.remove(b);
	}
	
	public synchronized Board getBoardById(long id)
	{
		java.util.Iterator<Board> i = currentBattles.iterator();
		while (i.hasNext())
		{
			Board p = i.next();
			if(p.whitePlayer.profileId == id || p.blackPlayer.profileId == id) return p;
		}
		
		return null;
	}
	
	public synchronized void removePlayerfromLobby(Player p)
	{
		currentPlayers.remove(p);
		java.util.Iterator<Player> i = currentPlayers.iterator();
		while (i.hasNext())
		{
			Player pp = i.next();
			if(pp.goingToChallengeID == p.profileId)
			{
				pp.goingToChallengeID=-1;
			}
		}
	}
	
	public synchronized Player getPlayerByName(String name)
	{
		java.util.Iterator<Player> i = currentPlayers.iterator();
		while (i.hasNext())
		{
			Player p = i.next();
			if(p.name.equals(name)) 
			{
				return p;
			}
				
		}
		
		return null;
	}
	
	public synchronized Player getPlayerById(long id)
	{
		java.util.Iterator<Player> i = currentPlayers.iterator();
		while (i.hasNext())
		{
			Player p = i.next();
			if(p.profileId==id) 
				{
				return p;
				}
		}
		
		return null;
	}
	
}

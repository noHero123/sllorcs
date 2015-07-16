package ServerStuff;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;


import org.json.*;

import BattleStuff.Board;
import BattleStuff.Color;
import BattleStuff.Player;
import BattleStuff.Position;


public class BattleServerThread extends Thread{
	
	private Board playedBoard;
	private Socket  socket;
	private boolean running = true;
	private String username ="";
	DataOutputStream out = null;
	public Player ownPlayer = new Player();
	
	private int GameState = 0;
	
	InterThreadStuff its = InterThreadStuff.getInstance();
	
	public BattleServerThread(Socket s)
	{
		this.socket = s;
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
			out.writeBytes(s);
			out.flush();
			if(!s.contains("\"Ping\""))System.out.println("send to " + this.ownPlayer.name +  " " + socket.getPort() + ": "+s);
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
	

	private String getGameInfo()
	{
		return this.playedBoard.getGameInfoMessage(this.ownPlayer.name);
	}
	
	
	private String getGameStateMessage()
	{
		return 	this.playedBoard.getGameStateMessage();
	}
	
	
	public void handleInput(JSONObject jo)
	{
		String mssgtype = jo.getString("msg");
		if(mssgtype.equals("Ping"))
    	{
			this.sendToClient( this.getPingMessage());
    		return;
    	}
		
		if(mssgtype.equals("Connect"))
		{
			
			//same as firstconnect only with msg:Connect
			this.username = jo.getJSONObject("accessToken").getJSONObject("selectedProfile").getString("name");
			
			Player p = this.its.getPlayerByName(username);
			if(p==null)
			{
				p = MyLittleDatabase.getInstance().getPlayerbyName(username);
				p = this.its.addPlayerToLobby(p);
	            System.out.println("Player loaded from db:" + p.name + " " + p.profileId);
			}
			p.bt = this;
			p.isBattling=true;
			this.ownPlayer = p;
			
			this.playedBoard = this.its.getBoardById(this.ownPlayer.profileId);
			
			if(this.playedBoard==null)
			{
				//TODO send error and close socket
				this.playedBoard = new Board();
				this.playedBoard.setPlayers(this.ownPlayer, this.ownPlayer, true);
				System.out.println("no board found");
				//return;
			}
			
			this.playedBoard.updatePlayer(this.ownPlayer);
			this.sendToClient("{\"op\":\"Connect\",\"msg\":\"Ok\"}");
			
			return;
		}
		
		if(mssgtype.equals("JoinBattle"))
    	{
			//we got {"msg" : "JoinBattle"}
			// we return GameInfo msg
			this.sendToClient(this.getGameInfo());
			this.sendToClient("{\"from\":\"Scrolls\",\"text\":\"This game is spectatable (private chat).\",\"msg\":\"GameChatMessage\"}");
			return;
    	}
		
		if(mssgtype.equals("GameChatMessage"))
    	{
			//we got {"text" : "oh noe","msg" : "GameChatMessage"}  // note: no from!
			String s = "{\"from\":\""+this.username+"\",\"text\":\""+jo.getString("text") +"\",\"msg\":\"GameChatMessage\"}";
			
			this.playedBoard.whitePlayer.sendMessgeToBattleServer(s);
			this.playedBoard.blackPlayer.sendMessgeToBattleServer(s);
			
			return;
    	}
		
		if(mssgtype.equals("EndPhase"))
    	{
			//we got {"phase" : "Init","msg" : "EndPhase"}
			//or {"phase" : "PreMain","msg" : "EndPhase"}
			
			//if init: start first turn 
			//we send: GameChatMessage (spectable or not)
			
			String ph=jo.getString("phase");
			this.playedBoard.doPhase(ph, this.ownPlayer.profileId);
			
			return;
    	}
		
		if(mssgtype.equals("PlayCardInfo"))
    	{
			//we got {"card" : 29908645,"msg" : "PlayCardInfo"}
			//we send cardinfo
			long cardid = jo.getLong("card");
			this.playedBoard.handleCardInfoMessage(cardid, this.ownPlayer.profileId);
			return;
    	}
		
		if(mssgtype.equals("PlayCard"))
    	{
			//we got {"card" : 29908635,"data" : {"positions" : ["W,2,0"]},"msg" : "PlayCard"}
			//"data" is optional
			//we send ress update, hand update, CardPlayed and then SelectedTiles, SummonUnit, StatsUpdate
			long cardid = jo.getLong("card");
			ArrayList<Position> poses = new ArrayList<Position>();
			if(jo.has("data"))
			{
				
				JSONArray jarr = jo.getJSONObject("data").getJSONArray("positions");
				for(int i=0; i < jarr.length(); i++)
				{
					String po = jarr.getString(i);
					Color c = Color.white;
					if(po.split(",")[0].equals("B")) c=Color.black;
					
					int r = Integer.parseInt(po.split(",")[1]);
					int col = Integer.parseInt(po.split(",")[2]);
					
					Position posi = new Position(c, r, col);
					poses.add(posi);					
				}
			}
			
			this.playedBoard.playCard(cardid, poses, this.ownPlayer.profileId);
			return;
    	}
		
		
		if(mssgtype.equals("SacrificeCard"))
    	{
			//we got {"card" : 29908645,"resource" : "ENERGY","msg" : "SacrificeCard"}
			//we send cardsacrifices, ressource update, hand update, cardstack update + mulligan update (if changed)
			long cardid = jo.getLong("card"); //remove that card
			String ressi = jo.getString("resource"); //raise that resource
			this.playedBoard.sacrificeCard(cardid, ressi, this.ownPlayer.profileId);
			return;
    	}
		
		if(mssgtype.equals("ActivateAbilityInfo"))
		{
			//TODO
			//{"abilityId" : "Move","unitPosition" : "W,2,1","msg" : "ActivateAbilityInfo"}
			
			//we answer {"unitPosition":{"color":"white","position":"2,1"},"abilityId":"Move","hasEnoughResources":true,"isPlayable":true,"data":{"selectableTiles":{"tileSets":[[{"color":"white","position":"1,1"},{"color":"white","position":"1,0"},{"color":"white","position":"2,0"},{"color":"white","position":"3,1"},{"color":"white","position":"3,0"},{"color":"white","position":"2,2"}]]},"targetArea":"UNDEFINED"},"msg":"AbilityInfo"}

			String abilityName = jo.getString("abilityId");
			
			String upos = jo.getString("unitPosition");
			Color c = Color.white;
			if(upos.split(",")[0].equals("B")) c=Color.black;
			int r = Integer.parseInt(upos.split(",")[1]);
			int col = Integer.parseInt(upos.split(",")[2]);
			
			Position pos = new Position(c, r, col);
			
			this.playedBoard.getAbilityInfo( abilityName, pos, this.ownPlayer.profileId);
			return;
		}
		
		
		if(mssgtype.equals("ActivateAbility"))
		{
			//we got:
			//{"abilityId" : "SummonWolf","unitPosition" : "W,2,1","data" : {"positions" : ["W,2,2"]},"msg" : "ActivateAbility"}
			String abilityName = jo.getString("abilityId");
			String upos = jo.getString("unitPosition");
			Color c = Color.white;
			if(upos.split(",")[0].equals("B")) c=Color.black;
			int r = Integer.parseInt(upos.split(",")[1]);
			int col = Integer.parseInt(upos.split(",")[2]);
			Position unitpos = new Position(c, r, col); 
			ArrayList<Position> poses = new ArrayList<Position>();
			if(jo.has("data"))
			{
				JSONArray jarr = jo.getJSONObject("data").getJSONArray("positions");
				for(int i=0; i < jarr.length(); i++)
				{
					String po = jarr.getString(i);
					c = Color.white;
					if(po.split(",")[0].equals("B")) c=Color.black;
					
					r = Integer.parseInt(po.split(",")[1]);
					col = Integer.parseInt(po.split(",")[2]);
					
					Position posi = new Position(c, r, col);
					poses.add(posi);					
				}
			}
			
			this.playedBoard.activateAbility(abilityName, unitpos, poses, this.ownPlayer.profileId);
			return;
			
		}
		
		if(mssgtype.equals("Mulligan"))
		{
			//we got {"msg" : "Mulligan"}
			
			this.playedBoard.mulligan(this.ownPlayer.profileId);
			return;
		}
		
		if(mssgtype.equals("Surrender"))
		{
			//"msg" : "Surrender"
			this.playedBoard.surrendering(this.ownPlayer.profileId);
			return;
		}
		
		if(mssgtype.equals("LeaveGame"))
		{
			//"msg" : "LeaveGame"
			//TODO player leaves game... disconnect him!
			try
			{
				this.socket.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			this.ownPlayer.initGame=false;
			this.ownPlayer.goingToChallengeID=-1;
			this.ownPlayer.isBattling=false;
			if(this.playedBoard.whitePlayer.initGame==false && this.playedBoard.blackPlayer.initGame==false)
			{
				//remove battlefield
				InterThreadStuff.getInstance().removeBoard(this.playedBoard);
				this.running=false;
			}
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
        while (running) 
        {
            try {
                line = null;
                
                byte[] contents = new byte[4*1024];

                int bytesRead = bis.read(contents);
                if(bytesRead!=-1)
                {
                	if(line==null)line="";
                    line += new String(contents, 0, bytesRead);
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
                	
                	
                	if(!line.startsWith("{"))
                		{
                			System.out.println("rec from " + this.ownPlayer.name + " " + socket.getPort() + ": " + line);
                			continue;
                		}
                	
                	JSONObject jo = new JSONObject(line);
                	if(!jo.getString("msg").equals("Ping"))
                	{
                		System.out.println("rec from " + this.ownPlayer.name + " " + socket.getPort() + ": " + line);
                		System.out.println(jo.toString());
                	}
                	this.handleInput(jo);
                	
                
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}

package ServerStuff;



import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.lang.Thread;


public class LobbyServer extends Thread{
	
	
	private ServerSocket  serverSocket;
	public static Boolean running =true;
	public List<LobbyThread> clientlist = Collections.synchronizedList( new ArrayList<LobbyThread>());
	
	public static LobbyServer instance;
	
	public static LobbyServer getInstance()
    {
		if (instance == null)
        {
            instance = new LobbyServer();
        }
        return instance;
    }
	
	private LobbyServer()
	{
		try 
		{
			this.serverSocket = new ServerSocket (InterThreadStuff.getInstance().lobbyPort);
			System.out.println("listening to " + InterThreadStuff.getInstance().lobbyPort);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("cant create lobbyserver");
			e.printStackTrace();
		}
	}
	
	public String getLobbyInfo()
	{

		return "{\"version\":\"1.4.0\",\"assetURL\":\"http://download.scrolls.com/assets/\",\"newsURL\":\"http://scrolls.com/news\",\"roles\":\"LOBBY,RESOURCE,LOOKUP\",\"msg\":\"ServerInfo\"}";
	}
	
	public void run()
	{
		Socket socket=null;
		while(running)
		{
			try {
				socket = serverSocket.accept();
				//send serverinfo of lookupserver:
				socket.getOutputStream().write(getLobbyInfo().getBytes("UTF-8"));
				socket.getOutputStream().flush();
				//handle other stuff
				LobbyThread lut = new LobbyThread(socket);
				lut.start();
				this.clientlist.add(lut);
				System.out.println("started lobbyconnection with" + socket.getInetAddress() + " " + socket.getPort());
            } catch (IOException e) {
                System.out.println("server accept error: " + e);
            }
            // new thread for a client
            
		}
		System.out.println("stopped lobbyserver");
	}
	
	public void cancel()
	{
		System.out.println("stopping lobbyserver...");
		running =false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("socket cant be closed: " + e);
			e.printStackTrace();
		}
		
		for(LobbyThread l : this.clientlist )
		{
			l.cancel();
		}
		this.interrupt();
		
	}

}





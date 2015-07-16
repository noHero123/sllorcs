package ServerStuff;



import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.lang.Thread;


public class BattleServer extends Thread{
	
	private ServerSocket  serverSocket;
	public static Boolean running =true;
	private ArrayList<BattleServerThread> clientlist = new ArrayList<BattleServerThread>();
	
	public static BattleServer instance;
	
	public static BattleServer getInstance()
    {
		if (instance == null)
        {
            instance = new BattleServer();
        }
        return instance;
    }
	
	private BattleServer()
	{
		try 
		{
			this.serverSocket = new ServerSocket (InterThreadStuff.getInstance().battlePort);
			System.out.println("listening to " + InterThreadStuff.getInstance().battlePort);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("cant create battleserver");
			e.printStackTrace();
		}
	}
	
	private String getBattleInfo()
	{
		return "{\"version\":\"1.4.1\",\"assetURL\":\"http://download.scrolls.com/assets/\",\"newsURL\":\"http://scrolls.com/news\",\"roles\":\"GAME,RESOURCE\",\"msg\":\"ServerInfo\"}";
	}
	
	public void run()
	{
		Socket socket=null;
		while(running)
		{
			try {
				socket = serverSocket.accept();
				//send serverinfo of battleserver:
				socket.getOutputStream().write(getBattleInfo().getBytes("UTF-8"));
				socket.getOutputStream().flush();
				
				//handle other stuff
				BattleServerThread lut = new BattleServerThread(socket);
				lut.start();
				this.clientlist.add(lut);
				System.out.println("started BattleConnection with" + socket.getInetAddress() + " " + socket.getPort());
            } catch (IOException e) {
                System.out.println("server accept error: " + e);
            }
            // new thread for a client
            
		}
		System.out.println("stopped battleserver");
	}
	
	public void cancel()
	{
		System.out.println("stopping battleserver...");
		running =false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("socket cant be closed: " + e);
			e.printStackTrace();
		}
		
		for(BattleServerThread l : this.clientlist )
		{
			l.cancel();
		}
		this.interrupt();
		
	}

}





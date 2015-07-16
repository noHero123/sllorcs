package ServerStuff;


import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.lang.Thread;


public class LookupServer extends Thread{
	
	private ServerSocket  serverSocket;
	public static Boolean running =true;
	private ArrayList<LookupThread> clientlist = new ArrayList<LookupThread>();
	
	public LookupServer()
	{
		try 
		{
			this.serverSocket = new ServerSocket (InterThreadStuff.getInstance().lookupPort);
			System.out.println("listening to " +InterThreadStuff.getInstance().lookupPort);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("cant create lookupserver");
			e.printStackTrace();
		}
	}
	
	public String getLookupInfo()
	{

		return "{\"version\":\"1.4.0\",\"assetURL\":\"http://download.scrolls.com/assets/\",\"newsURL\":\"http://scrolls.com/news\",\"roles\":\"LOOKUP,RESOURCE,JOBS\",\"msg\":\"ServerInfo\"}";
	}
	
	public void run()
	{
		Socket socket=null;
		while(running)
		{
			try {
				socket = serverSocket.accept();
				//send serverinfo of lookupserver:
				socket.getOutputStream().write(getLookupInfo().getBytes("UTF-8"));
				socket.getOutputStream().flush();
				//handle other stuff
				LookupThread lut = new LookupThread(socket);
				lut.start();
				this.clientlist.add(lut);
				System.out.println("started lookupconnection with" + socket.getInetAddress() + " " + socket.getPort());
            } catch (IOException e) {
                System.out.println("server accept error: " + e);
            }
            // new thread for a client
            
		}
		System.out.println("stopped lookupserver");
	}
	
	public void cancel()
	{
		System.out.println("stopping lookupserver...");
		running =false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("socket cant be closed: " + e);
			e.printStackTrace();
		}
		
		for(LookupThread l : this.clientlist )
		{
			l.cancel();
		}
		this.interrupt();
		
	}

}




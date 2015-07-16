package ServerStuff;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


import org.json.*;



public class LookupThread extends Thread{
	
	private Socket  socket;
	private boolean running =true;
	private InterThreadStuff its = InterThreadStuff.getInstance();
	
	public LookupThread(Socket s)
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
	
	public void run() {
        InputStream inp = null;
        //BufferedReader  brinp = null;
        BufferedInputStream bis=null;
        DataOutputStream out = null;
        System.out.println("lookupthread running");
        try {
            inp = socket.getInputStream();
            //brinp = new BufferedReader(new InputStreamReader(inp));
            bis = new BufferedInputStream(inp);
            out = new DataOutputStream(socket.getOutputStream());
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
                
                /*while( (bytesRead = bis.read(contents)) != -1){ 
                	if(line==null)line="";
                    line += new String(contents, 0, bytesRead);
                    System.out.println("while " + line);
                }*/
                
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    //socket.close();
                    //return;
                } else {
                	
                	System.out.println("rec. from client:" + line);
                	
                	if(!line.startsWith("{")) continue;
                	JSONObject jo = new JSONObject(line);
                	if(jo.getString("msg").equals("LobbyLookup"))
                	{
                		String s= "{\"ip\":\""+ its.usedServerIp + "\",\"port\":"+its.lobbyPort+",\"msg\":\"LobbyLookup\"}";
                		System.out.println("send "+ s);
                		out.writeBytes(s);
                        out.flush();
                        this.socket.close();
                        this.running=false;
                        
                	}
                	
                	
                    //out.writeBytes(line + "\n\r");
                    //out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

}

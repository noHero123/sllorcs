import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

import BattleStuff.CardDB;
import ServerStuff.BattleServer;
import ServerStuff.CardTypesMessageProvider;
import ServerStuff.InterThreadStuff;
import ServerStuff.LobbyServer;
import ServerStuff.LookupServer;
import ServerStuff.MyLittleDatabase;
import java.net.InetAddress;
import java.net.UnknownHostException;
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		InterThreadStuff.getInstance().usedServerIp = "127.0.0.1"; //ip of server
		
		System.out.println("database init...");
		MyLittleDatabase mld = MyLittleDatabase.getInstance();
		CardDB.getInstance().init(CardTypesMessageProvider.getCardTypesMessage());
		
		System.out.println("starting servers...");
		
		LookupServer ls = new LookupServer();
		LobbyServer lbbs = LobbyServer.getInstance();
		BattleServer bs = BattleServer.getInstance();
		bs.start();
		lbbs.start();
		ls.start();
		
		
		
		System.out.println("Enter something to close servers:");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ls.cancel();
		lbbs.cancel();
		bs.cancel();
		System.out.println("end");
	}

}

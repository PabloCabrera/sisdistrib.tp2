package balanceCargaJuanMartin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainServerT implements Runnable {
	Socket sClient;
	Socket sServer;
	Host host;
	
	public MainServerT(Socket s, Host h) {
		sClient = s;
		host = h;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("MS Thread connectando a" + host.getIp() + ":" + host.getPort());
			sServer = new Socket(host.getIp(), host.getPort());
			// sClient: inputStrem -> sServidor out
			// sServer: inputStrem -> sClient out
			
			BufferedReader cReader = new BufferedReader(new InputStreamReader(sClient.getInputStream()));
			PrintWriter cPrint = new PrintWriter(sClient.getOutputStream(), true);
			
			BufferedReader sReader = new BufferedReader(new InputStreamReader(sServer.getInputStream()));
			PrintWriter sPrint = new PrintWriter(sServer.getOutputStream(), true);
			
			(new Thread(new ThreadReader(cReader, sPrint))).start();
			(new Thread(new ThreadReader(sReader, cPrint))).start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

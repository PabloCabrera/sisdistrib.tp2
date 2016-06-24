package balanceCargaJuanMartin;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class MainServer {
	ServerSocket s;
	int port;
	ArrayList<Host> sPool;
	int nextH;
	
	public MainServer(int port) {
		this.port = port;
		nextH = 0;
		sPool = new ArrayList<Host>();
	}
	
	public void start() {
		try {
			s = new ServerSocket(port);
			//
			while (true) {
				System.out.println("MS: client en " + port);
				//
				(new Thread(new MainServerT(s.accept(),sPool.get(nextH)))).start();
				if (++nextH >= sPool.size())
					nextH = 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addHost(Host h) {
		sPool.add(h);
	}
	
	public static void main (String args[]) {
		MainServer server = new MainServer(5000);
		// xxx
		server.addHost(new Host("localhost", 7001));
		server.addHost(new Host("localhost", 7000));
		server.addHost(new Host("localhost", 7002));
		server.start();
	}
}

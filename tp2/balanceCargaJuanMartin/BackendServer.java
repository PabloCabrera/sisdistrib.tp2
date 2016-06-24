package balanceCargaJuanMartin;

import java.io.IOException;
import java.net.ServerSocket;


public class BackendServer {
	private ServerSocket s;
	private int port;
	
	public BackendServer(int port) {
		this.port = port;
		try {
			s = new ServerSocket(port);
			System.out.println("BS: esperando en " + port);
			while (true) {
				(new Thread(new Tarea(s.accept()))).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		BackendServer bServer = new BackendServer(7000);
	}
}

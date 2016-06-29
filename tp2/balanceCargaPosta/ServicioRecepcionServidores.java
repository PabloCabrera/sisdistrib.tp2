package balanceCargaPosta;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServicioRecepcionServidores implements Runnable {
	
	protected ArrayList<InfoServidor> servidores;
	protected DatagramSocket socket;
	
	
	public ServicioRecepcionServidores(Integer i) throws SocketException {
		this.socket = new DatagramSocket(i);
	}

	public static void main(String[] args) {
		try {
			ServicioRecepcionServidores server = new ServicioRecepcionServidores(6000);
			new Thread(server).start();
		} catch (IOException e) {
			System.out.println("no pude iniciar el servidor de balance de carga");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {

	}

	public ArrayList<InfoServidor> getServidores() {
		return servidores;
	}

	public void setServidores(ArrayList<InfoServidor> servidores) {
		this.servidores = servidores;
	}

}
